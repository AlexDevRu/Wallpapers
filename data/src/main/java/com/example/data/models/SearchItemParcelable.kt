package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.example.domain.models.SearchItem

@Parcelize
data class SearchItemParcelable(val model: SearchItem?): Parcelable
