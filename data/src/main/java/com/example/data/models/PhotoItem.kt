package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PhotoItem(
    var id: String,
    var width: Int = 0,
    var height: Int = 0,
    var created: Date = Date(),
    var color: String = "",
    var description: String = "",
    var thumb: String = "",
    var regular: String = "",
    var full: String = "",
    var userIsLoaded: Boolean = false,
    var user: User
): Parcelable