package com.mind.lib.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import com.mind.lib.base.BaseApp

import java.net.NetworkInterface

/**
 * Created by rui
 *  on 2021/8/5
 */
object Util {
    /**
     * 网络检查
     */
    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }






}

/**
 * toast
 */
fun toast(content: String?, int: Int = Toast.LENGTH_LONG) {
    content ?: return
    Toast.makeText(BaseApp.getApp(), content, int).show()
}

val handler = Handler(Looper.getMainLooper())

/**
 * 切换主线程  谨慎使用
 */
fun runOnUiThread(action: Runnable) {
    handler.post(action)
}

/**
 * 延迟执行 谨慎使用
 */
fun delay(long: Long = 600L, action: Runnable) {
    handler.postDelayed(action, long)
}