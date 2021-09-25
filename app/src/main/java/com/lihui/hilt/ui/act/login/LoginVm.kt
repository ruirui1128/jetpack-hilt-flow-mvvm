package com.lihui.hilt.ui.act.login

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.UserApi
import com.mind.data.data.model.LoginModel
import com.mind.lib.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVm @Inject constructor(
    private val userApi: UserApi
) : BaseViewModel() {


    //账号
    val username = MutableLiveData<String>()

    //密码
    val passwrod = MutableLiveData<String>()

    //是否显示密码  默认不显示
    val isClose = MutableLiveData<Boolean>().apply { this.value = true }

    val loginResult = MutableLiveData<LoginModel>()
    fun login() {
        val map = hashMapOf(
            "username" to (username.value ?: ""),
            "password" to (passwrod.value ?: "")
        )
        launchFlow(
            request = { userApi.login(map) },
            resp = { loginResult.postValue(it) },
            isShowDialog = true
        )
    }


}