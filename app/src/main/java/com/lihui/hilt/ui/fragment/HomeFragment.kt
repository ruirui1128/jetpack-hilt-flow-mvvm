package com.lihui.hilt.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.data.ds.DataStoreValue
import com.lihui.hilt.data.ds.DsUtil
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeVm, FragmentHomeBinding>() {

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
        //协程取dataStore 数据  数据改变会立即更新   三种方式任选其一
        // 注意 第一种有协程类型限制 详见说明
        lifecycleScope.launch {
            DsUtil.readSuspend<String>(DataStoreValue.TOKEN) {
                bind.tvToken.text = "token改变会立即更新:$it"
            }
        }
//        DsUtil.readScope<String>(key = DataStoreValue.TOKEN){
//            bind.tvToken.text = "token改变会立即更新:$it"
//        }
//
//        DsUtil.readScope<String>(lifecycleScope,DataStoreValue.TOKEN){
//            bind.tvToken.text = "token改变会立即更新:$it"
//        }

        initView()
        initAdapter()
        initVm()
        initData(true)
    }

    /**
     * 初次加载失败 点击重新加载
     */
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
                    //是否登录   这个操作也可以放在itemHomePresenter中 具体场景具体实现，没有固定要求
                    loginObserver(this) {
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
            pageNumber = adapter.loadMore(it?.list, pageNumber) { }
        })
    }

    private fun initView() {
        bind.swipe.init { initData(false) }
    }

}