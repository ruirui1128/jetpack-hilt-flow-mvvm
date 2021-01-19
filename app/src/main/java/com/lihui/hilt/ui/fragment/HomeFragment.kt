package com.lihui.hilt.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.model.BannerDataModel
import com.lihui.hilt.databinding.FragmentHomeBinding
import com.lihui.hilt.ui.act.JhfActivity
import com.lihui.hilt.ui.adapter.ArticleAdapter
import com.lihui.hilt.ui.vm.HomeVm
import com.lihui.hilt.uitl.ToastUtil
import com.lihui.hilt.uitl.loginObserver
import com.lihui.indiamall.util.ClickUtil
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.ext.init

import com.rui.libray.ext.initLoadMore
import com.rui.libray.ext.loadMore
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeVm,FragmentHomeBinding>(){

    /**
     * fastmock接口不保存数据!!!  不保存数据!!!  不保存数据!!!
     * 仅仅是为了演示
     */

    private lateinit var adapter: ArticleAdapter

    private var pageNumber = 1

    public override val viewModelConfig: ViewModelConfig<HomeVm>
        get() = ViewModelConfig<HomeVm>(R.layout.fragment_home)
                .addViewModel(viewModels<HomeVm>().value, BR.homeVm)

    override fun init(savedInstanceState: Bundle?) {

        initView()
        initAdapter()
        initVm()
        initData(true)
    }

    override fun reLoad() {
        initData(true)
    }

    private fun initAdapter() {
        adapter = ArticleAdapter(viewModel, this)
        //加载更多 loadMore = getArticleList()   在扩展里面
        adapter.initLoadMore { getArticleList() }
        bind.rcvArticle.adapter = adapter
        adapter.setOnItemChildClickListener { ada, view, position ->
            if (ClickUtil.isFastDoubleClick) return@setOnItemChildClickListener
            when (view.id) {
                R.id.ivCollect -> {
                    //是否登录
                    loginObserver(this){
                        //数据请求成功 更新状态
                        viewModel.getCollect {
                            val item = adapter.getItem(position)
                            item.isCollect = !item.isCollect
                            item.notifyChange()
                        }
                    }
                }
            }
        }
        adapter?.setOnItemClickListener { ada, view, position ->
            val item = adapter.getItem(position)
            val intent = Intent(activity,JhfActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(JhfActivity.JHA_DATA,item)
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private fun initData(firstLoad: Boolean = false) {
        pageNumber = 1
        getArticleList(firstLoad)
    }

    //获取文章数据
    private fun getArticleList(firstLoad: Boolean = false) {
        viewModel.getArticleList(pageNumber, firstLoad) { adapter.loadMoreModule.loadMoreFail() }
    }

    private fun initVm() {
        //文章数据返回
        viewModel.articleResult.observe(this, Observer {
            pageNumber = adapter.loadMore(it?.list, pageNumber) {  }
        })
    }

    private fun initView() {
        bind.swipe.init { initData(false) }
    }

}