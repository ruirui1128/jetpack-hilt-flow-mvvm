package com.mind.data.data.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleModel(
        @Bindable
        var url: String,
        val title: String,
        val subTitle: String,
        val type: String,
        var random: String,
        var isCollect: Boolean = false,
        var isJie: Boolean = false,
        var isHua:Boolean = false,
        var isFa :Boolean = false
) : Parcelable, BaseObservable()