package com.lihui.hilt.ui.act.svg

import androidx.activity.viewModels
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivitySvgPlayBinding
import com.lihui.hilt.ui.vm.SvgPlayVm
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class SvgPlayActivity : BaseActivity<SvgPlayVm, ActivitySvgPlayBinding>() {


    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_svg_play)
            .bindViewModel(BR.svgVm)
            .bindingParam(BR.svgOwner,this)

    override fun initialize() {}

}