package com.lihui.hilt.uitl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.lihui.hilt.app.MyApp
import com.lihui.hilt.data.ds.DsUtil

/**
 *Created by Rui
 *on 2020/12/31
 */

/**
 * 是否登录
 */
fun isLogined(): Boolean {
    return DsUtil.getToken().isNotEmpty()
}

/**
 * 是否登录
 * 如果登录执行method()
 * 如果没有登录执行 login()
 */
fun afterLogin(method: () -> Unit, login: () -> Unit) {
    if (isLogined()) {
        method()
    } else {
        login()
    }
}

/**
 * 监听登录之后消息   登录之后完成自动衔接之后的操作
 *
 * 已经登录或者登录之后执行 logined()
 */
fun loginObserver(
    owner: LifecycleOwner,
    logined: () -> Unit,
    observer: Observer<String> = Observer { logined() }
) {

    if (isLogined()) {
        logined()
    } else {
        val liveData: LiveData<String>? = LoginHandler.get().login(MyApp.getApp())
        liveData?.observe(owner, observer)
    }

}


private fun loginObserver(
    observer: Observer<String>,
    liveData: LiveData<String>
): Observer<String> {
    return Observer<String> { str ->
        //登录成功以后这里接受消息 发送至 observer
        observer.onChanged(str)
        liveData.removeObserver(observer)
    }
}