package com.example.kulakov_p3_wallpapers_app.views.dialogs

import android.Manifest
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.DialogPhotoFunctionsBinding
import com.example.kulakov_p3_wallpapers_app.utils.Utils
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoFunctionsVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class PhotoFunctionsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogPhotoFunctionsBinding
    private val viewModel: PhotoFunctionsVM by viewModels()
    private val args: PhotoFunctionsDialogArgs by navArgs()
    private lateinit var wm: WallpaperManager

    private lateinit var storagePermissions: ActivityResultLauncher<Array<String>>

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
                Log.e("asd", "${it.key} = ${it.value}")
                if(!it.value) {
                    allAllowed = false
                    return@forEach
                }
            }
            Log.e("asd", "1 ${allAllowed}")
            if(allAllowed) {
                savePhoto()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        storagePermissions.unregister()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveSetWallpapers.observe(viewLifecycleOwner, {
            wm.setBitmap(it)
        })

        viewModel.liveSetLockScreen.observe(viewLifecycleOwner, {
            Log.e("asd", "api level ${Build.VERSION.SDK_INT}")

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setWallpaperLockScreen(it)
            } else {
                if(viewModel.photoItem?.regular != null) {
                    alternateSetWallpaperLockScreen(viewModel.photoItem!!.regular!!)
                }
            }
        })

        viewModel.closeDialog.observe(viewLifecycleOwner, {
            if(it) dismiss()
        })


        binding.saveToFavoriteButton.setOnClickListener {
            if(!checkStoragePermissions()) {
                setStoragePermissions()
            } else {
                savePhoto()
            }
        }
    }

    private var saveFavoriteJob: Job? = null

    private fun savePhoto() {
        saveFavoriteJob?.cancel()
        saveFavoriteJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.photoItem!!.localPhotoPath = Utils.saveFavoritePhoto(requireContext(), viewModel.photoItem!!)
            viewModel.photoItem!!.user?.localPhotoPath = Utils.saveFavoriteUser(requireContext(), viewModel.photoItem!!.user!!)
            viewModel.saveToFavorite()
            dismiss()
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun setWallpaperLockScreen(bitmap: Bitmap) {
        Log.e("asd", "set wallpapers lock")
        if(wm.isSetWallpaperAllowed) {
            wm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
        } else {
            Toast.makeText(context, resources.getString(R.string.wallpaper_not_support), Toast.LENGTH_SHORT).show()
        }
    }


    private fun alternateSetWallpaperLockScreen(url: String) {
        val intent = Intent("android.intent.action.ATTACH_DATA")
        intent.addCategory("android.intent.category.DEFAULT")
        val str = "image/*"
        intent.setDataAndType(Uri.fromFile(File(url)), str)
        intent.putExtra("mimeType", str)
        startActivity(Intent.createChooser(intent, "Set As:"))
    }

    private fun checkStoragePermissions(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun setStoragePermissions() {
        storagePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }
}