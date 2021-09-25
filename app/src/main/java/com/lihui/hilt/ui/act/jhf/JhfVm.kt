package com.lihui.hilt.ui.act.jhf

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.UserApi
import com.mind.lib.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JhfVm @Inject constructor(
    private val userService: UserApi,
) : BaseViewModel() {
    val collection = MutableLiveData<Boolean>()
    val jie = MutableLiveData<Boolean>()
    val hua = MutableLiveData<Boolean>()
    val fa = MutableLiveData<Boolean>()


    /**
     * 收藏
     */
    fun getCollect(ok: () -> Unit) {
        launchFlow({ userService.getCollect() }, { ok() })
    }


    /**
     * 接化发
     */
    fun jhf(resp: (String?) -> Unit, error: () -> Unit) {
        launchFlow(
            request = { userService.jhf() },
            resp = { resp(it) },
            error = { error() })
    }


}