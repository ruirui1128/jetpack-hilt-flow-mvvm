package com.lihui.hilt.ui.act

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.lihui.hilt.R
import com.lihui.hilt.vm.MainViewModel
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModelConfig =
        ViewModelConfig<MainViewModel>(R.layout.activity_main)
            .addViewModel(viewModels<MainViewModel>().value)
    override fun init() {

        viewModel.fetchUsers(){
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }

        btnLogin.onClick {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnInfo.onClick {
            startActivity(Intent(this, InfoActivity::class.java))
        }

    }


}