package com.mind.lib.base

import android.app.Application
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

/**
 * Created by rui
 * on 2021/8/2
 */
open class BaseApp : Application() {
    companion object {
        private lateinit var baseApp: BaseApp
        fun getApp(): Application {
            return baseApp
        }
    }
    override fun onCreate() {
        super.onCreate()
        // mmkv 初始化
        MMKV.initialize(this)
        //liveBus 初始化
        LiveEventBus.config().lifecycleObserverAlwaysActive(true)
        baseApp = this
    }
}