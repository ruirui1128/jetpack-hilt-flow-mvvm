package com.lihui.hilt.data.api



import com.lihui.hilt.data.bean.ArticleBean
import com.lihui.hilt.data.bean.BannerDataBean
import com.lihui.hilt.data.bean.InfoBean
import com.lihui.hilt.data.bean.PageList
import com.rui.libray.data.bean.Res
import retrofit2.http.GET

import retrofit2.http.Path

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getPageList(@Path("page")page:String):Res<PageList<InfoBean>>

    @GET("banner/json")
    suspend fun getBanner(): Res<MutableList<BannerDataBean>>

    @GET("/article/list/{page}/json")
    suspend fun getArticle(@Path("page")page:String):Res<PageList<ArticleBean>>

}