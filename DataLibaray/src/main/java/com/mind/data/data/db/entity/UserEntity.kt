package com.mind.data.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    //对应表的字段名
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: String
)