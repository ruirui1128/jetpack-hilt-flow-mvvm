package com.lihui.hilt.ui.act


import androidx.activity.viewModels
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.R
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.databinding.ActivityInfoBinding
import com.lihui.hilt.databinding.ActivityJhfBinding
import com.lihui.hilt.event.MessageEvent
import com.lihui.hilt.ui.vm.JhfVm
import com.rui.libray.base.BaseActivity

import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.onClick

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JhfActivity : BaseActivity<JhfVm,ActivityJhfBinding>() {

    companion object{
        const val JHA_DATA = "jha_data"
    }
    var model :ArticleModel ? = null

    override val viewModelConfig: ViewModelConfig<JhfVm>
        get() = ViewModelConfig<JhfVm>(R.layout.activity_jhf)
                .addViewModel(viewModels<JhfVm>().value)

    override fun init() {
        model = intent?.extras?.getParcelable<ArticleModel>(JHA_DATA)
        viewModel.collection.value = model?.isCollect?:false
        bind.tvCollect.onClick {
            viewModel.getCollect {
                model?.isCollect = !(model?.isCollect?:false)
                LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
            }
        }

        bind.tvJie.onClick {
            model?.isJie = !(model?.isJie?:false)
            LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
        }

        bind.tvHua.onClick {
            model?.isHua = !(model?.isHua?:false)
            LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
        }
        bind.tvFa.onClick {
            model?.isFa = !(model?.isFa?:false)
            LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
        }

    }


}