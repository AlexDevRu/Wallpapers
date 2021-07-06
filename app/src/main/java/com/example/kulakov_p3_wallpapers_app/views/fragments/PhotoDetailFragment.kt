package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoDetailBinding
import com.example.kulakov_p3_wallpapers_app.view_models.PhotoDetailVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment: BaseFragment<PhotoDetailVM, FragmentPhotoDetailBinding>
    (R.layout.fragment_photo_detail) {

    override val viewModel: PhotoDetailVM by viewModels()

    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.photoItem = args.photoItem
        viewModel.liveNavigateBack.observe(viewLifecycleOwner, { direction ->
            navigate(direction)
        })
    }
}