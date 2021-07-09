package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigator
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.utils.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.PhotoDetailFragmentDirections


class PhotoDetailVM: BaseVM() {
    val liveNavigateBack = SingleLiveEvent<Boolean>()
    val liveIntent = MutableLiveData<Intent>()

    var fullScreenExtras: FragmentNavigator.Extras? = null

    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val fullImage: String?
        get() = photoItem?.regular

    @get:Bindable
    val toolbarTitle: String?
        get() = photoItem?.description


    fun navigateBack() {
        liveNavigateBack.value = true
    }

    fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${photoItem?.full}")
        sendIntent.type = "text/plain"
        liveIntent.value = sendIntent
    }

    fun openDialog() {
        val event = NavigationEvent()
        event.direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoFunctionsDialog(photoItem)
        newDestination.value = event
    }

    fun navigateToFullScreen() {
        val event = NavigationEvent()
        event.direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoFullscreenFragment(photoItem?.regular)
        event.extras = fullScreenExtras
        newDestination.value = event
    }

    fun navigateToInfo() {
        val event = NavigationEvent()
        event.direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoInfoFragment(photoItem)
        newDestination.value = event
    }
}