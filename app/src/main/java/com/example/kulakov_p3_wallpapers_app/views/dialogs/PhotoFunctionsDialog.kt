package com.example.kulakov_p3_wallpapers_app.views.dialogs

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.DialogPhotoFunctionsBinding
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoFunctionsVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class PhotoFunctionsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogPhotoFunctionsBinding
    private val viewModel: PhotoFunctionsVM by viewModels()
    private val args: PhotoFunctionsDialogArgs by navArgs()
    private lateinit var wm: WallpaperManager

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveSetWallpapers.observe(viewLifecycleOwner, {
            wm.setBitmap(it)
        })

        viewModel.liveSetLockScreen.observe(viewLifecycleOwner, {
            Log.e("asd", "api level ${Build.VERSION.SDK_INT}")

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.e("asd", "set wallpapers lock")
                if(wm.isSetWallpaperAllowed) {
                    wm.setBitmap(it, null, true, WallpaperManager.FLAG_LOCK)
                } else {
                    Toast.makeText(context, "not supported", Toast.LENGTH_SHORT).show()
                }
            } else {
                if(viewModel.photoItem?.regular != null) {
                    val intent = Intent("android.intent.action.ATTACH_DATA")
                    intent.addCategory("android.intent.category.DEFAULT")
                    val str = "image/*"
                    intent.setDataAndType(Uri.fromFile(File(viewModel.photoItem!!.regular!!)), str)
                    intent.putExtra("mimeType", str)
                    startActivity(Intent.createChooser(intent, "Set As:"))
                }
            }
        })

        viewModel.closeDialog.observe(viewLifecycleOwner, {
            if(it) dismiss()
        })
    }
}