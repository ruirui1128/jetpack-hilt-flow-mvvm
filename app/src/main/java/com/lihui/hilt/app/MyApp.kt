package com.lihui.hilt.app

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.data.ds.DsUtil
import com.lihui.hilt.uitl.CacheManager


import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
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
        LiveEventBus.config().lifecycleObserverAlwaysActive(true)
        dataStore = createDataStore(name = "hilt_app")
        CacheManager.instance.putToken(DsUtil.getToken())
    }


}