package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.data.database.PhotoDao
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.utils.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.PhotoDetailFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PhotoDetailVM @Inject constructor(
    photoDao: PhotoDao
): BaseVM() {
    val liveNavigateBack = SingleLiveEvent<Boolean>()
    val liveIntent = MutableLiveData<Intent>()

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
        Log.w("asd", "click")
        liveNavigateBack.value = true
    }

    fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${photoItem?.full}")
        sendIntent.type = "text/plain"
        liveIntent.value = Intent.createChooser(sendIntent, "Поделиться")
    }

    fun openDialog() {
        val direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoFunctionsDialog(photoItem)
        newDestination.value = direction
    }

    val liveFullscreen = MutableLiveData<NavDirections>()

    fun navigateToFullScreen() {
        val direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoFullscreenFragment(photoItem?.regular)
        liveFullscreen.value = direction
        //newDestination.value = direction
    }

    fun navigateToInfo() {
        val direction = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToPhotoInfoFragment(photoItem)
        newDestination.value = direction
    }
}