package com.lihui.hilt.data.api



import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.data.model.InfoModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.data.bean.Res
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import retrofit2.http.Path

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getPageList(@Path("page")page:String):Res<PageList<InfoModel>>

    @GET("banner/list")
    suspend fun getBanner(): Res<MutableList<BannerDataModel>>

    @POST("article/list")
    suspend fun getArticle(@Body map:HashMap<String,String>):Res<PageList<ArticleModel>>

    /**
     * 收藏
     */
    @GET("article/collect")
    suspend fun getCollect():Res<String>

}