package com.lihui.hilt.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lihui.hilt.data.room.entity.WordEntity

@Dao
interface WordDao {
    @Insert()
    suspend fun insertWords(vararg words: WordEntity)

    @Query("SELECT * FROM tab_word ORDER BY word ASC")
    suspend fun getAllWords(): MutableList<WordEntity>?
}