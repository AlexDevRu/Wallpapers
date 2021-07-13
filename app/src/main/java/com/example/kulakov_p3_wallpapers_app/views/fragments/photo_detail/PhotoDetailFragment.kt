package com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoDetailBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoDetailVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment


class PhotoDetailFragment: BaseFragment<FragmentPhotoDetailBinding>
    (R.layout.fragment_photo_detail) {

    private val viewModel: PhotoDetailVM by lazy {
        ViewModelProvider(this).get(PhotoDetailVM::class.java)
    }

    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        //sharedElementEnterTransition = transition
        //sharedElementReturnTransition = transition
        //postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        if(savedInstanceState == null) {
            viewModel.photoItem = args.photoItem?.model
        }

        binding.imageView.transitionName = viewModel.photoItem?.id

        viewModel.liveIntent.observe(viewLifecycleOwner, {
            startActivity(Intent.createChooser(it, resources.getString(R.string.share)))
        })

        binding.delegate = object : Delegate {
            override fun onOpenDialog() {
                Navigator.getInstance().photoDetailFragmentNavigator.showPhotoFunctions(viewModel.photoItem)
            }

            override fun onShowFullscreen() {
                val extras = FragmentNavigatorExtras(
                    binding.imageView to "imageView",
                    binding.expandButton to "button"
                )
                Navigator.getInstance().photoDetailFragmentNavigator.showFullscreen(viewModel.photoItem?.regular, extras)
            }

            override fun onOpenInfo() {
                Navigator.getInstance().photoDetailFragmentNavigator.showInfo(viewModel.photoItem)
            }

            override fun onBack() {
                Navigator.getInstance().navigateBack()
            }
        }

        binding.startTransition = { startPostponedEnterTransition() }

        startAnimations()
    }

    private fun startAnimations() {
        val translateBt = AnimationUtils.loadAnimation(context, R.anim.translate_bt)
        binding.functions.startAnimation(translateBt)
    }

    interface Delegate {
        fun onOpenDialog()
        fun onShowFullscreen()
        fun onOpenInfo()
        fun onBack()
    }
}