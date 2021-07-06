package com.example.kulakov_p3_wallpapers_app.views.fragments.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteSearchVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteSearchFragment: BaseFragment<FavoriteSearchVM, FragmentFavoriteSearchBinding>
    (R.layout.fragment_favorite_search) {

    override val viewModel: FavoriteSearchVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.getQueries()

        viewModel.liveQueriesLoading.observe(viewLifecycleOwner, {
            binding.queriesList.scrollToPosition(0)
        })

        binding.queriesList.adapter = viewModel.adapter
    }
}