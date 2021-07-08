package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoInfoBinding
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoInfoVM

class PhotoInfoFragment: BaseFragment<PhotoInfoVM, FragmentPhotoInfoBinding>
    (R.layout.fragment_photo_info) {

    private val args: PhotoInfoFragmentArgs by navArgs()

    override val viewModel: PhotoInfoVM by lazy {
        ViewModelProvider(this).get(PhotoInfoVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        if(savedInstanceState == null) {
            viewModel.photoItem = args.photoItem
        }

        viewModel.liveNavigateBack.singleObserve(viewLifecycleOwner, {
            if(it != null)
                navController.navigateUp()
        })

        viewModel.liveIntent.observe(viewLifecycleOwner, {
            startActivity(it)
        })
    }
}