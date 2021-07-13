package com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentPhotoInfoBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.photo_detail.PhotoInfoVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
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
            //startActivity(it)
            if (it != null) {
                Navigator.getInstance().photoInfoFragmentNavigator.openLink(it)
            }
        })

        binding.onBack = {
            Navigator.getInstance().navigateBack()
        }

        startAnimations()
    }

    private fun startAnimations() {
        val translateBt = AnimationUtils.loadAnimation(context, R.anim.translate_bt)
        val scale = AnimationUtils.loadAnimation(context, R.anim.scale)
        binding.scrollView.startAnimation(translateBt)
        binding.backButton.startAnimation(scale)
    }
}