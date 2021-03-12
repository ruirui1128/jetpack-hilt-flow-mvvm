package com.lihui.hilt.ui.act



import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.ds.DsUtil
import com.lihui.hilt.databinding.ActivityLoginBinding
import com.lihui.hilt.event.MessageEvent
import com.lihui.hilt.ui.presenter.LoginPresenter
import com.lihui.hilt.ui.vm.LoginVm
import com.lihui.hilt.uitl.LoginHandler
import com.lihui.hilt.uitl.ToastUtil
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.ViewModelConfig
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
            .bindingParam(BR.loginOwner, this)

    override fun init() {
        initVm()
    }

    var token = ""
    private fun initVm() {
        //登录成功 操作
        viewModel.loginResult.observe(this, Observer {
            ToastUtil.toast("登录成功")
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