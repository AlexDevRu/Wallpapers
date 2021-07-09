package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoDetailBinding
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoDetailVM


class PhotoDetailFragment: BaseFragment<PhotoDetailVM, FragmentPhotoDetailBinding>
    (R.layout.fragment_photo_detail) {

    override val viewModel: PhotoDetailVM by lazy {
        ViewModelProvider(this).get(PhotoDetailVM::class.java)
    }
    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        if(savedInstanceState == null) {
            viewModel.photoItem = args.photoItem
        }

        binding.imageView.transitionName = viewModel.photoItem?.id

        viewModel.liveNavigateBack.observe(viewLifecycleOwner, {
            if(it != null) {
                navController.navigateUp()
            }
        })

        viewModel.liveIntent.observe(viewLifecycleOwner, {
            startActivity(Intent.createChooser(it, resources.getString(R.string.share)))
        })

        viewModel.fullScreenExtras = FragmentNavigatorExtras(
            binding.imageView to "imageView",
            binding.expandButton to "button"
        )

        binding.startTransition = { startPostponedEnterTransition() }

        binding.resizeWidth = binding.imageView.measuredWidth
        binding.resizeHeight = binding.imageView.height
    }
}