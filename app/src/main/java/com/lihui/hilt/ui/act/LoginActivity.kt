package com.lihui.hilt.ui.act


import android.util.Log
import androidx.activity.viewModels

import androidx.lifecycle.Observer
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityLoginBinding
import com.lihui.hilt.ui.presenter.LoginPresenter
import com.lihui.hilt.uitl.ToastUtil
import com.lihui.hilt.ui.vm.LoginVm
import com.rui.libray.base.BaseActivity

import com.rui.libray.base.ViewModelConfig

import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint class LoginActivity : BaseActivity<LoginVm,ActivityLoginBinding>() {

    @Inject lateinit var loginPresenter: LoginPresenter
    override val viewModelConfig: ViewModelConfig<LoginVm>
        get() = ViewModelConfig<LoginVm>(R.layout.activity_login)
                .addViewModel(viewModels<LoginVm>()
                .value, BR.loginVm)
                .addBindingParam(BR.loginPresenter, loginPresenter)
                .addBindingParam(BR.loginOwner,this)
    override fun init() {
        initVm()
    }

    private fun initVm() {
        //登录成功 操作
        viewModel.loginResult.observe(this, Observer {
             ToastUtil.toast("登录成功")
            Log.d("-----", it.token)
            goToAndFinish(HomeActivity::class.java)
        })

    }
}