package com.lihui.hilt.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by rui
 *  on 2022/5/11
 */
class MyService:Service() {

    override fun onCreate() {
        super.onCreate()
       val job = GlobalScope.launch {

        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}