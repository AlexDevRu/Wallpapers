package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import androidx.databinding.Bindable
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM


class PhotoDetailVM: BaseVM() {
    val liveIntent = SingleLiveEvent<Intent>()

    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val fullImage: String?
        get() = if(photoItem?.localPhotoPath == null) photoItem?.regular else "file://${photoItem?.localPhotoPath}"

    @get:Bindable
    val toolbarTitle: String?
        get() = photoItem?.description

    fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${photoItem?.full}")
        sendIntent.type = "text/plain"
        liveIntent.value = sendIntent
    }
}