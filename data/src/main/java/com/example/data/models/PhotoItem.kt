package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PhotoItem(
    var id: String =  "",
    var width: Int = 0,
    var height: Int = 0,
    var created: Date = Date(),
    var color: String = "",
    var description: String = "",
    var thumb: String = "",
    var regular: String = "",
    var full: String = "",
    var user: User = User()
): Parcelable {
    @Parcelize
    data class User(
        var id: String? = null,
        var bio: String? = null,
        var instagram_username: String? = null,
        var name: String? = null,
        var portfolio_url: String? = null,
        var twitter_username: String? = null,
        var username: String? = null,
        var photoUrl: String? = null
    ): Parcelable
}