package com.lihui.hilt.ui.vm

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.uitl.ToastUtil
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper

class LoginVm @ViewModelInject constructor(
         private val userApi: UserApi,
         networkHelper: NetworkHelper)
    :BaseViewModel(networkHelper) {

    val username = MutableLiveData<String>()
    val passwrod = MutableLiveData<String>()

    val loginText = MutableLiveData<String>().apply {
        this.value = "默认登录"
    }


    val loginResult = MutableLiveData<String>()
    fun login(){
        if (!checkParams())return
        val map = hashMapOf("username" to (username.value?:""),
        "password" to (passwrod.value?:""))
        launchFlow({userApi.login(map)},{loginResult.postValue(it)},isShowDialog = true)

    }




    fun login2(listener: (String)->Unit){
        if (!checkParams())return
        val map = hashMapOf("username" to (username.value?:""),
            "password" to (passwrod.value?:""))
        launchFlow({userApi.login(map)},{loginResult.postValue(it)},
            isShowDialog = true,
            error = {listener(it)})

    }

    private fun checkParams():Boolean{
        if (TextUtils.isEmpty(username.value)){
            ToastUtil.toast("用户名为空")
            return false
        }

        if (TextUtils.isEmpty(passwrod.value)){
            ToastUtil.toast("密码为空")
            return false
        }
        return true
    }


}