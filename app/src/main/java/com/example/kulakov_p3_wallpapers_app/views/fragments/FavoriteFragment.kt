package com.example.kulakov_p3_wallpapers_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentFavoriteBinding
import com.example.kulakov_p3_wallpapers_app.tab_adapters.FavoriteAdapter
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment
import com.google.android.material.tabs.TabLayout

class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.adapter = FavoriteAdapter(childFragmentManager, lifecycle)

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
