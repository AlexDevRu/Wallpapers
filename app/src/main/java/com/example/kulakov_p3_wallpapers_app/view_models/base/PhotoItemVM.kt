package com.example.kulakov_p3_wallpapers_app.view_models.base

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.domain.models.PhotoItem
import java.text.SimpleDateFormat
import java.util.*

open class PhotoItemVM: BaseVM() {
    val photoItemObservable = ObservableField<PhotoItem>()

    var photoItem: PhotoItem?
        get() = photoItemObservable.get()
        set(value) {
            photoItemObservable.set(value)
            notifyChange()
        }

    @get:Bindable
    val photoUrl: String?
        get() = if(photoItem?.localPhotoPath == null) photoItem?.regular else "file://${photoItem?.localPhotoPath}"

    @get:Bindable
    val created: String?
        get() = if(photoItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(photoItem!!.created)
        else null

    @get:Bindable
    val userPhotoUrl: String?
        get() = if(photoItem?.user?.localPhotoPath == null)
            photoItem?.user?.photoUrl
        else "file://${photoItem?.user?.localPhotoPath}"


    @get:Bindable
    val hasInstagram: Boolean
        get() = !photoItem?.user?.instagram_username.isNullOrEmpty()

    @get:Bindable
    val hasTwitter: Boolean
        get() = !photoItem?.user?.twitter_username.isNullOrEmpty()

    @get:Bindable
    val hasPortfolio: Boolean
        get() = !photoItem?.user?.portfolio_url.isNullOrEmpty()

    @get:Bindable
    val hasDescription: Boolean
        get() = !photoItem?.user?.bio.isNullOrEmpty()
}