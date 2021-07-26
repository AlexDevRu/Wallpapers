package com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoDetailBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoDetailVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment: BaseFragment<FragmentPhotoDetailBinding>
    (R.layout.fragment_photo_detail) {

    private val viewModel: PhotoDetailVM by viewModels()

    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        if(savedInstanceState == null) {
            viewModel.init(args.photoItem?.model)
            startAnimations()
        }

        binding.delegate = object : Delegate {
            override fun onOpenDialog() {
                Navigator.getInstance().photoDetailFragmentNavigator.showPhotoFunctions(viewModel.photoItem)
            }

            override fun onShowFullscreen() {
                val extras = FragmentNavigatorExtras(
                    binding.imageView to "imageView",
                    binding.expandButton to "button"
                )
                Navigator.getInstance().photoDetailFragmentNavigator.showFullscreen(viewModel.photoItemVM.get()?.photoUrl, extras)
            }

            override fun onShare() {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, viewModel.photoItem.full)
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
            }

            override fun onBack() {
                Navigator.getInstance().navigateBack()
            }
        }

        viewModel.openLink.observe(viewLifecycleOwner, {
            if (it != null) {
                Navigator.getInstance().photoDetailFragmentNavigator.openLink(it)
            }
        })
    }

    private fun startAnimations() {
        val translateBt = AnimationUtils.loadAnimation(context, R.anim.translate_bt)
        binding.functions.startAnimation(translateBt)
    }

    interface Delegate {
        fun onOpenDialog()
        fun onShowFullscreen()
        fun onShare()
        fun onBack()
    }
}