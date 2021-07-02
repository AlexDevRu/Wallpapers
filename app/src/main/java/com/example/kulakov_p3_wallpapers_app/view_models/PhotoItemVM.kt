package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.Bindable
import com.example.domain.data.PhotoItem

class PhotoItemVM: BaseVM() {
    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val thumb: String?
        get() = photoItem?.thumb
}