package com.lihui.hilt.ui.vm

import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.UserApi
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserVm @Inject constructor(
    private val userApi: UserApi,
    networkHelper: NetworkHelper):BaseViewModel(networkHelper)
{

    val name = MutableLiveData<String>().apply {
        this.value = "默认名称"
    }
    fun login(){
//        val map = hashMapOf("" to "")
//        launchFlow({userApi.login(map)},{},
//        error = {name.postValue("請求失敗")})

        name.postValue("2222")
    }

}