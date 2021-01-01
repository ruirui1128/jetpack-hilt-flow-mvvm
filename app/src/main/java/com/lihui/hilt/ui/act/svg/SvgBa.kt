package com.lihui.hilt.ui.act.svg


import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lihui.hilt.data.model.SvgModel
import com.lihui.hilt.ui.ext.start

import com.lihui.hilt.ui.vm.SvgPlayVm
import com.opensource.svgaplayer.SVGACallback
import com.opensource.svgaplayer.SVGAImageView
import java.util.concurrent.LinkedBlockingQueue

/**
 *Created by Rui
 *on 2020/12/30
 *
 * svgaAct 的 bindAdapter
 */
object SvgBa {

    /**
     * 监听队列中的长度大小
     */
    @JvmStatic
    @BindingAdapter(value = ["sizeSvgPlayVm", "sizeOwner"], requireAll = false)
    fun listenerQueue(
        textView: TextView,
        sizeSvgPlayVm: SvgPlayVm,
        sizeOwner: LifecycleOwner,
    ) {
        sizeSvgPlayVm.queueSize.observe(sizeOwner, Observer {
            textView.text = it?.toString()
            if (it > 0 && sizeSvgPlayVm.isPlaying.value == false) {
                sizeSvgPlayVm.isPlaying.postValue(true)
            }
        })
    }

    /**
     * 监听svg动画
     */
    @JvmStatic
    @BindingAdapter(
        value = ["svgPlayVm", "owner"], requireAll = false
    )
    fun listenerSvgPlay(
        sVGAImageView: SVGAImageView,
        svgPlayVm: SvgPlayVm,
        owner: LifecycleOwner,
    ) {

        svgPlayVm.queueSize.observe(owner, Observer {
            if (it > 0 && svgPlayVm.isPlaying.value == false) {
                svgPlayVm.isPlaying.postValue(true)
                val model = svgPlayVm.queue.value?.poll()
                model?:return@Observer
                sVGAImageView.start(model)
            }
        })


        sVGAImageView.callback = object : SVGACallback {
            override fun onFinished() {
                val size = svgPlayVm.queue.value?.size?:0
                //更新队列长度
                svgPlayVm.queueSize.postValue(size)
                if (size==0){
                    svgPlayVm.isPlaying.postValue(false)
                }else{
                    //说明队列中还有 继续播放
                    val model = svgPlayVm.queue.value?.poll()
                    model?:return
                    sVGAImageView.start(model)
                }

            }

            override fun onPause() {}

            override fun onRepeat() {}

            override fun onStep(frame: Int, percentage: Double) {}
        }
    }
}