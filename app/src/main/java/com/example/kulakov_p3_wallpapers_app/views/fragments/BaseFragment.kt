package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM

abstract class BaseFragment<TViewModel: BaseVM>(
    @LayoutRes layout: Int,
    private val vmClass: Class<TViewModel>
): Fragment(layout) {

    private lateinit var navController: NavController
    protected val viewModel: TViewModel by lazy {
        ViewModelProvider(this).get(vmClass)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        navController = findNavController()
        viewModel.newDestination.observe(
            viewLifecycleOwner
        ) { direction ->
            navigate(direction)
        }
    }

    private fun navigate(direction: NavDirections) {
        navController.navigate(direction)
    }
}