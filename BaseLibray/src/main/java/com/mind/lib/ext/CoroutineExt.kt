package com.mind.lib.ext

import android.util.Log
import com.mind.lib.data.model.Res
import com.mind.lib.data.net.ResCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 *  http 请求扩展
 */
fun <T> CoroutineScope.loadHttp(
    start: () -> Unit = {},
    request: suspend CoroutineScope.() -> Res<T>,
    resp: (T?) -> Unit,
    err: (String) -> Unit = {},
    end: () -> Unit = {}
) {
    launch {
        try {
            start()
            val data = request()
            if (data.code == ResCode.OK.getCode()) {
                resp(data.data)
            } else {
                err(data.msg)
            }
        } catch (e: Exception) {
            err(e.message ?: "")  //可根据具体异常显示具体错误提示
            Log.e("HttpClient", "异常:" + e.message)
        } finally {
            end()
        }
    }
}

/**
 *  http 请求扩展
 */
fun <T> CoroutineScope.http(
    start: () -> Unit = {},
    request: suspend CoroutineScope.() -> Res<T>,
    resp: (T?) -> Unit,
    err: (String) -> Unit = {},
    end: () -> Unit = {}
):Job {
  return  launch {
        try {
            start()
            val data = request()
            if (data.code == ResCode.OK.getCode()) {
                resp(data.data)
            } else {
                err(data.msg)
            }
        } catch (e: Exception) {
            err(e.message ?: "")  //可根据具体异常显示具体错误提示
            Log.e("HttpClient", "异常:" + e.message)
        } finally {
            end()
        }
    }
}

