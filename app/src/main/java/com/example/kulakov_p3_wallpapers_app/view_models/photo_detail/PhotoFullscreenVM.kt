package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.kulakov_p3_wallpapers_app.utils.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM

class PhotoFullscreenVM: BaseVM() {
    val liveNavigateBack = SingleLiveEvent<Boolean>()

    @Bindable
    var photoUrl: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.photoUrl)
        }

    fun navigateBack() {
        Log.e("asd", "fullscreen back click")
        liveNavigateBack.value = true
    }
}