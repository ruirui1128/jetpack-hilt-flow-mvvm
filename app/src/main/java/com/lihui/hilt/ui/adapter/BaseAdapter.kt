package com.lihui.hilt.ui.adapter

import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.rui.libray.base.BaseConstant


object BaseAdapter {




    fun <T,BD:ViewDataBinding> baseAdapter(adapter: BaseQuickAdapter<T, BaseDataBindingHolder<BD>>,
                                           list: MutableList<T>,  pageNumber: Int) :Int{
        adapter.loadMoreModule.isEnableLoadMore = true
        adapter.loadMoreModule.isEnableLoadMore = true
        if (pageNumber==1) {
            adapter.setList(list)
        } else {
            adapter.addData(list)
        }
        val page =pageNumber+1
        if (list.size ?: 0 < BaseConstant.PAGESIZE_19) {
            adapter.loadMoreModule.loadMoreEnd()
        } else {
            adapter.loadMoreModule.loadMoreComplete()
        }

        return page
    }

}