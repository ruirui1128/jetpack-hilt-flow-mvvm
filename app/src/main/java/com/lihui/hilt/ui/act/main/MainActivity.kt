package com.lihui.hilt.ui.act.main

import android.content.Intent
import android.widget.Toast
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityMainBinding
import com.lihui.hilt.task.TimeLifecycleTask
import com.lihui.hilt.task.task.TaskTask2
import com.lihui.hilt.ui.act.info.InfoActivity
import com.lihui.hilt.ui.act.login.LoginActivity
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.onClick

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_main)

    override fun initialize() {

        // 执行异步定时任务
        lifecycle.addObserver(TimeLifecycleTask())

        viewModel.fetchUsers() {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        bind.btnLogin.onClick {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        bind.btnInfo.onClick {
            startActivity(Intent(this, InfoActivity::class.java))
        }

    }


}