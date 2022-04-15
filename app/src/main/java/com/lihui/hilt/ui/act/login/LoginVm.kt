package com.lihui.hilt.ui.act.login

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.UserApi
import com.mind.data.data.model.LoginModel
import com.mind.data.http.ApiClient
import com.mind.data.http.RetrofitClient
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


    /**
     * 不使用注解中的userApi
     *
     * 针对 不使用 hilt的 情况
     * ApiClient.userApi 是 ApiClient.userApi单例中的 不是 hilt 中的userApi
     */
    fun login2() {
        val map = hashMapOf(                         // 传参
            "username" to (username.value ?: ""),
            "password" to (passwrod.value ?: "")
        )
        loadHttp(
            request = { ApiClient.userApi.login(map) },  // 请求
            resp = { loginResult.postValue(it) },       // 响应回调
        )
    }


}