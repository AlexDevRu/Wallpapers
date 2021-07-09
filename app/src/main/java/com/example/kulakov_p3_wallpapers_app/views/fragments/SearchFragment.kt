package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoLoadStateAdapter
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.SearchVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment: BaseFragment<SearchVM, FragmentSearchBinding>
    (R.layout.fragment_search) {

    override val viewModel: SearchVM by viewModels()

    private val args: SearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        if(savedInstanceState == null) {
            viewModel.searchQuery.onNext(args.searchQuery.orEmpty())
        }

        binding.photoList.adapter = viewModel.adapter.withLoadStateHeaderAndFooter(
            PhotoLoadStateAdapter(), PhotoLoadStateAdapter()
        )

        viewModel.livePhotoError.observe(viewLifecycleOwner, { message ->
            if(!message.isNullOrEmpty()) {
                Snackbar.make(binding.root,
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        binding.photoList.layoutManager = GridLayoutManager(context, viewModel.columnListCount)
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestFocus()
    }
}