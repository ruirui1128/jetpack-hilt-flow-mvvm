package com.lihui.hilt.ui.act

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityMainBinding
import com.lihui.hilt.ui.vm.MainViewModel
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {

    override val viewModelConfig: ViewModelConfig<MainViewModel>
    get() = ViewModelConfig<MainViewModel>(R.layout.activity_main)
            .addViewModel(viewModels<MainViewModel>().value)
    override fun init() {

        viewModel.fetchUsers(){
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }

        bind.btnLogin.onClick {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        bind.btnInfo.onClick {
            startActivity(Intent(this, InfoActivity::class.java))
        }

    }


}