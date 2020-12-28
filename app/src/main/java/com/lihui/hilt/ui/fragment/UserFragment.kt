package com.lihui.hilt.ui.fragment

import android.os.Bundle

import androidx.fragment.app.viewModels

import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.databinding.FragmentUserBinding
import com.lihui.hilt.ui.vm.UserVm

import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class UserFragment : BaseFragment<UserVm,FragmentUserBinding> (){

    override val viewModelConfig: ViewModelConfig<UserVm>
        get() = ViewModelConfig<UserVm>(R.layout.fragment_user)
            .addViewModel(viewModels<UserVm>().value,BR.userVm)

    override fun init(savedInstanceState: Bundle?) {
        viewModel.login()

//        viewModel.name.observe(this, Observer {
//            tvUser.text = it
//        })




    }
}