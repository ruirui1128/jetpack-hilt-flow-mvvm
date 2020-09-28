package com.lihui.hilt.data.api



import com.lihui.hilt.data.bean.InfoBean
import com.lihui.hilt.data.bean.PageList
import com.rui.libray.data.bean.Res
import retrofit2.http.GET

import retrofit2.http.Path

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getPageList(@Path("page")page:String):Res<PageList<InfoBean>>

}