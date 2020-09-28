package com.lihui.hilt.app

import android.app.Application
import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp:Application(){
    companion object {
        private lateinit var mApplication: Application
        fun getApp(): Application {
            return mApplication
        }


    }
    override fun onCreate() {
        super.onCreate()
        mApplication = this
        LiveEventBus.config().lifecycleObserverAlwaysActive(true)

    }
}