package com.mind.data.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mind.data.data.db.entity.WordEntity

@Dao
interface WordDao {
    @Insert()
    suspend fun insertWords(vararg words: WordEntity)

    @Query("SELECT * FROM tab_word ORDER BY word ASC")
    suspend fun getAllWords(): MutableList<WordEntity>?
}