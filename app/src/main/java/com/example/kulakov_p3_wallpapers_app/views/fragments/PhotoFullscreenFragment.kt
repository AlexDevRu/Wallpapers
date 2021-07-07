package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoFullscreenBinding
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoFullscreenVM

class PhotoFullscreenFragment: BaseFragment<PhotoFullscreenVM, FragmentPhotoFullscreenBinding>
    (R.layout.fragment_photo_fullscreen) {

    private val args: PhotoFullscreenFragmentArgs by navArgs()

    override val viewModel: PhotoFullscreenVM by lazy {
        ViewModelProvider(this).get(PhotoFullscreenVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.photoUrl = args.photoUrl
        viewModel.liveNavigateBack.observe(viewLifecycleOwner, {
            navController.navigateUp()
        })
    }
}