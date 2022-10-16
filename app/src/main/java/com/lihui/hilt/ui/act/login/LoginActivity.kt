package com.lihui.hilt.ui.act.login


import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.ds.DsUtil
import com.lihui.hilt.databinding.ActivityLoginBinding
import com.lihui.hilt.event.MessageEvent
import com.lihui.hilt.ui.act.main.MainActivity
import com.lihui.hilt.uitl.LoginHandler
import com.lihui.hilt.uitl.ToastUtil
import com.mind.data.data.mmkv.KV
import com.mind.data.data.model.LoginModel
import com.mind.data.http.ApiClient
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.loadHttp
import com.mind.lib.ext.onClick
import com.mind.lib.util.CacheManager
import com.mind.lib.util.toast
import com.tencent.mmkv.MMKV

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginVm, ActivityLoginBinding>() {

    @Inject
    lateinit var loginPresenter: LoginPresenter
    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_login)
            .bindViewModel(BR.loginVm)
            .bindingParam(BR.loginPresenter, loginPresenter)


    override fun initialize() {
        initView()
        initVm()
    }

    private fun initView() {
        // 非hilt 注解的hilt http 请求
        bind.tvLogin2.onClick {
          //  viewModel.login()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        // 如果不想使用viewModel
        bind.tvLogin3.onClick {
            val usename = bind.etUser.text.toString().trim()
            val pwd = bind.etPwd.text.toString().trim()
            val map = hashMapOf(
                "username" to usename,
                "password" to pwd,
            )

            lifecycleScope.loadHttp(
                request = { ApiClient.userApi.login(map) },
                resp = { loginOk(it) }
            )

        }

    }

    private fun loginOk(it: LoginModel?) {
        ToastUtil.toast("登录成功")
        // mmkv 与 dataStore 可以选择一个使用  dataStore jetpack 原生，数据可观察， mmkv 去百度
        MMKV.defaultMMKV().putString(KV.TOKEN, token)
        CacheManager.instance.putToken(token)
        LoginHandler.get().postLoginHandler(token)
        DsUtil.putToken(lifecycleScope, it?.token ?: "")
        finish()
    }

    var token = ""
    private fun initVm() {
        //登录成功 操作
        viewModel.loginResult.observe(this, Observer { loginOk(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get(MessageEvent.LOGIN_TOKEN_EVENT).post("")
    }


}