package com.example.kulakov_p3_wallpapers_app.tab_adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kulakov_p3_wallpapers_app.views.fragments.favorite.FavoritePhotosFragment
import com.example.kulakov_p3_wallpapers_app.views.fragments.favorite.FavoriteSearchFragment

class FavoriteAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifeCycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if(position == 0) return FavoritePhotosFragment()
        return FavoriteSearchFragment()
    }
}