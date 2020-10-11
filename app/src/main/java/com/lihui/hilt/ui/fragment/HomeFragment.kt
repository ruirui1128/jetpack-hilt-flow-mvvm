package com.lihui.hilt.ui.fragment

import android.os.Bundle

import android.view.View

import android.widget.ImageView

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.bean.BannerDataBean
import com.lihui.hilt.ui.adapter.ArticleAdapter
import com.lihui.hilt.ui.vm.HomeVm
import com.lihui.hilt.ui.vm.MainViewModel
import com.lihui.hilt.uitl.ToastUtil
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.init
import com.rui.libray.ext.initLoadMore
import com.rui.libray.ext.loadMore
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeVm>(), BGABanner.Delegate<ImageView, BannerDataBean>,
    BGABanner.Adapter<ImageView, BannerDataBean> {

    @Inject lateinit var adapter : ArticleAdapter
    private var pageNumber = 1


    override fun setStatusLayoutAndListener(): View? {
        reloadListener = { initData(true) }
        return swipe
    }


    override val viewModelConfig: ViewModelConfig<HomeVm>
        get() = ViewModelConfig<HomeVm>(R.layout.fragment_home)
            .addViewModel(viewModels<HomeVm>().value,BR.homeVm)

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initAdapter()
        initVm()
        initData(true)
    }

    private fun initAdapter() {
        adapter.initLoadMore { getArticleList() }
        rcvArticle.adapter = adapter
    }

    private fun initData(firstLoad : Boolean = false) {
        pageNumber = 1
        //获取banner数据
        viewModel.getBanner()
        getArticleList(firstLoad)
    }
    //获取文章数据
    private fun getArticleList(firstLoad : Boolean = false){
        viewModel.getArticleList(pageNumber,firstLoad){adapter.loadMoreModule.loadMoreFail()}
    }

    private fun initVm() {
        //banner数据返回
        viewModel.bannerResult.observe(this, Observer {
          //  banner.setData(it,null)
        })
        //文章数据返回
        viewModel.articleResult.observe(this, Observer {
            pageNumber = adapter.loadMore(it.datas,pageNumber){loadSirShowEmpty()}
        })
    }

    private fun initView() {
        swipe.init { initData(false) }
//        banner.setAdapter(this)
//        banner.setDelegate(this)
    }


    override fun onBannerItemClick(
        banner: BGABanner?,
        itemView: ImageView?,
        model: BannerDataBean?,
        position: Int
    ) {
        ToastUtil.toast("点击了$position")
    }

    override fun fillBannerItem(
        banner: BGABanner?,
        itemView: ImageView?,
        model: BannerDataBean?,
        position: Int
    ) {
        itemView?.context?:return
        val roundedCorners = RoundedCorners(10);
        val options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(itemView.context)
            .load(model?.imagePath)
            .apply(options)
            .into(itemView)
    }
}