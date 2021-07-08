package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        viewModel.newDestination.singleObserve(viewLifecycleOwner) { navEvent ->
            if(navEvent != null) {
                navigate(navEvent)
            }
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            when(navController.currentDestination?.id) {
                R.id.searchFragment, R.id.favoriteFragment, R.id.historyFragment -> View.VISIBLE
                else -> View.GONE
            }
    }

    protected fun navigate(navEvent: NavigationEvent) {
        if(navEvent.direction != null) {
            if(navEvent.extras != null)
                navController.navigate(navEvent.direction!!, navEvent.extras!!)
            else
                navController.navigate(navEvent.direction!!)
        }
    }
}