package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteImagesBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteImagesVM

class FavoriteImagesFragment: BaseFragment<FavoriteImagesVM>(R.layout.fragment_favorite_images, FavoriteImagesVM::class.java) {
    private lateinit var binding: FragmentFavoriteImagesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
    }
}