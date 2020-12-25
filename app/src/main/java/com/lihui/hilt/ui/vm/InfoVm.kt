package com.lihui.hilt.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.model.InfoModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper

class InfoVm @ViewModelInject constructor(
    private val apiService: ApiService,
    networkHelper: NetworkHelper):BaseViewModel(networkHelper)
 {

    val isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val infoResult = MutableLiveData<PageList<InfoModel>>()

    fun getInfoList(pageNumber: Int,firstLoad : Boolean,loadMoreError:()->Unit) {
        launchFlow({apiService.getPageList(pageNumber.toString())},
            {infoResult.postValue(it)},
            isStatueLayout = firstLoad,
            isLoadMore = (pageNumber!=1),
            loadMoreError = {loadMoreError()},
            complete = { isRefreshing.postValue(false)})

    }

}