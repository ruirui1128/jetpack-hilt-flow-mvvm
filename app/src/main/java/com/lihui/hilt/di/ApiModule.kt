package com.lihui.hilt.di

import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.api.SmsApi
import com.lihui.hilt.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideSmsApi(retrofit: Retrofit): SmsApi = retrofit.create(SmsApi::class.java)
}