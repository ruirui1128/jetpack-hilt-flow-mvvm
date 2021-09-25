package com.lihui.hilt.ui.act.login


import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.ds.DsUtil
import com.lihui.hilt.databinding.ActivityLoginBinding
import com.lihui.hilt.event.MessageEvent
import com.lihui.hilt.uitl.LoginHandler
import com.lihui.hilt.uitl.ToastUtil
import com.mind.data.data.mmkv.KV
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.util.CacheManager
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
        initVm()
    }

    var token = ""
    private fun initVm() {
        //登录成功 操作
        viewModel.loginResult.observe(this, Observer {
            ToastUtil.toast("登录成功")
            // mmkv 与 dataStore 可以选择一个使用  dataStore jetpack 原生，数据可观察， mmkv 去百度
            MMKV.defaultMMKV().putString(KV.TOKEN, token)
            CacheManager.instance.putToken(token)
            LoginHandler.get().postLoginHandler(token)
            DsUtil.putToken(lifecycleScope, it.token)
            finish()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get(MessageEvent.LOGIN_TOKEN_EVENT).post("")
    }


}