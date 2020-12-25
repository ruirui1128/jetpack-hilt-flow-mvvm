package com.lihui.hilt.di


import android.content.Context
import android.os.Build
import com.lihui.hilt.BuildConfig
import com.lihui.hilt.uitl.AppPrefsUtils
import com.rui.libray.util.NetworkHelper

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
               .addHeader("version", BuildConfig.VERSION_NAME)
               .addHeader("model", Build.MODEL)
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

    @Provides
    @Singleton
    fun providerNetworkHelper(@ApplicationContext appContext: Context) = NetworkHelper(appContext)
}