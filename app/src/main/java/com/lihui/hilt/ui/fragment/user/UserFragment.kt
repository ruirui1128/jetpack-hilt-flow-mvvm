package com.lihui.hilt.ui.fragment.user

import android.os.Bundle
import androidx.lifecycle.lifecycleScope

import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.ds.DsUtil
import com.lihui.hilt.databinding.FragmentUserBinding
import com.mind.data.data.mmkv.KV
import com.mind.lib.base.BaseFragment
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.onClick
import com.tencent.mmkv.MMKV

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
        bind.btn.onClick {
            //MMKV datastore 任选其一 两者区别-百度
            MMKV.defaultMMKV().putString(KV.TOKEN, "")
            DsUtil.putToken(lifecycleScope, "")
        }
        bind.btn2.onClick { viewModel.changeHeader() }
        bind.btn3.onClick { viewModel.level.value = (0..4).random() }
    }
}