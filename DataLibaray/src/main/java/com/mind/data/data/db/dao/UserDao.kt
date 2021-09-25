package com.mind.data.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mind.data.data.db.entity.UserEntity

@Dao
interface UserDao {
    //增删改查
    @Insert()
    suspend fun insertWords(vararg user: UserEntity)


}