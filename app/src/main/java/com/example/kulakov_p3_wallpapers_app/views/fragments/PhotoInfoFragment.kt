package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoInfoBinding
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoInfoVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoInfoFragment: BaseFragment<FragmentPhotoInfoBinding>
    (R.layout.fragment_photo_info) {

    private val args: PhotoInfoFragmentArgs by navArgs()

    private val viewModel: PhotoInfoVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        if(savedInstanceState == null) {
            viewModel.photoItem = args.photoItem?.model
        }

        viewModel.liveIntent.observe(viewLifecycleOwner, {
            startActivity(it)
        })

        binding.onBack = {
            findNavController().navigateUp()
        }
    }
}