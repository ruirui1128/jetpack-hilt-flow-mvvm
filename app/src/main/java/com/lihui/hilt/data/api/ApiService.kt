package com.lihui.hilt.data.api



import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.data.model.InfoModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.data.bean.Res
import retrofit2.http.GET

import retrofit2.http.Path

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getPageList(@Path("page")page:String):Res<PageList<InfoModel>>

    @GET("banner/json")
    suspend fun getBanner(): Res<MutableList<BannerDataModel>>

    @GET("/article/list/{page}/json")
    suspend fun getArticle(@Path("page")page:String):Res<PageList<ArticleModel>>

}