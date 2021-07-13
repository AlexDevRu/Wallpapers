package com.example.kulakov_p3_wallpapers_app.navigators

import androidx.navigation.NavController

class Navigator private constructor(private val navController: NavController) {

    val searchFragmentNavigator = SearchFragmentNavigator(navController)
    val favoriteFragmentNavigator = FavoriteFragmentNavigator(navController)
    val historyFragmentNavigator = HistoryFragmentNavigator(navController)
    val photoDetailFragmentNavigator = PhotoDetailFragmentNavigator(navController)
    val photoInfoFragmentNavigator = PhotoInfoFragmentNavigator(navController)

    fun navigateBack() {
        navController.navigateUp()
    }

    companion object {
        private lateinit var INSTANCE: Navigator

        fun getInstance() = INSTANCE

        fun init(navController: NavController) {
            INSTANCE = Navigator(navController)
        }
    }
}
