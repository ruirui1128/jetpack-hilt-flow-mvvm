package com.lihui.hilt.ui.vm


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData

import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.api.UserApi
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper


class HomeVm @ViewModelInject constructor(
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
        launchData({ apiService.getBanner() }, { ok(it) })
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
        launchData({ userService.getCollect() }, { ok(it) })
    }

    /**
     * 更换头像
     */
    fun changeHeader(ok: (String?) -> Unit) {
        launchData({ userService.changeHeader() }, { ok(it) })
    }

    /**
     * 接化发
     */
    fun jhf(ok: (String?) -> Unit) {
        launchData({ userService.jhf() }, { ok(it) })
    }



//    fun test() {
//
//        val name = Thread.currentThread().name
//        val id = Thread.currentThread().id
//        Log.e("HomeVm", "调用者------当前线程名称:$name---------id:---$id")
//
//
//        viewModelScope.launch {
//            //   f1()
//            val a = withContext(Dispatchers.IO) {
//                f1()
//            }
//            val names = Thread.currentThread().name
//            val ids = Thread.currentThread().id
//            Log.e("HomeVm", "返回------当前线程名称:$names---------id:---$ids--------$a---------------")
//
//        }
//
//
//    }
//
//
//    suspend fun f1(): String {
//        delay(100L)
//        val name = Thread.currentThread().name
//        val id = Thread.currentThread().id
//        Log.e("HomeVm", "协程-------当前线程名称:$name---------id:---$id")
//        return "3333333333333333333333"
//    }


}