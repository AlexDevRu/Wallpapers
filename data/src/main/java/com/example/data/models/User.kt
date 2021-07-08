package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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