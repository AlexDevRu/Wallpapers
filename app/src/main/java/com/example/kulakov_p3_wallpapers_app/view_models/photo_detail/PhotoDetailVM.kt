package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM


class PhotoDetailVM: BaseVM() {
    val photoItemVM = ObservableField<PhotoItemVM>()

    @get:Bindable
    val photoItem
        get() = photoItemVM.get()?.photoItem!!

    val liveIntent = SingleLiveEvent<Intent>()

    fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${photoItemVM.get()?.photoItem?.full}")
        sendIntent.type = "text/plain"
        liveIntent.value = sendIntent
    }
}