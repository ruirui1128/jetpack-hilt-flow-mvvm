package com.lihui.hilt.data.bean

data class PageList<T>(
    val curPage:Int,
    val total:Int,
    val pageCount:Int,
    val datas:MutableList<T>
)