package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM


class PhotoDetailVM: BaseVM() {
    val photoItemVM = ObservableField<PhotoItemVM>()

    @get:Bindable
    val photoItem
        get() = photoItemVM.get()?.photoItem!!
}