package com.lihui.hilt.ui.act.info

import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.PageList
import com.lihui.hilt.data.model.UserModel
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoVm @Inject constructor(
    private val apiService: ApiService,
    private val userApi: UserApi,
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {
    val isRefreshing = MutableLiveData<Boolean>().also { it.value = false }

    val infoResult = MutableLiveData<PageList<ArticleModel>>()
    fun getInfoList(pageNumber: Int, loadMoreError: () -> Unit) {
        launchFlow({ apiService.getArticle(hashMapOf()) },
            { infoResult.postValue(it) },
            isLoadMore = (pageNumber != 1),
            loadMoreError = { loadMoreError() },
            complete = { isRefreshing.postValue(false) })

    }

    val userInfoResult = MutableLiveData<UserModel>()
    fun getUserInfo() {
        launchFlow({ userApi.getUserInfo() }, { userInfoResult.postValue(it) })
    }

}