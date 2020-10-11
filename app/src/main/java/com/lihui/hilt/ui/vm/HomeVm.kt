package com.lihui.hilt.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.lihui.hilt.data.bean.ArticleBean
import com.lihui.hilt.data.bean.BannerDataBean
import com.lihui.hilt.data.bean.InfoBean
import com.lihui.hilt.data.bean.PageList
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper

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
    val bannerResult = MutableLiveData<MutableList<BannerDataBean>>()
    fun getBanner(){
        launchFlow({apiService.getBanner()},{bannerResult.postValue(it)})
    }

    /**
     * 获取首页文章信息
     */
    val articleResult = MutableLiveData<PageList<ArticleBean>>()
    fun getArticleList(pageNumber: Int,firstLoad : Boolean,loadMoreError:()->Unit) {
        launchFlow({apiService.getArticle(pageNumber.toString())},
            {articleResult.postValue(it)},
            isStatueLayout = firstLoad,
            isLoadMore = (pageNumber!=1),
            loadMoreError = {loadMoreError()},
            complete = { isRefreshing.postValue(false)})

    }




}