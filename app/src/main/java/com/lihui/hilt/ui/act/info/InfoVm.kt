package com.lihui.hilt.ui.act.info

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.ApiService
import com.mind.data.data.api.UserApi
import com.mind.data.data.model.ArticleModel
import com.mind.data.data.model.PageList
import com.mind.data.data.model.UserModel
import com.mind.lib.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoVm @Inject constructor(
    private val apiService: ApiService,
    private val userApi: UserApi,

    ) : BaseViewModel() {
    val isRefreshing = MutableLiveData<Boolean>().also { it.value = false }

    val infoResult = MutableLiveData<PageList<ArticleModel>>()
    fun getInfoList(pageNumber: Int, loadMoreError: () -> Unit) {
        launchFlow(
            request = { apiService.getArticle(hashMapOf()) },
            resp = { infoResult.postValue(it) },
            isLoadMore = (pageNumber != 1),
            loadMoreError = { loadMoreError() },
            complete = { isRefreshing.postValue(false) })
    }

    val userInfoResult = MutableLiveData<UserModel>()
    fun getUserInfo() {
        launchFlow({ userApi.getUserInfo() }, { userInfoResult.postValue(it) })
    }

}