package com.lihui.hilt.ui.act


import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.uitl.ToastUtil
import com.lihui.hilt.vm.LoginVm
import com.rui.libray.base.BaseActivity

import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginVm>() {

    override val viewModelConfig: ViewModelConfig<LoginVm>
        get() = ViewModelConfig<LoginVm>(R.layout.activity_login)
            .addViewModel(viewModels<LoginVm>().value,BR.loginVm)
    override fun init() {
        initView()
        initVm()
    }

    private fun initView() {
        btnLogin.onClick {
            viewModel.login2(){
                ToastUtil.toast("干哈我要自己处理")
            }
        }
    }

    private fun initVm() {
        viewModel.loginResult.observe(this, Observer {
            //登录成功 操作
            ToastUtil.toast("登录成功")
            finish()
        })
    }
}