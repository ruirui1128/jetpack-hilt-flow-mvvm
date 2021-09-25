package com.mind.data.di.interceptor

import android.os.Build
import com.mind.lib.util.CacheManager
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("version", CacheManager.instance.getVersion())
            .addHeader("model", Build.MODEL)
            .addHeader("token", CacheManager.instance.getToken())
            .build()
      return  chain.proceed(request)
    }
}