package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.SearchHistoryAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentHistoryBinding
import com.example.kulakov_p3_wallpapers_app.view_models.HistoryVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
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

    private var getJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        adapter = SearchHistoryAdapter(viewModel.repository)

        binding.historyList.adapter = adapter

        getQueries()
    }

    private fun getQueries() {
        getJob?.cancel()
        getJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getQueries().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}