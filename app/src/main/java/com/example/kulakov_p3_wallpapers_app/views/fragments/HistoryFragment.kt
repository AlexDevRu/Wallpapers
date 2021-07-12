package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.example.data.mappers.SearchItemMapper
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.SearchHistoryAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentHistoryBinding
import com.example.kulakov_p3_wallpapers_app.view_models.HistoryVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding>
    (R.layout.fragment_history) {

    private val viewModel: HistoryVM by viewModels()

    private lateinit var adapter: SearchHistoryAdapter

    private var readJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        adapter = SearchHistoryAdapter(viewModel.repository)

        binding.historyList.adapter = adapter

        getQueries()
    }

    private fun getQueries() {
        readJob?.cancel()
        readJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.flowQueries.collectLatest {
                adapter.submitData(it.map { SearchItemMapper.toModel(it) })
            }
        }

    }
}