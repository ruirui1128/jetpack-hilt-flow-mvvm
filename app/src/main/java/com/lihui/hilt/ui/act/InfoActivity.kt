package com.lihui.hilt.ui.act

import android.view.View
import androidx.activity.viewModels

import androidx.lifecycle.Observer
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityInfoBinding

import com.lihui.hilt.ui.adapter.InfoAdapter
import com.lihui.hilt.ui.vm.InfoVm
import com.rui.libray.base.BaseActivity

import com.rui.libray.base.ViewModelConfig

import com.rui.libray.ext.*

import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : BaseActivity<InfoVm,ActivityInfoBinding>() {

    @Inject lateinit var adapter:InfoAdapter
    private var pageNumber = 1
    override val viewModelConfig: ViewModelConfig<InfoVm>
        get() = ViewModelConfig<InfoVm>(R.layout.activity_info)
            .addViewModel(viewModels<InfoVm>().value,BR.infoVm)

    override fun init() {
        bind.swipeRefreshLayout.init{refresh(true)}
        adapter.initLoadMore{refresh(false)}
        bind.recyclerView.adapter = adapter
        viewModel.infoResult.observe(this, Observer {
            pageNumber = adapter.loadMore(it.list,pageNumber){loadSirShowEmpty()}
        })
        refresh(true, firstLoad = true)
    }

    private fun refresh(isRefresh: Boolean,firstLoad : Boolean = false) {
        if (isRefresh){  pageNumber = 1}
        viewModel.getInfoList(pageNumber,firstLoad){adapter.loadMoreModule.loadMoreFail()}
    }

}