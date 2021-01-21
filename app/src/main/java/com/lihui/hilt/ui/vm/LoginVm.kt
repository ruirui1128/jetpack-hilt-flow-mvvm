package com.lihui.hilt.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.data.model.LoginModel
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper

class LoginVm @ViewModelInject constructor(
         private val userApi: UserApi,
         networkHelper: NetworkHelper):BaseViewModel(networkHelper){


    //账号
    val username = MutableLiveData<String>()
    //密码
    val passwrod = MutableLiveData<String>()

    //是否显示密码  默认不显示
    val isClose = MutableLiveData<Boolean>().apply { this.value = true }

    val loginResult = MutableLiveData<LoginModel>()
    fun login(){
        val map = hashMapOf("username" to (username.value?:""),
        "password" to (passwrod.value?:""))
        launchFlow({userApi.login(map)},{loginResult.postValue(it)},isShowDialog = true)
    }






}