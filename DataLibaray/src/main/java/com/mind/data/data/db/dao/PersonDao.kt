package com.mind.data.data.db.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mind.data.data.db.entity.PersonEntityModel

/**
 * Created by rui
 *  on 2021/8/16
 */
@Dao
interface PersonDao {

    @Insert
    suspend fun insertPerson(vararg person: PersonEntityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(persons: MutableList<PersonEntityModel>)

    /**
     * 查询
     */
    @Query("SELECT * FROM tab_person WHERE person_id=:personId")
    suspend fun get(personId: String): MutableList<PersonEntityModel?>?

    /**
     * 查询所有
     */
    @Query("SELECT * FROM tab_person")
    suspend fun get(): MutableList<PersonEntityModel?>?


    /**
     * 修改
     */
    @Update()
    suspend fun update(person: PersonEntityModel?)


    /**
     * 删除
     */
    @Delete()
    suspend fun delete(sup: PersonEntityModel?)

    /**
     * 删除供应商签名
     */
    @Query("DELETE   FROM tab_person WHERE person_id=:PersonId")
    suspend fun deleteById(PersonId: String?)


    /**
     * 删除表中全部数据
     */
    suspend fun deleteAll() {
        val tableName = "tab_person"
        val query = SimpleSQLiteQuery("delete from $tableName")
        doDeleteAll(query)
    }

    @RawQuery
    suspend fun doDeleteAll(query: SimpleSQLiteQuery): Int?

}