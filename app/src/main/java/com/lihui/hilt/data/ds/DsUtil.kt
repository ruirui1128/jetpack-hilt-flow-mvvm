package com.lihui.hilt.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.lihui.hilt.app.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *Created by Rui
 *on 2020/12/31
 */
object DsUtil {

    val dataStore: DataStore<Preferences> = MyApp.getDataStore()


    /**
     * 存储数据
     */
    inline  fun <reified T : Any> put(
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
     * 取数据
     */
    suspend inline fun <reified T : Any> readSuspend(key: String, crossinline read: (T?) -> Unit) {
        val preferences = preferencesKey<T>(key)
        dataStore.data.map {
            it[preferences]
        }.collect {
            read(it)
        }
    }

    /**
     * 同步读取数据
     */
    inline fun <reified T : Any> read(key: String, defValue: T? = null): T? {
        val preferences: Preferences.Key<T> = preferencesKey<T>(key)
        val data = runBlocking { dataStore.data.first() }
        return data[preferences]?:defValue

    }


    fun getToken() = read(DataStoreValue.TOKEN, "")?:""


}