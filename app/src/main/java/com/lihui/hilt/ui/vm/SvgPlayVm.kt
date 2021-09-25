package com.lihui.hilt.ui.vm

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.ApiService
import com.mind.data.data.model.SvgModel
import com.mind.lib.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


/**
 *Created by Rui
 *on 2020/12/30
 */
@HiltViewModel
class SvgPlayVm  @Inject constructor(
    private val api: ApiService,
) : BaseViewModel() {
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
    val queue = MutableLiveData<LinkedList<SvgModel>>().apply {
        this.value = LinkedList<SvgModel>()
    }

}