package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoDetailBinding
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoDetailVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotoDetailFragment: BaseFragment<PhotoDetailVM, FragmentPhotoDetailBinding>
    (R.layout.fragment_photo_detail) {

    override val viewModel: PhotoDetailVM by viewModels()
    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.photoItem = args.photoItem
        viewModel.liveNavigateBack.observe(viewLifecycleOwner, {
            if(it != null) {
                navController.navigateUp()
            }
        })

        viewModel.liveIntent.observe(viewLifecycleOwner, {
            startActivity(it)
        })

        viewModel.liveFullscreen.observe(viewLifecycleOwner, {
            val extras = FragmentNavigatorExtras(
                binding.imageView to "imageView",
                binding.expandButton to "button"
            )
            navController.navigate(it, extras)
        })
    }
}