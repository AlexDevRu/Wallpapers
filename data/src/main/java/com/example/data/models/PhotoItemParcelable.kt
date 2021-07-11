package com.example.data.models

import android.os.Parcelable
import com.example.domain.models.PhotoItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItemParcelable(val model: PhotoItem?): Parcelable
