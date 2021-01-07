package com.lihui.hilt.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.databinding.FragmentDashboardBinding
import com.lihui.hilt.ui.act.svg.SvgPlayActivity
import com.lihui.hilt.ui.act.view.ViewActivity
import com.lihui.hilt.ui.vm.HomeVm
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.BaseViewModel
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<BaseViewModel,FragmentDashboardBinding>() {

    override val viewModelConfig: ViewModelConfig<BaseViewModel>
        get() = ViewModelConfig<BaseViewModel>(R.layout.fragment_dashboard)
            .addViewModel(viewModels<BaseViewModel>().value)

    override fun init(savedInstanceState: Bundle?) {

        bind.btn1.onClick {
            goTo(SvgPlayActivity::class.java)
        }

        bind.btn2.onClick {
            goTo(ViewActivity::class.java)
        }

    }
}