package com.lihui.hilt.data.api

import com.lihui.hilt.data.bean.UserBean
import com.rui.libray.data.bean.Res
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 用户相关
 */
interface UserApi {
    @POST("login")
    suspend fun login(@Body map: HashMap<String,String>):Res<String>

    @GET("userInfo")
    suspend fun getUserInfo():Res<UserBean>
}