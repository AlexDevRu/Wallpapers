package com.example.data.parcelables

import android.os.Parcel
import android.os.Parcelable
import com.example.domain.models.SearchItem
import java.util.*

class SearchItemParcelable(val model: SearchItem?): Parcelable {
    constructor(parcel: Parcel) : this(
        SearchItem(
            UUID.fromString(parcel.readString()),
            parcel.readString(),
            parcel.readInt(),
            parcel.readSerializable() as Date,
            parcel.readInt() == 1
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(model?.id.toString())
        parcel.writeString(model?.query)
        parcel.writeInt(model?.resultsCount ?: 0)
        parcel.writeSerializable(model?.date)
        parcel.writeInt(if(model?.isFavorite == true) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchItemParcelable> {
        override fun createFromParcel(parcel: Parcel): SearchItemParcelable {
            return SearchItemParcelable(parcel)
        }

        override fun newArray(size: Int): Array<SearchItemParcelable?> {
            return arrayOfNulls(size)
        }
    }
}