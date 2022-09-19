package com.lihui.hilt.ui.act.info

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.lihui.hilt.BR
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityInfoBinding
import com.lihui.hilt.ui.adapter.InfoAdapter
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.init
import com.mind.lib.ext.initLoadMore
import com.mind.lib.ext.loadMore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class InfoActivity : BaseActivity<InfoVm, ActivityInfoBinding>() {


    /**
     * 无  BaseAct中的 layoutstatue 情况
     * 也是最简单用法
     *
     * 如果嵌套NestedScrollView 参考github com.github.CymChad:BaseRecyclerViewAdapterHelper
     */
    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, InfoActivity::class.java))
        }
    }

    @Inject
    lateinit var adapter: InfoAdapter
    private var pageNumber = 1
    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_info)
            .bindViewModel(BR.infoVm)

    override fun initialize() {
        bind.swipeRefreshLayout.init { refresh(true) }
        adapter.initLoadMore { refresh(false) }
        bind.recyclerView.adapter = adapter
        viewModel.infoResult.observe(this, Observer {
            pageNumber = adapter.loadMore(it.list, pageNumber) { setEmpty() }
        })
        refresh(true)
    }

    private fun refresh(isRefresh: Boolean) {
        if (isRefresh) {
            pageNumber = 1
        }
        viewModel.getInfoList(pageNumber) { adapter.loadMoreModule.loadMoreFail() }
    }

    //设置空界面
    // 空界面
    // 多种方式
    private fun setEmpty() {
        //  1 简单
        adapter.setEmptyView(R.layout.layout_empty)
        // 2 带有空界面点击事件
        //  val view = View.inflate(this, R.layout.layout_empty, bind.recyclerView)
        //  view.findViewById<TextView>(R.id.tvTry).onClick { refresh(true) }
    }

}