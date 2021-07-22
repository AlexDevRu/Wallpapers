package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoLoadStateAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.SearchVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>
    (R.layout.fragment_search) {

    private val viewModel: SearchVM by viewModels()

    private val args: SearchFragmentArgs by navArgs()

    private val adapter = PhotoAdapter()

    private var searchJob: Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        if(savedInstanceState == null && !args.searchQuery.isNullOrEmpty()) {
            viewModel.searchQuery.onNext(args.searchQuery!!)
        }

        adapter.addLoadStateListener { state ->
            viewModel.loading.set(state.refresh == LoadState.Loading)
            viewModel.error.set(if(state.refresh is LoadState.Error)
                (state.refresh as LoadState.Error).error.localizedMessage
            else null)
        }

        val headerAdapter = PhotoLoadStateAdapter { adapter.retry() }
        val footerAdapter = PhotoLoadStateAdapter { adapter.retry() }

        binding.photoList.adapter = adapter.withLoadStateHeaderAndFooter(
            headerAdapter, footerAdapter
        )

        (binding.photoList.layoutManager as GridLayoutManager).spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                    viewModel.columnListCount.get()
                } else {
                    1
                }
            }
        }

        observe()
    }

    private fun observe() {
        viewModel.collectData.observe(viewLifecycleOwner, {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch(Dispatchers.IO) {
                viewModel.searchPhotos().collectLatest {
                    adapter.submitData(it)
                }
            }
        })

        viewModel.scrollList.observe(viewLifecycleOwner, {
            if(it != null)
                binding.photoList.scrollToPosition(it)
        })

        internetObserver.observe(viewLifecycleOwner, {
            //viewModel.isNetworkAvailable.set(it)

        })
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveData()
    }
}