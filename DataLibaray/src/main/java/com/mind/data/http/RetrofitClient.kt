package com.mind.data.http

import com.mind.data.config.AppConfig
import com.mind.data.di.interceptor.RequestInterceptor
import com.mind.data.di.interceptor.ResponseInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * create by Rui on 2022/4/14
 * desc:
 */
object RetrofitClient {

    //---------------------------------OkHttp-------------------------------
    @Volatile
    @JvmStatic
    private var _OKCLIENNT: OkHttpClient? = null

    @JvmStatic
    val okClient = _OKCLIENNT ?: synchronized(this) {
        _OKCLIENNT ?: kotlin.run {
            OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .addInterceptor(ResponseInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
            .also { _OKCLIENNT = it }
    }

    //-------------------------------Retrofit-----------------------------------
    @Volatile
    @JvmStatic
    private var _RETROFIT: Retrofit? = null

    @JvmStatic
    val retrofitClient = _RETROFIT ?: synchronized(this) {
        _RETROFIT ?: kotlin.run {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConfig.BASE_URL)
                .client(okClient)
                .build()
        }.also { _RETROFIT = it }
    }

}