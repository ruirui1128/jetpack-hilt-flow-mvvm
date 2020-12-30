package com.lihui.hilt.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.model.SvgModel
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import java.util.concurrent.LinkedBlockingQueue


/**
 *Created by Rui
 *on 2020/12/30
 */
class SvgPlayVm @ViewModelInject constructor(
    private val api: ApiService,
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {
    /**
     * 播放队列中的长度
     */
    val queueSize = MutableLiveData<Int>().apply { this.value = 0 }

    /**
     * 动画是否已经开始播放
     */
    val isPlaying = MutableLiveData<Boolean>().apply { this.value = false }

    /**
     * 动画队列
     */
    val queue = MutableLiveData<LinkedBlockingQueue<SvgModel>>().apply {
        this.value = LinkedBlockingQueue<SvgModel>()
    }

}