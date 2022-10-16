package com.lihui.hilt.app

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.lihui.hilt.BuildConfig
import com.mind.data.data.mmkv.KV
import com.mind.lib.base.BaseApp
import com.mind.lib.util.CacheManager
import com.tencent.mmkv.MMKV

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : BaseApp() {
    companion object {
        private lateinit var mApplication: Application
        fun getApp(): Application {
            return mApplication
        }

        private lateinit var dataStore: DataStore<Preferences>
        fun getDataStore(): DataStore<Preferences> {
            return dataStore
        }
    }


    override fun onCreate() {
        super.onCreate()
        mApplication = this
        dataStore = createDataStore(name = "hilt_app")
        initCache()

    }

    /**
     * 全局缓存
     */
    private fun initCache() {
        CacheManager.instance.putToken(MMKV.defaultMMKV().getString(KV.TOKEN, "") ?: "")
        CacheManager.instance.putVersion(BuildConfig.VERSION_NAME)
    }


}