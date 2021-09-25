package com.mind.lib.widget.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 1.禁止左右滑动
 * 2.点击立即切换，无切换过渡动画
 */
class SuperViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    private val helper: ViewPageHelper = ViewPageHelper(this)

    override fun setCurrentItem(item: Int) {
        setCurrentItem(item, true)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        val scroller = helper.scroller
        scroller.setNoDuration(true)
        super.setCurrentItem(item, smoothScroll)
        scroller.setNoDuration(false)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 禁止左右滑动
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // 可行,消费,拦截事件
        return true
    }
}