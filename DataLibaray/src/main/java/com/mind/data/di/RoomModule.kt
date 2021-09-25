package com.mind.data.di

import android.content.Context
import androidx.room.Room
import com.mind.data.data.db.HiltRoomBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideHiltRoomBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HiltRoomBase::class.java, "hilt_room").build()


    /**
     * 注入 WordDao
     */
    @Provides
    @Singleton
    fun provideWordDao(roomBase: HiltRoomBase) = roomBase.wordDao()

    /**
     * 注入 UserDao
     */
    @Provides
    @Singleton
    fun provideUserDao(roomBase: HiltRoomBase) = roomBase.userDao()

}