package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM


class PhotoDetailVM: PhotoItemVM() {
    val liveIntent = SingleLiveEvent<Intent>()

    fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${photoItem?.full}")
        sendIntent.type = "text/plain"
        liveIntent.value = sendIntent
    }
}