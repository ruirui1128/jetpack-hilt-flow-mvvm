package com.lihui.hilt.ui.act.svg

import com.lihui.hilt.ui.vm.SvgPlayVm
import com.mind.data.data.model.SvgModel
import java.util.*

/**
 *Created by Rui
 *on 2020/12/30
 */
object SvgPresenter {
    /**
     * 播放动画放入队列中
     */
    fun putQueue(viewModel: SvgPlayVm) {
        val randomType = (0..1).random()
        val id = UUID.randomUUID().toString()
        val svgModel = SvgModel(id = id, name = "", type = randomType)
        viewModel.queue.value?.offer(svgModel)
        viewModel.queueSize.postValue(viewModel.queue.value?.size)
    }

}