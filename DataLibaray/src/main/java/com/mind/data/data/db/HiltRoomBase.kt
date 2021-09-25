package com.mind.data.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mind.data.data.db.dao.UserDao
import com.mind.data.data.db.dao.WordDao
import com.mind.data.data.db.entity.UserEntity
import com.mind.data.data.db.entity.WordEntity

/**
 * 实体类 一定要映射 entities 中
 */
@Database(entities = [WordEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class HiltRoomBase : RoomDatabase() {
    abstract fun wordDao(): WordDao?
    abstract fun userDao(): UserDao?
}