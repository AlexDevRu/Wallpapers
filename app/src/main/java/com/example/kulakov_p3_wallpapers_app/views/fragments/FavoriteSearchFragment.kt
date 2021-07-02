package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteSearchVM

class FavoriteSearchFragment: BaseFragment<FavoriteSearchVM>(R.layout.fragment_favorite_search, FavoriteSearchVM::class.java) {
    private lateinit var binding: FragmentFavoriteSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
    }
}