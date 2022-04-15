package com.mind.data.data.api


import com.mind.data.data.model.LoginModel
import com.mind.data.data.model.UserModel
import com.mind.lib.data.model.Res
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 用户相关
 */
interface UserApi {
    /**
     * 登录
     */
    @POST("api/login")
    suspend fun login(@Body map: HashMap<String, String>): Res<LoginModel?>


    /**
     * 收藏
     */
    @GET("article/collect")
    suspend fun getCollect(): Res<String>

    /**
     * 更换头像
     */
    @GET("article/header")
    suspend fun changeHeader(): Res<String>

    /**
     * 接化发
     */
    @GET("article/jhf")
    suspend fun jhf(): Res<String>

    /**
     * 获取用户信息
     */
    @GET("user/info")
    suspend fun getUserInfo(): Res<UserModel>

}