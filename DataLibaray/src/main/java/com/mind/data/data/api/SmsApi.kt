package com.mind.data.data.api

import com.mind.lib.data.model.Res
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 短信相关接口
 */
interface SmsApi {
    @POST("sms-send")
    suspend fun sendSmsMsg(@Body hashMap: HashMap<String,String>) : Res<String>
}