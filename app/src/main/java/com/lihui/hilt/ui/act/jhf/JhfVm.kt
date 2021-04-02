package com.lihui.hilt.ui.act.jhf

import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.UserApi
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JhfVm  @Inject constructor(
    private val userService: UserApi,
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {
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
    fun jhf(ok: (String?) -> Unit, error: () -> Unit) {
        launchFlow({ userService.jhf() }, ok = { ok(it) }, error = { error() })
    }


}