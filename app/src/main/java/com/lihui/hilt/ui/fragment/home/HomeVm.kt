package com.lihui.hilt.ui.fragment.home


import androidx.lifecycle.MutableLiveData
import com.mind.data.data.api.ApiService
import com.mind.data.data.api.UserApi
import com.mind.data.data.model.ArticleModel
import com.mind.data.data.model.BannerDataModel
import com.mind.data.data.model.PageList
import com.mind.lib.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVm @Inject constructor(
    private val apiService: ApiService,
    private val userService: UserApi,
) : BaseViewModel() {

    /**
     * 界面刷新
     */
    val isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    fun getBanner(ok: (MutableList<BannerDataModel>?) -> Unit) {
        launchFlow({ apiService.getBanner() }, { ok(it) })
    }

    /**
     * 获取首页文章信息
     */
    val articleResult = MutableLiveData<PageList<ArticleModel>>()
    fun getArticleList(pageNumber: Int, firstLoad: Boolean, loadMoreError: () -> Unit) {
        val map = hashMapOf(
            "pageNumber" to pageNumber.toString(),
            "pageSize" to "20"
        )
        launchFlow(
            request = { apiService.getArticle(map) },
            resp = { articleResult.postValue(it) },
            isStatueLayout = firstLoad,
            isLoadMore = (pageNumber != 1),
            loadMoreError = { loadMoreError() },
            complete = { isRefreshing.postValue(false) })
    }

    /**
     * 是否收藏成功   这里无须在viewmodel声明livedata 在Acitity中设置监听
     * 而是直接返回
     * 注意区分场景
     */
    fun getCollect(resp: (String?) -> Unit) {
        launchFlow(
            request = { userService.getCollect() },
            resp = { resp(it) })
    }

    /**
     * 更换头像
     */
    fun changeHeader(ok: (String?) -> Unit) {
        launchFlow(
            request = { userService.changeHeader() },
            resp = { ok(it) })
    }

    /**
     * 接化发
     */
    fun jhf(ok: (String?) -> Unit) {
        launchFlow({ userService.jhf() }, { ok(it) })
    }


}