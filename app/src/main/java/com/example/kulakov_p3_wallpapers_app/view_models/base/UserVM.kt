package com.example.kulakov_p3_wallpapers_app.view_models.base

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.domain.files.IFileProvider
import com.example.domain.models.User

class UserVM(private val fileProvider: IFileProvider? = null): BaseObservable() {
    @Bindable
    var user: User? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val userPhotoUrl: String?
        get() {
            if(user != null && fileProvider != null) {
                val path = fileProvider.getUserFilePath(user!!)
                if(path != null)
                    return "file://${path}"
            }
            return user?.photoUrl
        }

    @get:Bindable
    val hasInstagram: Boolean
        get() = !user?.instagram_username.isNullOrEmpty()

    @get:Bindable
    val hasTwitter: Boolean
        get() = !user?.twitter_username.isNullOrEmpty()

    @get:Bindable
    val hasPortfolio: Boolean
        get() = !user?.portfolio_url.isNullOrEmpty()

    @get:Bindable
    val hasDescription: Boolean
        get() = !user?.bio.isNullOrEmpty()
}