package com.lihui.hilt.di


import android.os.Build
import com.lihui.hilt.BuildConfig
import com.lihui.hilt.uitl.AppPrefsUtils

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
class NetModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor = Interceptor { chain ->
           val request = chain.request()
               .newBuilder()
               .addHeader("charset", "UTF-8")
               .addHeader("Content-Type", "application/json")
               .addHeader("channel", "android")
               .addHeader("os", "android")
               .addHeader("version", BuildConfig.VERSION_NAME)
               .addHeader("maker", Build.BRAND)
               .addHeader("model", Build.MODEL)
               .addHeader("device", "device")
               .addHeader("Token", AppPrefsUtils.getString(AppPrefsUtils.TOKEN))
               .build()
           chain.proceed(request)
       }
       return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
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
}