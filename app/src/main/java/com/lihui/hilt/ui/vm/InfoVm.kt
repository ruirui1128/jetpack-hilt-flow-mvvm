package com.lihui.hilt.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.bean.InfoBean
import com.lihui.hilt.data.bean.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper

class InfoVm @ViewModelInject constructor(
    private val apiService: ApiService,
    networkHelper: NetworkHelper
) :BaseViewModel(networkHelper) {

    val isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val infoResult = MutableLiveData<PageList<InfoBean>>()
    fun getInfoList(pageNumber: Int,loadMoreError:()->Unit) {
        launchFlow({apiService.getPageList(pageNumber.toString())},
            {infoResult.postValue(it)},
            isLoadMore = (pageNumber!=1),
            loadMoreError = {loadMoreError()},
            complete = { isRefreshing.postValue(false)})

    }

}