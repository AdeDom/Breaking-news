package com.adedom.breakingnews.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class DetailModel(
    val id: Long,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailModel> {
        override fun createFromParcel(parcel: Parcel): DetailModel {
            return DetailModel(parcel)
        }

        override fun newArray(size: Int): Array<DetailModel?> {
            return arrayOfNulls(size)
        }
    }
}
