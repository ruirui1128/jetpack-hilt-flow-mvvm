package com.lihui.hilt.data.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleModel(
   val url:String,
   val title:String,
   val subTitle:String,
   val type:String,
   val random:String,
   var isCollect:Boolean = false
): Parcelable,BaseObservable()