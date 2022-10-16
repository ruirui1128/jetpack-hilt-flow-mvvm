package com.lihui.hilt.ui.act.main


import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.uitl.ToastUtil
import com.mind.data.data.api.ApiService
import com.mind.lib.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
) : BaseViewModel() {

    val result = MutableLiveData<String>().apply {
        this.value = "谜一样的男人"
    }

    fun fetchUsers(listener: (String) -> Unit) {}

    fun test() {
        ToastUtil.toast("xiaoxi")
    }

}