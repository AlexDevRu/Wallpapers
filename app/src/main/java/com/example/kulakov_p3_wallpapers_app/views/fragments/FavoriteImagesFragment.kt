package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteImagesBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteImagesVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteImagesFragment: BaseFragment<FavoriteImagesVM, FragmentFavoriteImagesBinding>
    (R.layout.fragment_favorite_images) {

    override val viewModel: FavoriteImagesVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}