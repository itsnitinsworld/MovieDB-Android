package com.themoviedb.ui.fragment.intro.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author- Nitin Khanna
 * @date - 19-11-2020
 */
data class IntroPagerModel(
    val imageSource:Int,
    val titleText:String,
    val descText:String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageSource)
        parcel.writeString(titleText)
        parcel.writeString(descText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IntroPagerModel> {
        override fun createFromParcel(parcel: Parcel): IntroPagerModel {
            return IntroPagerModel(parcel)
        }

        override fun newArray(size: Int): Array<IntroPagerModel?> {
            return arrayOfNulls(size)
        }
    }
}