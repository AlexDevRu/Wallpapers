package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentSearchBinding
import com.example.kulakov_p3_wallpapers_app.view_models.SearchVM

class SearchFragment: BaseFragment<SearchVM>(R.layout.fragment_search, SearchVM::class.java) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var layoutManager: GridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.photoList.adapter = viewModel.adapter

        viewModel.searchByKeyword()
        viewModel.livePhotoSearch.observe(viewLifecycleOwner, {
            binding.photoList.scrollToPosition(0)
        })

        layoutManager = GridLayoutManager(context, 3)
        binding.photoList.layoutManager = layoutManager

        viewModel.liveColumnCount.observe(viewLifecycleOwner, {
            layoutManager.spanCount = it
        })
    }
}