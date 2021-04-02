package com.lihui.hilt.ui.act.info

import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoVm @Inject constructor(
    private val apiService: ApiService,
    networkHelper: NetworkHelper):BaseViewModel(networkHelper)
 {

    val isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val infoResult = MutableLiveData<PageList<ArticleModel>>()

    fun getInfoList(pageNumber: Int,firstLoad : Boolean,loadMoreError:()->Unit) {
        launchFlow({apiService.getArticle(hashMapOf())},
            {infoResult.postValue(it)},
            isStatueLayout = firstLoad,
            isLoadMore = (pageNumber!=1),
            loadMoreError = {loadMoreError()},
            complete = { isRefreshing.postValue(false)})

    }

}