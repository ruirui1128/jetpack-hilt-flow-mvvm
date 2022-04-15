package com.lihui.hilt.task.task

import com.mind.data.data.model.BannerDataModel
import com.mind.data.http.ApiClient
import com.mind.lib.ext.loadHttp
import kotlinx.coroutines.runBlocking

/**
 * create by Rui on 2022/4/15
 * desc:
 */
class TaskTask1() : Runnable {
    override fun run() {
        val data = runBlocking {             //阻塞当前线程
            var result = mutableListOf<BannerDataModel>()
            loadHttp(
                request = { ApiClient.appApi.getBanner() },
                resp = {
                    it ?: return@loadHttp
                    result = it
                }
            )
            result
        }
        //线程继续执行
        if (data.size > 0) {
        }

    }
}