package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoLoadStateAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.SearchVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>
    (R.layout.fragment_search) {

    private val viewModel: SearchVM by viewModels()

    private val args: SearchFragmentArgs by navArgs()

    private val adapter = PhotoAdapter()

    private var searchJob: Job? = null


    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        if(savedInstanceState == null) {
            viewModel.searchQuery = args.searchQuery
        }

        postponeEnterTransition()
        binding.photoList.post { startPostponedEnterTransition() }

        adapter.addLoadStateListener { state ->
            viewModel.loading = state.refresh == LoadState.Loading
            viewModel.error =
                if(state.refresh is LoadState.Error)
                    (state.refresh as LoadState.Error).error.localizedMessage
                else null
        }

        binding.photoList.adapter = adapter.withLoadStateHeaderAndFooter(
            PhotoLoadStateAdapter(), PhotoLoadStateAdapter()
        )

        search()
    }

    @FlowPreview
    private fun search() {
        viewModel.collectData.observe(viewLifecycleOwner, {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch(Dispatchers.IO) {
                if(!viewModel.initialSearch) {
                    viewModel.searchPhotos().debounce(1500).collectLatest {
                        adapter.submitData(it)
                    }
                } else {
                    viewModel.searchPhotos().collectLatest {
                        adapter.submitData(it)
                    }
                }
                viewModel.initialSearch = false
            }
        })

        viewModel.scrollList.observe(viewLifecycleOwner, {
            binding.photoList.scrollToPosition(it)
        })
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestFocus()
    }
}