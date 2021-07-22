package com.example.kulakov_p3_wallpapers_app.views.dialogs

import android.Manifest
import android.app.Dialog
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.utils.IFileProvider
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.DialogPhotoFunctionsBinding
import com.example.kulakov_p3_wallpapers_app.utils.InternetUtil
import com.example.kulakov_p3_wallpapers_app.utils.Utils
import com.example.kulakov_p3_wallpapers_app.utils.extensions.loadingDialog
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoFunctionsVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class PhotoFunctionsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogPhotoFunctionsBinding
    private val viewModel: PhotoFunctionsVM by viewModels()
    private val args: PhotoFunctionsDialogArgs by navArgs()
    private lateinit var wm: WallpaperManager

    private lateinit var storagePermissions: ActivityResultLauncher<Array<String>>

    private var setDesktopJob: Job? = null
    private var saveFavoriteJob: Job? = null
    private var setLockScreenJob: Job? = null

    private lateinit var internetObserver: InternetUtil

    private lateinit var loadingDialog: Dialog

    @Inject
    lateinit var fileProvider: IFileProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.dialog_photo_functions, container,
            false
        )
        binding = DialogPhotoFunctionsBinding.bind(view)
        binding.viewModel = viewModel
        viewModel.photoItem = args.photoItem?.model
        internetObserver = InternetUtil(requireContext())
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        wm = WallpaperManager.getInstance(context.applicationContext)

        storagePermissions = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            var allAllowed = true
            permissions.entries.forEach {
                if(!it.value) {
                    allAllowed = false
                    return@forEach
                }
            }
            if(allAllowed) {
                saveToFavorite()
            }
        }

        loadingDialog = requireContext().loadingDialog
        loadingDialog.setCancelable(false)
    }

    override fun onDetach() {
        super.onDetach()
        storagePermissions.unregister()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.delegate = object : PhotoFunctionsDelegate {
            override fun onSetDesktopWallpaper() {
                setDesktopWallpaper()
            }

            override fun onSetLockScreenWallpaper() {
                setLockScreenWallpaper()
            }

            override fun onSaveToFavorite() {
                if(!fileProvider.checkStoragePermissions()) {
                    setStoragePermissions()
                } else {
                    saveToFavorite()
                }
            }
        }

        viewModel.isLoadingDialogOpen.observe(viewLifecycleOwner, {
            if(it) loadingDialog.show() else loadingDialog.dismiss()
        })
    }

    private fun setStoragePermissions() {
        storagePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    private fun saveToFavorite() {
        saveFavoriteJob?.cancel()
        viewModel.isLoadingDialogOpen.value = true
        Log.e("asd", "url ${viewModel.photoItem!!.regular}")

        saveFavoriteJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.saveToFavorite()
            viewModel.isLoadingDialogOpen.postValue(false)
        }
    }

    private fun setDesktopWallpaper() {
        setDesktopJob?.cancel()
        viewModel.isLoadingDialogOpen.value = true
        setDesktopJob = lifecycleScope.launch(Dispatchers.IO) {
            if(viewModel.isPhotoSaved()) {
                val data = File(fileProvider.getPhotoItemFilePath(viewModel.photoItem!!)!!).inputStream()
                wm.setStream(data)
            } else {
                if(internetObserver.isInternetOn()) {
                    val bitmap = Utils.getBitmapByUrl(viewModel.photoItem!!.regular!!)
                    wm.setBitmap(bitmap)
                }
            }

            viewModel.isLoadingDialogOpen.postValue(false)
        }
    }

    private fun setLockScreenWallpaper() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mainSetLockScreenWallpaper()
        } else {
            alternateSetWallpaperLockScreen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun mainSetLockScreenWallpaper() {
        if(!wm.isSetWallpaperAllowed && !wm.isWallpaperSupported) {
            Toast.makeText(context, resources.getString(R.string.wallpaper_not_support), Toast.LENGTH_SHORT).show()
            return
        }

        setLockScreenJob?.cancel()
        viewModel.isLoadingDialogOpen.value = true
        setLockScreenJob = lifecycleScope.launch(Dispatchers.IO) {
            if(viewModel.isPhotoSaved()) {
                val data = File(fileProvider.getPhotoItemFilePath(viewModel.photoItem!!)!!).inputStream()
                wm.setStream(data, null, true, WallpaperManager.FLAG_LOCK)
            } else {
                if(internetObserver.isInternetOn()) {
                    val bitmap = Utils.getBitmapByUrl(viewModel.photoItem!!.regular!!)
                    wm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
            }
            viewModel.isLoadingDialogOpen.postValue(false)
        }
    }

    private fun alternateSetWallpaperLockScreen() {
        setLockScreenJob?.cancel()

        setLockScreenJob = lifecycleScope.launch(Dispatchers.IO) {
            fileProvider.savePhoto(viewModel.photoItem!!)
            withContext(Dispatchers.Main) {
                val intent = Intent("android.intent.action.ATTACH_DATA")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(Uri.fromFile(File(viewModel.photoUrl!!)), "image/*")
                intent.putExtra("mimeType", "image/*")
                startActivity(Intent.createChooser(intent, resources.getString(R.string.set_as)))
            }
        }
    }

    interface PhotoFunctionsDelegate {
        fun onSetDesktopWallpaper()
        fun onSetLockScreenWallpaper()
        fun onSaveToFavorite()
    }
}