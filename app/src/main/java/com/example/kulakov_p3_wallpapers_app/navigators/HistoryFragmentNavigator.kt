package com.example.kulakov_p3_wallpapers_app.navigators

import androidx.navigation.NavController
import com.example.kulakov_p3_wallpapers_app.views.fragments.HistoryFragmentDirections

class HistoryFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun showSearch(searchQuery: String?) {
        val direction = HistoryFragmentDirections.actionHistoryFragmentToSearchFragment(searchQuery)
        navigate(direction)
    }
}