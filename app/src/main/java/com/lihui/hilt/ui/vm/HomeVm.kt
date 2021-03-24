package com.lihui.hilt.ui.vm


import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVm  @Inject constructor(
    private val apiService: ApiService,
    private val userService: UserApi,
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {

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
        launchFlow({ apiService.getArticle(map) },
            { articleResult.postValue(it) },
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
    fun getCollect(ok: (String?) -> Unit) {
        launchFlow({ userService.getCollect() }, { ok(it) })
    }

    /**
     * 更换头像
     */
    fun changeHeader(ok: (String?) -> Unit) {
        launchFlow({ userService.changeHeader() }, { ok(it) })
    }

    /**
     * 接化发
     */
    fun jhf(ok: (String?) -> Unit) {
        launchFlow({ userService.jhf() }, { ok(it) })
    }


}