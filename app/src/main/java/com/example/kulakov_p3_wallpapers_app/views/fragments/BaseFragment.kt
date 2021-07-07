package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<TViewModel: BaseVM, TBinding: ViewDataBinding>(
    @LayoutRes layout: Int
): Fragment(layout) {

    protected lateinit var navController: NavController
    protected abstract val viewModel: TViewModel
    protected lateinit var binding: TBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner

        navController = findNavController()
        viewModel.newDestination.singleObserve(viewLifecycleOwner) { direction ->
            if(direction != null) {
                navigate(direction)
            }
        }
    }

    protected fun navigate(direction: NavDirections) {
        navController.navigate(direction)
    }
}