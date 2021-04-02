package com.lihui.hilt.ui.act.login

import com.lihui.hilt.uitl.ToastUtil
import javax.inject.Inject

class LoginPresenter @Inject constructor() {

    /**
     * 登录
     */
     fun login(viewModel: LoginVm){
        if (viewModel.username.value.isNullOrEmpty()){
            ToastUtil.toast("账号不能为空")
            return
        }
        if (viewModel.passwrod.value.isNullOrEmpty()){
            ToastUtil.toast("密码不能为空")
            return
        }
        viewModel.login()
    }

    /**
     * 是否显示密码
     */
    fun showPwd(viewModel: LoginVm){
        val value = viewModel.isClose.value?:false
        viewModel.isClose.postValue(!value)
    }

}