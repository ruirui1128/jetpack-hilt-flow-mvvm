package com.mind.data.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by rui
 *  on 2021/8/10
 */
interface LoadApi {

    @Streaming
    @GET
    fun download(
        @Url url: String,
        @QueryMap map: HashMap<String, String> = HashMap()
    ): Call<ResponseBody>
}