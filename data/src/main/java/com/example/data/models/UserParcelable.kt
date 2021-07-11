package com.example.data.models

import android.os.Parcelable
import com.example.domain.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserParcelable(val model: User?): Parcelable
