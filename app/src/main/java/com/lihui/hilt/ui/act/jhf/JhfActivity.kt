package com.lihui.hilt.ui.act.jhf


import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityJhfBinding
import com.lihui.hilt.event.MessageEvent
import com.mind.data.data.model.ArticleModel
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.onClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JhfActivity : BaseActivity<JhfVm, ActivityJhfBinding>() {

    companion object {
        private const val JHA_DATA = "jha_data"
        fun start(context: Context?, data: ArticleModel) {
            context ?: return
            val intent = Intent(context, JhfActivity::class.java)
            intent.putExtra(JHA_DATA, data)
            context.startActivity(intent)
        }

    }

    var model: ArticleModel? = null

    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_jhf)

    override fun initialize() {
        viewModels<JhfVm>().value
        model = intent?.getParcelableExtra(JHA_DATA)
        viewModel.collection.value = model?.isCollect ?: false
        bind.tvCollect.onClick {
            viewModel.getCollect {
                model?.isCollect = !(model?.isCollect ?: false)
                LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
            }
        }

        bind.tvJie.onClick {
            model?.isJie = !(model?.isJie ?: false)
            model?.isHua = false
            model?.isFa = false
            LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
        }

        bind.tvHua.onClick {
            model?.isHua = !(model?.isHua ?: false)
            model?.isJie = false
            model?.isFa = false
            LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
        }
        bind.tvFa.onClick {
            model?.isFa = !(model?.isFa ?: false)
            model?.isJie = false
            model?.isHua = false
            LiveEventBus.get(MessageEvent.ITEM_JHF_EVENT).post(model)
        }


    }


}