package com.lihui.hilt.ui.act

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lihui.hilt.R

import com.lihui.hilt.ui.adapter.InfoAdapter
import com.lihui.hilt.ui.vm.InfoVm
import com.lihui.hilt.widget.loadmore.CustomLoadMoreView
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.Message
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.data.net.ResCode
import com.rui.libray.ext.baseInitLoadMoreAdapter

import com.rui.libray.ext.baseLoadMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_info.*
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : BaseActivity<InfoVm>() {

    @Inject lateinit var adapter:InfoAdapter

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
        refresh(true)
    }

    private fun initAdapter() {
        baseInitLoadMoreAdapter(adapter, CustomLoadMoreView()){refresh(false)}
        recyclerView.adapter = adapter
    }


    private fun initVm() {
        viewModel.infoResult.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = false
            pageNumber = baseLoadMoreAdapter(adapter,it.datas,pageNumber)
        })
    }

    private fun initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_999)
        swipeRefreshLayout.setOnRefreshListener { refresh(true) }
    }


    private fun refresh(isRefresh: Boolean) {
        if (isRefresh){  pageNumber = 1}
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