package com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoFullscreenBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoFullscreenVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment

class PhotoFullscreenFragment: BaseFragment<FragmentPhotoFullscreenBinding>
    (R.layout.fragment_photo_fullscreen) {

    private val args: PhotoFullscreenFragmentArgs by navArgs()

    private val viewModel: PhotoFullscreenVM by lazy {
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
        if(savedInstanceState == null) {
            viewModel.photoUrl = args.photoUrl
        }
        binding.onBack = {
            Navigator.getInstance().navigateBack()
        }
    }
}