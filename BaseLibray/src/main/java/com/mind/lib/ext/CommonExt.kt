package com.mind.lib.ext

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mind.lib.R
import com.mind.lib.base.BaseConstant
import com.mind.lib.widget.loadmore.CustomLoadMoreView


/**
 * Created by rui
 *  on 2021/8/5
 *  公共扩展
 */
const val MIN_CLICK_DELAY_TIME = 300L
private const val lastClickTime: Long = 0

/**
 * 点击事件扩展
 * quickly-false 默认防止连点
 * quickly-true 可以进行连续点击
 *
 */
fun View.onClick(quickly: Boolean = false, method: () -> Unit): View {
    setOnClickListener {
        if (quickly) {
            method()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                method()
            }
        }
    }
    return this
}

fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)

    return this
}


//adapter加载更多
fun <T, BD : ViewDataBinding> BaseQuickAdapter<T, BaseDataBindingHolder<BD>>.loadMore(
    list: MutableList<T>?, pageNumber: Int, emptyListener: () -> Unit = {}
): Int {
    loadMoreModule.isEnableLoadMore = true
    if (pageNumber == 1) {
        if (list == null || list.size == 0) {
            setList(null)
            emptyListener()
            return 1
        } else {
            setList(list)
        }

    } else {
        addData(list as MutableList<T>)
    }
    val page = pageNumber + 1
    if (list?.size ?: 0 < BaseConstant.PAGESIZE_20) {
        loadMoreModule.loadMoreEnd()
    } else {
        loadMoreModule.loadMoreComplete()
    }
    return page
}

/**
 * adapter加载更多
 * 带有初始化网络加载失败（不使用BaseActivity或者BaseFragment中的layoutStatue）
 */
fun <T, BD : ViewDataBinding> BaseQuickAdapter<T, BaseDataBindingHolder<BD>>.loadMore2(
    recyclerView: RecyclerView,
    list: MutableList<T>?, pageNumber: Int,
    isFirstLoad: Boolean = false,
    reload: () -> Unit,
    emptyListener: () -> Unit = {}
): Int {
    loadMoreModule.isEnableLoadMore = true
    if (pageNumber == 1) {
        if (isFirstLoad) {
            val inflate = LayoutInflater.from(recyclerView.context)
                .inflate(R.layout.layout_error, recyclerView)
            inflate.findViewById<Button>(R.id.btnReLoad).onClick { reload() }
            setEmptyView(inflate)
        } else {
            if (list == null || list.size == 0) {
                setList(null)
                emptyListener()
                return 1
            } else {
                setList(list)
            }
        }

    } else {
        addData(list as MutableList<T>)
    }
    val page = pageNumber + 1
    if (list?.size ?: 0 < BaseConstant.PAGESIZE_20) {
        loadMoreModule.loadMoreEnd()
    } else {
        loadMoreModule.loadMoreComplete()
    }
    return page
}


//初始化adapter
fun <T, BD : ViewDataBinding> BaseQuickAdapter<T, BaseDataBindingHolder<BD>>.initLoadMore(
    loadMoreView: BaseLoadMoreView = CustomLoadMoreView(),
    loadMore: () -> Unit
) {
    loadMoreModule.loadMoreView = loadMoreView
    loadMoreModule.setOnLoadMoreListener { loadMore() }
    loadMoreModule.isAutoLoadMore = true
    loadMoreModule.isEnableLoadMoreIfNotFullPage = false
}


//初始化 SwipeRefreshLayout
fun SwipeRefreshLayout.init(refreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            refreshListener()
        }
        setColorSchemeResources(R.color.colorPrimary)
    }
}
