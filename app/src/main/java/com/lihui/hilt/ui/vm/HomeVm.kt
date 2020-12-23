package com.lihui.hilt.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.data.model.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import kotlinx.coroutines.flow.collect

class HomeVm  @ViewModelInject constructor(
    private val apiService: ApiService,
    networkHelper: NetworkHelper):BaseViewModel(networkHelper) {


    /**
     * 界面刷新
     */
    val isRefreshing = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    /**
     * 获取Banner 数据
     */
    val bannerResult = MutableLiveData<MutableList<BannerDataModel>>()
    fun getBanner(){
        launchFlow({apiService.getBanner()},{bannerResult.postValue(it)})
    }

    /**
     * 获取首页文章信息
     */
    val articleResult = MutableLiveData<PageList<ArticleModel>>()
    fun getArticleList(pageNumber: Int,firstLoad : Boolean,loadMoreError:()->Unit) {
        val map = hashMapOf("pageNumber" to pageNumber.toString(),
        "pageSize" to "20")
        launchFlow({apiService.getArticle(map)},
            {articleResult.postValue(it)},
            isStatueLayout = firstLoad,
            isLoadMore = (pageNumber!=1),
            loadMoreError = {loadMoreError()},
            complete = { isRefreshing.postValue(false)})

    }

    /**
     * 是否收藏成功   这里无须在viewmodel 设置 livedata 监听数据返回
     *
     * 而是直接返回
     *
     * 注意区分场景
     */
     fun getCollect(ok:(String?)->Unit){
         launchData({apiService.getCollect()},{ok(it)})
    }




}