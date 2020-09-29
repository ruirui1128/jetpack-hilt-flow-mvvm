package com.lihui.hilt.ui.act

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import androidx.navigation.ui.setupWithNavController
import com.lihui.hilt.R
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.BaseViewModel
import com.rui.libray.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<BaseViewModel>() {

    override val viewModelConfig: ViewModelConfig<BaseViewModel>
        get() = ViewModelConfig<BaseViewModel>(R.layout.activity_home)
            .addViewModel(viewModels<BaseViewModel>().value)
    override fun init() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }
}