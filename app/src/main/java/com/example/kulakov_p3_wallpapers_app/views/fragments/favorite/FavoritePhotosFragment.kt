package com.example.kulakov_p3_wallpapers_app.views.fragments.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.FavoriteImagesAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoritePhotosBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoritePhotosVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritePhotosFragment: BaseFragment<FragmentFavoritePhotosBinding>
    (R.layout.fragment_favorite_photos) {

    private val viewModel: FavoritePhotosVM by viewModels()

    private lateinit var adapter: FavoriteImagesAdapter

    private var getJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        adapter = FavoriteImagesAdapter(viewModel.repository)

        binding.favoritePhotosList.adapter = adapter

        getPhotos()
    }

    private fun getPhotos() {
        getJob?.cancel()
        getJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getFavoritePhotos().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}