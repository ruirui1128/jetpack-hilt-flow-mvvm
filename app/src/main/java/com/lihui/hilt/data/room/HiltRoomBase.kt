package com.lihui.hilt.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lihui.hilt.data.room.dao.UserDao
import com.lihui.hilt.data.room.dao.WordDao
import com.lihui.hilt.data.room.entity.UserEntity
import com.lihui.hilt.data.room.entity.WordEntity

/**
 * 实体类 一定要映射 entities 中
 */
@Database(entities = [WordEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class HiltRoomBase : RoomDatabase() {
    abstract fun wordDao(): WordDao?
    abstract fun userDao(): UserDao?
}