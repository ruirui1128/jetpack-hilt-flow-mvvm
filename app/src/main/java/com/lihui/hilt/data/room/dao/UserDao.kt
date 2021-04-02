package com.lihui.hilt.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.lihui.hilt.data.room.entity.UserEntity

@Dao
interface UserDao {
    //增删改查

    @Insert()
    suspend fun insertWords(vararg user: UserEntity)
}