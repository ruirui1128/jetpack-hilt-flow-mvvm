package com.lihui.hilt.ui.fragment.user

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.UserApi
import com.mind.data.data.model.UserModel
import com.mind.lib.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserVm @Inject constructor(
    private val userApi: UserApi,
) : BaseViewModel() {


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