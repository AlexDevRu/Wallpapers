package com.example.kulakov_p3_wallpapers_app.navigators

import androidx.navigation.NavController
import com.example.data.models.PhotoItemParcelable
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.views.fragments.FavoriteFragmentDirections

class FavoriteFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun showPhotoDetail(photoItem: PhotoItem?, extras: androidx.navigation.Navigator.Extras?) {
        val arg = PhotoItemParcelable(photoItem)
        val direction = FavoriteFragmentDirections.actionFavoriteFragmentToPhotoDetailFragment(arg)
        navigate(direction, extras)
    }

    fun showSearch(searchQuery: String?) {
        val direction = FavoriteFragmentDirections.actionFavoriteFragmentToSearchFragment(searchQuery)
        navigate(direction)
    }
}