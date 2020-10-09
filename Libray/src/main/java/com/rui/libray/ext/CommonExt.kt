package com.rui.libray.ext

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.rui.libray.base.BaseConstant


/**
 * 扩展
 */

fun View.onClick(method:()->Unit):View{
    setOnClickListener { method() }
    return this
}


fun View.onClick(listener: View.OnClickListener):View{
    setOnClickListener(listener)
    return this
}


fun <T,BD: ViewDataBinding> baseLoadMoreAdapter(adapter: BaseQuickAdapter<T, BaseDataBindingHolder<BD>>,
                                        list: MutableList<T>, pageNumber: Int) :Int{
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


fun <T,BD: ViewDataBinding> baseInitLoadMoreAdapter(adapter: BaseQuickAdapter<T, BaseDataBindingHolder<BD>>,
                                                loadMoreView: BaseLoadMoreView, loadMore:()->Unit) {
    adapter.loadMoreModule.loadMoreView = loadMoreView
    adapter.loadMoreModule.setOnLoadMoreListener { loadMore() }
    adapter.loadMoreModule.isAutoLoadMore = true
    adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
}
