package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentHistoryBinding
import com.example.kulakov_p3_wallpapers_app.view_models.HistoryVM
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteSearchVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment: BaseFragment<HistoryVM, FragmentHistoryBinding>
    (R.layout.fragment_history) {

    override val viewModel: HistoryVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.getQueries()

        viewModel.liveQueriesLoading.observe(viewLifecycleOwner, {
            binding.historyList.scrollToPosition(0)
        })

        binding.historyList.adapter = viewModel.adapter
    }
}