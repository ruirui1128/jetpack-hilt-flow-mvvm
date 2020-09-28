package com.lihui.hilt.uitl

import android.content.Context
import android.content.SharedPreferences.Editor

import com.lihui.hilt.base.BaseConstant
import com.lihui.hilt.app.MyApp


/*
  SP工具类
 */
object AppPrefsUtils {
    private var sp = MyApp.getApp().getSharedPreferences(BaseConstant.TABLE_PREFS, Context.MODE_PRIVATE)
    private var ed: Editor


    const val USER_INFO = "user_info"
    const val DEVICEID  = "deviceId"
    const val TOKEN = "Authorization"

    //用户登陆token
    const val TOKEN_AUTH = "token_auth"
    init {
        ed = sp.edit()
    }

    /**
     * Boolean型数据
     */
    fun putBoolean(key: String, value: Boolean) {
        ed.putBoolean(key, value)
        ed.commit()
    }

    /**
     * 默认 false
     */
    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    /*
        String数据
     */
    fun putString(key: String, value: String) {
        ed.putString(key, value)
        ed.commit()
    }

    /*
        默认 ""
     */
    fun getString(key: String): String {
        return sp.getString(key, "").toString()
    }

    /*
        Int数据
     */
    fun putInt(key: String, value: Int) {
        ed.putInt(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    /*
        Long数据
     */
    fun putLong(key: String, value: Long) {
        ed.putLong(key, value)
        ed.commit()
    }

    /*
        默认 0
     */
    fun getLong(key: String): Long {
        return sp.getLong(key, 0)
    }

    /**
     * Set数据
     */
    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key).toMutableSet()
        localSet.addAll(set)
        ed.putStringSet(key, localSet)
        ed.commit()
    }

    /**
     * 默认空set
     */
    fun getStringSet(key: String): Set<String> {
        val set = setOf<String>()
        return sp.getStringSet(key, set)!!
    }

    /**
     * 删除key数据
     */
    fun remove(key: String) {
        ed.remove(key)
        ed.commit()
    }


}