package com.lihui.hilt.app

import android.app.Application
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadSir
import com.rui.libray.widget.loadsir.EmptyCallback
import com.rui.libray.widget.loadsir.ErrorCallback
import com.rui.libray.widget.loadsir.LoadingCallback

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
        initLoadSir()

    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }
}