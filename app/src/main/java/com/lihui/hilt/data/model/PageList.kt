package com.lihui.hilt.data.model

data class PageList<T>(
    val curPage:Int,
    val total:Int,
    val pageCount:Int,
    val list:MutableList<T>
)