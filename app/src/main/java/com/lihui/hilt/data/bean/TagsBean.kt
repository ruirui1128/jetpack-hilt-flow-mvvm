package com.lihui.hilt.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagsBean (
    var name:String, var url:String
): Parcelable