package com.lihui.hilt.di


import android.content.Context
import com.lihui.hilt.BuildConfig

import com.lihui.hilt.di.interceptor.RequestInterceptor
import com.lihui.hilt.di.interceptor.ResponseInterceptor

import com.rui.libray.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        // --------https校验  支持单项校验和双向校验--------------------
//        val sslParams = HttpsUtils.getSslSocketFactory(
//            // assets 目录下的.cer 文件 后台或运维给的文件
//            MyApp.getApp().assets.open("xxx.cer")
//        )
//        if (sslParams.sSLSocketFactory != null &&
//            sslParams.trustManager != null
//        ) {
//            builder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
//            builder.hostnameVerifier(SafeHostnameVerifier())
//        }
        return builder
            .addInterceptor(RequestInterceptor())
            .addInterceptor(ResponseInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providerNetworkHelper(@ApplicationContext appContext: Context) = NetworkHelper(appContext)

    /**
     * 验证主机名是否匹配
     *
     * "https://baidu.com/getUerInfo"
     *  主机名为 baidu.com
     */
    private class SafeHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            return (hostname == "baidu.com")
        }
    }

}