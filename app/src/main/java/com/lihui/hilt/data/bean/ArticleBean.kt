package com.lihui.hilt.data.bean

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleBean(
    var apkLink: String,
    var author: String,//作者
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,//是否收藏
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var superChapterId: Int,
    var superChapterName: String,
    var shareUser: String,
    var tags: List<TagsBean>,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int
): Parcelable,BaseObservable()