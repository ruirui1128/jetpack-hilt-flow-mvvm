package com.mind.data.http

import com.mind.data.data.api.ApiService
import com.mind.data.data.api.UserApi
import com.mind.data.http.RetrofitClient.retrofitClient

/**
 * create by Rui on 2022/4/14
 * desc: http api 初始化
 */
object ApiClient {


    // api 可自行扩展

    @Volatile
    @JvmStatic
    private var _USERAPI: UserApi? = null

    @Volatile
    @JvmStatic
    private var _AppAPI: ApiService? = null


    @JvmStatic
    val appApi = _AppAPI ?: synchronized(this) {
        _AppAPI ?: retrofitClient.create(ApiService::class.java).also { _AppAPI = it }
    }

    @JvmStatic
    val userApi = _USERAPI ?: synchronized(this) {
        _USERAPI ?: retrofitClient.create(UserApi::class.java).also { _USERAPI = it }
    }
}