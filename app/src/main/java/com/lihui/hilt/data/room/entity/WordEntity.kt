package com.lihui.hilt.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//表名
@Entity(tableName = "tab_word")
 class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    //对应表的字段名
    @ColumnInfo(name = "word")
    val word: String
)