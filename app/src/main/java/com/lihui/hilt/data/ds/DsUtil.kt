package com.lihui.hilt.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.lihui.hilt.app.MyApp
import com.mind.lib.util.CacheManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 *Created by Rui
 *on 2020/12/31
 */
object DsUtil {

    val dataStore: DataStore<Preferences> = MyApp.getDataStore()


    /**
     * 存储数据
     */
    inline fun <reified T : Any> put(
        scope: CoroutineScope = GlobalScope,
        key: String,
        value: T
    ) {
        scope.launch {
            val preferences = preferencesKey<T>(key)
            dataStore.edit {
                it[preferences] = value
            }
        }
    }

    /**
     * 指定协程取  如果需要更新数据限定 lifeScope,viewModelScope
     */
    suspend inline fun <reified T : Any> readSuspend(key: String, crossinline read: (T?) -> Unit) {
        val preferences = preferencesKey<T>(key)
        dataStore.data.map {
            it[preferences]
        }.collect {
            read(it)
        }
    }

     inline fun <reified T : Any> readFlow(key: String): Flow<T?> {
        val preferences = preferencesKey<T>(key)
      return  dataStore.data.map {
            it[preferences]
        }
    }

    /**
     * 指定协程 无限制协程类型
     */
    inline fun <reified T : Any> readScope(
        scope: CoroutineScope = GlobalScope,
        key: String,
        crossinline read: (T?) -> Unit
    ) {
        scope.launch {
            withContext(Dispatchers.Main) {
                readSuspend(key,read)
            }
        }
    }


    /**
     * 同步读取数据
     */
    inline fun <reified T : Any> read(key: String, defValue: T? = null): T? {
        val preferences: Preferences.Key<T> = preferencesKey<T>(key)
        val data = runBlocking { dataStore.data.first() }
        return data[preferences] ?: defValue

    }


    fun getToken() = read(DataStoreValue.TOKEN, "") ?: ""

    fun putToken(scope: CoroutineScope = GlobalScope, value: String) {
        put(scope, DataStoreValue.TOKEN, value)
        CacheManager.instance.putToken(value)
    }


}