package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteBinding
import com.example.kulakov_p3_wallpapers_app.view_models.FavoriteVM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FavoriteFragment: BaseFragment<FavoriteVM>(R.layout.fragment_favorite, FavoriteVM::class.java) {
    private lateinit var binding: FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        val adapter = FavoriteAdapter(childFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null) {
                    binding.viewpager.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabs.selectTab(binding.tabs.getTabAt(position))
            }
        })
    }
}

class FavoriteAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifeCycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if(position == 0) return FavoriteImagesFragment()
        return FavoriteSearchFragment()
    }
}