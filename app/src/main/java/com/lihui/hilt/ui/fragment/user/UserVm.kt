package com.lihui.hilt.ui.fragment.user

import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.data.model.UserModel
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserVm @Inject constructor(
    private val userApi: UserApi,
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {


    //星耀等级
    val level  = MutableLiveData<Int>().also { it.value = 0 }

    //改变头像
    /**
     * 更换头像
     */
    val changeResult = MutableLiveData<String>()
    fun changeHeader() {
        launchFlow({ userApi.changeHeader() }, { changeResult.postValue(it) })
    }

    /**
     * 获取用户信息
     */
    val userInfoResult = MutableLiveData<UserModel>()
    fun getUserInfo() {
        launchFlow({ userApi.getUserInfo() }, { userInfoResult.postValue(it) })
    }

}