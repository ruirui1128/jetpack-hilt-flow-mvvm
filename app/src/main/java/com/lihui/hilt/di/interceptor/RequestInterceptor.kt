package com.lihui.hilt.di.interceptor

import android.os.Build
import com.lihui.hilt.BuildConfig
import com.lihui.hilt.data.ds.DataStoreValue
import com.lihui.hilt.data.ds.DsUtil
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("version", BuildConfig.VERSION_NAME)
            .addHeader("model", Build.MODEL)
            .addHeader(DataStoreValue.TOKEN, DsUtil.getToken())
            .build()
      return  chain.proceed(request)
    }
}