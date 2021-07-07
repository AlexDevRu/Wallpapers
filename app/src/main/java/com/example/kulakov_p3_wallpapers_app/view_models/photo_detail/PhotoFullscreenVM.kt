package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM

class PhotoFullscreenVM: BaseVM() {
    val liveNavigateBack = MutableLiveData<Boolean>()

    @Bindable
    var photoUrl: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.photoUrl)
        }

    fun navigateBack() {
        liveNavigateBack.value = true
    }
}