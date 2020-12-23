package com.lihui.hilt.ui.act

import android.view.View
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
    /**
     * 带有StatusLayout布局界面 必须复写此方法
     */
    override fun setStatusLayoutAndListener(): View? {
        reloadListener = { refresh(true) }
        return swipeRefreshLayout
    }
    override fun init() {
        swipeRefreshLayout.init{refresh(true)}
        adapter.initLoadMore{refresh(false)}
        recyclerView.adapter = adapter
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