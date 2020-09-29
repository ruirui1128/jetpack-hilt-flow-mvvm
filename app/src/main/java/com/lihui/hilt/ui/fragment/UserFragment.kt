package com.lihui.hilt.ui.fragment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.ui.vm.UserVm
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.fragment_user.*

@AndroidEntryPoint
class UserFragment : BaseFragment<UserVm> (){

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