package com.mind.data.data.api




import com.mind.data.data.model.ArticleModel
import com.mind.data.data.model.BannerDataModel
import com.mind.data.data.model.InfoModel
import com.mind.data.data.model.PageList
import com.mind.lib.data.model.Res
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

import retrofit2.http.Path

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getPageList(@Path("page")page:String): Res<PageList<InfoModel>>

    @GET("banner/list")
    suspend fun getBanner(): Res<MutableList<BannerDataModel>>

    @POST("article/list")
    suspend fun getArticle(@Body map:HashMap<String,String>):Res<PageList<ArticleModel>>


}