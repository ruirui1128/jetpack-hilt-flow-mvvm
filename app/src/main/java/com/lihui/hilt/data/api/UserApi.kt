package com.lihui.hilt.data.api

import com.lihui.hilt.data.model.LoginModel
import com.lihui.hilt.data.model.UserModel
import com.rui.libray.data.bean.Res
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 用户相关
 */
interface UserApi {
    @POST("api/login")
    suspend fun login(@Body map: HashMap<String,String>):Res<LoginModel?>

    @GET("userInfo")
    suspend fun getUserInfo():Res<UserModel>
}