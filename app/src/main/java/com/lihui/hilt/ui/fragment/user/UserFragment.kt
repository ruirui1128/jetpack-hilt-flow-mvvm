package com.lihui.hilt.ui.fragment.user

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.ds.DsUtil
import com.lihui.hilt.databinding.FragmentUserBinding
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : BaseFragment<UserVm, FragmentUserBinding>() {

    // 实现方式多样 , 没有固定要求!!!!
    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.fragment_user)
            .bindViewModel(BR.userVm)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    private fun initData() {
        viewModel.getUserInfo()
    }

    private fun initView() {
        bind.btn.onClick { DsUtil.putToken(lifecycleScope, "") }
        bind.btn2.onClick { viewModel.changeHeader() }
        bind.btn3.onClick { viewModel.level.value = (0..4).random() }
    }
}