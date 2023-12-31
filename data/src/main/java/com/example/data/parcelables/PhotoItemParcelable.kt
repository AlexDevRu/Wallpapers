package com.example.data.parcelables

import android.os.Parcel
import android.os.Parcelable
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import java.util.*

class PhotoItemParcelable(val model: PhotoItem?): Parcelable {
    constructor(parcel: Parcel) : this(
        PhotoItem(
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readSerializable() as Date,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readSerializable() as Date,
            parcel.readInt() == 1,
            parcel.readParcelable(User::class.java.classLoader)
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(model?.id)
        parcel.writeInt(model?.width ?: 0)
        parcel.writeInt(model?.height ?: 0)
        parcel.writeSerializable(model?.created)
        parcel.writeString(model?.color)
        parcel.writeString(model?.description)
        parcel.writeString(model?.thumb)
        parcel.writeString(model?.regular)
        parcel.writeString(model?.full)
        parcel.writeSerializable(model?.addedToFavorite)
        parcel.writeInt(if(model?.isFavorite == true) 1 else 0)
        parcel.writeParcelable(UserParcelable(model?.user), 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItemParcelable> {
        override fun createFromParcel(parcel: Parcel): PhotoItemParcelable {
            return PhotoItemParcelable(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItemParcelable?> {
            return arrayOfNulls(size)
        }
    }
}

