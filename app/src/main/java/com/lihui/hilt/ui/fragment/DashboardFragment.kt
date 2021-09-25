package com.lihui.hilt.ui.fragment

import android.os.Bundle
import com.lihui.hilt.R
import com.lihui.hilt.databinding.FragmentDashboardBinding
import com.lihui.hilt.ui.act.info.InfoActivity
import com.lihui.hilt.ui.act.room.RoomActivity
import com.mind.lib.base.BaseFragment
import com.mind.lib.base.BaseViewModel
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.onClick

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<BaseViewModel, FragmentDashboardBinding>() {

    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.fragment_dashboard)

    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        bind.btnRoom.onClick { RoomActivity.start(context) }
        bind.btnInfo.onClick { InfoActivity.start(context) }
    }
}