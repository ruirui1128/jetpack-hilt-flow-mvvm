package com.mind.data.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by rui
 *  on 2021/8/6
 *  数据库版本升级
 */
object Migration {
    val M_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //database.execSQL("ALTER TABLE user ADD COLUMN birthday INTEGER NOT NULL DEFAULT 23 ")
        }
    }
}