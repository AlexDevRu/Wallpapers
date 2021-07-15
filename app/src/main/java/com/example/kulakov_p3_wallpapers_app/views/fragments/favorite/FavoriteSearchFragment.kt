package com.example.kulakov_p3_wallpapers_app.views.fragments.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.data.aliases.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.FavoriteSearchItemsAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteSearchVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteSearchFragment: BaseFragment<FragmentFavoriteSearchBinding>
    (R.layout.fragment_favorite_search) {

    private val viewModel: FavoriteSearchVM by viewModels()

    private lateinit var adapter: FavoriteSearchItemsAdapter

    private var getJob: Job? = null

    @Inject
    lateinit var updateQueryUseCase: UpdateQueryUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        adapter = FavoriteSearchItemsAdapter(updateQueryUseCase)
        binding.queriesList.adapter = adapter
        getQueries()
    }

    private fun getQueries() {
        getJob?.cancel()
        getJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getFavoriteQueries().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}