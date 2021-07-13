package com.example.kulakov_p3_wallpapers_app.navigators

import androidx.navigation.NavController
import com.example.data.parcelables.PhotoItemParcelable
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail.PhotoDetailFragmentDirections

class PhotoDetailFragmentNavigator(navController: NavController): BaseNavigator(navController) {
    fun showInfo(photoItem: PhotoItem?) {
        val arg = PhotoItemParcelable(photoItem)
        val direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoInfoFragment(arg)
        navigate(direction)
    }

    fun showFullscreen(photoUrl: String?, extras: androidx.navigation.Navigator.Extras? = null) {
        val direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoFullscreenFragment(photoUrl)
        navigate(direction, extras)
    }

    fun showPhotoFunctions(photoItem: PhotoItem?) {
        val arg = PhotoItemParcelable(photoItem)
        val direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoFunctionsDialog(arg)
        navigate(direction)
    }
}