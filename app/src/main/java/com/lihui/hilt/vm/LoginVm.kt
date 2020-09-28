package com.lihui.hilt.vm

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.uitl.ToastUtil
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import okhttp3.EventListener

class LoginVm @ViewModelInject constructor(
         private val userApi: UserApi,
         networkHelper: NetworkHelper)
    :BaseViewModel(networkHelper) {

    val username = MutableLiveData<String>()
    val passwrod = MutableLiveData<String>()


    val loginResult = MutableLiveData<String>()
    fun login(){
        if (TextUtils.isEmpty(username.value)){
            ToastUtil.toast("用户名为空")
            return
        }

        if (TextUtils.isEmpty(passwrod.value)){
            ToastUtil.toast("密码为空")
            return
        }
        val map = hashMapOf("username" to (username.value?:""),
        "password" to (passwrod.value?:""))
        launchFlow({userApi.login(map)},{loginResult.postValue(it)},isShowDialog = true)

    }


    fun login2(listener: (String)->Unit){
        if (TextUtils.isEmpty(username.value)){
            ToastUtil.toast("用户名为空")
            return
        }

        if (TextUtils.isEmpty(passwrod.value)){
            ToastUtil.toast("密码为空")
            return
        }
        val map = hashMapOf("username" to (username.value?:""),
            "password" to (passwrod.value?:""))
        launchFlow({userApi.login(map)},{loginResult.postValue(it)},
            isShowDialog = true,
            error = {listener(it)})

    }


}