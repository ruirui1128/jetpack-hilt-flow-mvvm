package com.lihui.hilt.ui.act

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lihui.hilt.BR
import com.lihui.hilt.R

import com.lihui.hilt.ui.adapter.InfoAdapter
import com.lihui.hilt.ui.vm.InfoVm
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.Message
import com.rui.libray.base.ViewModelConfig
import com.rui.libray.data.net.ResCode
import com.rui.libray.ext.*

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_info.*
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : BaseActivity<InfoVm>() {

    @Inject lateinit var adapter:InfoAdapter

    private var pageNumber = 1

    override val viewModelConfig: ViewModelConfig<InfoVm>
        get() = ViewModelConfig<InfoVm>(R.layout.activity_info)
            .addViewModel(viewModels<InfoVm>().value,BR.infoVm)

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
        adapter.initLoadMore{refresh(false)}
        recyclerView.adapter = adapter
    }

    private fun initVm() {
        viewModel.infoResult.observe(this, Observer {
            pageNumber = adapter.loadMore(it.datas,pageNumber)
        })
    }

    private fun initView() {
        swipeRefreshLayout.init{refresh(true)}
    }

    private fun refresh(isRefresh: Boolean) {
        if (isRefresh){  pageNumber = 1}
        viewModel.getInfoList(pageNumber){adapter.loadMoreModule.loadMoreFail()}
    }

}