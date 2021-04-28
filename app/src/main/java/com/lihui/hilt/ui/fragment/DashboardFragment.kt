package com.lihui.hilt.ui.fragment

import android.os.Bundle
import com.lihui.hilt.R
import com.lihui.hilt.databinding.FragmentDashboardBinding
import com.lihui.hilt.ui.act.info.InfoActivity
import com.lihui.hilt.ui.act.room.RoomActivity
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.BaseViewModel
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick
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