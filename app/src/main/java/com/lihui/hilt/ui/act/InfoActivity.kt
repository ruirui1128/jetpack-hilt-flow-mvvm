package com.lihui.hilt.ui.act

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lihui.hilt.R
import com.lihui.hilt.base.BaseConstant.PAGESIZE_19
import com.lihui.hilt.ui.adapter.InfoAdapter
import com.lihui.hilt.vm.InfoVm
import com.lihui.hilt.widget.loadmore.CustomLoadMoreView
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.Message
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.data.net.ResCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_info.*
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : BaseActivity<InfoVm>() {

    @Inject lateinit var adapter:InfoAdapter

    private var refresh = true;

    private var pageNumber = 1

    override val viewModelConfig: ViewModelConfig<InfoVm>
        get() = ViewModelConfig<InfoVm>(R.layout.activity_info)
            .addViewModel(viewModels<InfoVm>().value)

    override fun init() {
        initView()
        initAdapter()
        initVm()
        initData()
    }

    private fun initData() {
        refresh()
    }

    private fun initAdapter() {
        adapter.loadMoreModule.loadMoreView = CustomLoadMoreView()
        adapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
        adapter.loadMoreModule.isAutoLoadMore = true
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        recyclerView.adapter = adapter

    }


    private fun initVm() {
        viewModel.infoResult.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = false
            adapter.loadMoreModule.isEnableLoadMore = true
            val list = it.datas
            if (refresh) {
                adapter.setList(list)
            } else {
                adapter.addData(list)

            }
            pageNumber++
            if (list.size ?: 0 < PAGESIZE_19) {
                adapter.loadMoreModule.loadMoreEnd()
            } else {
                adapter.loadMoreModule.loadMoreComplete()
            }
        })
    }

    private fun initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_999)
        swipeRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun loadMore() {
        refresh = false
        viewModel.getInfoList(pageNumber)
    }

    private fun refresh() {
        refresh = true
        pageNumber = 1
        viewModel.getInfoList(pageNumber)
    }

    //加载更多失败，重新加载
    override fun handleEvent(msg: Message) {
        super.handleEvent(msg)
        swipeRefreshLayout.isRefreshing = false
        if (msg.code == ResCode.LOAD_MORE_ERROR.getCode()){
            adapter.loadMoreModule.loadMoreFail()
        }
    }



}