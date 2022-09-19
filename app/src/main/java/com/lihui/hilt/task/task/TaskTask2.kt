package com.lihui.hilt.task.task

import com.mind.data.http.ApiClient
import kotlinx.coroutines.runBlocking

/**
 * create by Rui on 2022/4/15
 * desc:
 */
class TaskTask2() : Runnable {
    override fun run() {
        val data = runBlocking {             //阻塞当前线程
            ApiClient.appApi.getBanner()
        }

    }
}