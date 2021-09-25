package com.mind.data.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagsModel (
    var name:String, var url:String
): Parcelable