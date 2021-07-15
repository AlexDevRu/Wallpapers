package com.example.kulakov_p3_wallpapers_app.navigators

import androidx.navigation.NavController
import com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail.PhotoInfoFragmentDirections

class PhotoInfoFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun openLink(url: String, extras: androidx.navigation.Navigator.Extras? = null) {
        val direction = PhotoInfoFragmentDirections.actionPhotoInfoFragmentToWebViewFragment(url)
        navigate(direction, extras)
    }
}