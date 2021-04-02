package com.lihui.hilt.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lihui.hilt.data.room.dao.WordDao
import com.lihui.hilt.data.room.entity.WordEntity

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class HiltRoomBase : RoomDatabase() {
    abstract fun wordDao(): WordDao?
}