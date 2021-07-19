package com.example.data.parcelables

import android.os.Parcel
import android.os.Parcelable
import com.example.domain.models.User
import java.util.*

class UserParcelable(val model: User?): Parcelable {
    constructor(parcel: Parcel) : this(
        User(
            parcel.readString() ?: UUID.randomUUID().toString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(model?.id)
        parcel.writeString(model?.bio)
        parcel.writeString(model?.instagram_username)
        parcel.writeString(model?.name)
        parcel.writeString(model?.portfolio_url)
        parcel.writeString(model?.twitter_username)
        parcel.writeString(model?.username)
        parcel.writeString(model?.photoUrl)
        parcel.writeString(model?.localPhotoPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserParcelable> {
        override fun createFromParcel(parcel: Parcel): UserParcelable {
            return UserParcelable(parcel)
        }

        override fun newArray(size: Int): Array<UserParcelable?> {
            return arrayOfNulls(size)
        }
    }
}