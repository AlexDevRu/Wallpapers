package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import androidx.databinding.Bindable
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM

class PhotoInfoVM: BaseVM() {
    @Bindable
    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }
}