package com.example.kulakov_p3_wallpapers_app.view_models.base

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.domain.models.PhotoItem
import com.example.domain.files.IFileProvider
import java.text.SimpleDateFormat
import java.util.*

open class PhotoItemVM(private val fileProvider: IFileProvider? = null): BaseVM() {

    @Bindable
    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.photoItem)
        }

    fun isPhotoSaved(): Boolean {
        if(photoItem != null && fileProvider != null) {
            val path = fileProvider.getPhotoItemFilePath(photoItem!!)
            return path != null
        }
        return false
    }

    @get:Bindable
    val photoUrl: String?
        get() {
            if(photoItem != null && fileProvider != null) {
                val path = fileProvider.getPhotoItemFilePath(photoItem!!)
                Log.e("asd", "path $path")
                if(path != null)
                    return "file://${path}"
            }

            return photoItem?.regular
        }

    @get:Bindable
    val created: String?
        get() = if(photoItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(photoItem!!.created)
        else null

    @get:Bindable
    val userPhotoUrl: String?
        get() {
            if(photoItem?.user != null && fileProvider != null) {
                val path = fileProvider.getUserFilePath(photoItem!!.user!!)
                if(path != null)
                    return "file://${path}"
            }
            return photoItem?.user?.photoUrl
        }


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