package com.mind.data.di


import com.mind.data.data.api.ApiService
import com.mind.data.data.api.SmsApi
import com.mind.data.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule  {
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