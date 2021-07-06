package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoLoadStateAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.SearchVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<SearchVM, FragmentSearchBinding>
    (R.layout.fragment_search) {

    override val viewModel: SearchVM by viewModels()

    private val args: SearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.searchQuery = args.searchQuery
        //binding.searchView.setQuery(viewModel.searchQuery, false)

        binding.photoList.adapter = viewModel.adapter.withLoadStateHeaderAndFooter(
            PhotoLoadStateAdapter(), PhotoLoadStateAdapter()
        )

        viewModel.livePhotoError.observe(viewLifecycleOwner, { message ->
            if(message != null) {
                Snackbar.make(binding.root,
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.searchByKeyword()
        viewModel.livePhotoSearch.observe(viewLifecycleOwner, {
            binding.photoList.scrollToPosition(0)
        })

        binding.photoList.layoutManager = GridLayoutManager(context, viewModel.columnListCount)
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestFocus()
    }
}