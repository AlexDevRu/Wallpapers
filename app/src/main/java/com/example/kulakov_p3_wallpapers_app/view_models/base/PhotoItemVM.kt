package com.example.kulakov_p3_wallpapers_app.view_models.base

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.domain.files.IFileProvider
import com.example.domain.models.PhotoItem
import java.text.SimpleDateFormat
import java.util.*

open class PhotoItemVM(photoItem: PhotoItem? = null, private val fileProvider: IFileProvider? = null): BaseObservable() {

    @Bindable
    var photoItem: PhotoItem? = photoItem
        set(value) {
            field = value
            userVM.user = field?.user
            notifyPropertyChanged(BR.photoItem)
        }

    @get:Bindable
    var userVM: UserVM = UserVM(fileProvider)

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
}