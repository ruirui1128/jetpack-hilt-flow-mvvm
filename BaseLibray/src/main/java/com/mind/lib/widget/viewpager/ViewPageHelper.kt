package com.mind.lib.widget.viewpager


import androidx.viewpager.widget.ViewPager

class ViewPageHelper(internal var viewPager: ViewPager) {

    lateinit var scroller: MScroller
        internal set

    init {
        init()
    }


    @JvmOverloads
    fun setCurrentItem(item: Int, somoth: Boolean = true) {
        val current = viewPager.currentItem
        //如果页面相隔大于1,就设置页面切换的动画的时间为0
        if (Math.abs(current - item) > 1) {
            scroller.setNoDuration(true)
            viewPager.setCurrentItem(item, somoth)
            scroller.setNoDuration(false)
        } else {
            scroller.setNoDuration(false)
            viewPager.setCurrentItem(item, somoth)
        }
    }

    private fun init() {
        scroller = MScroller(viewPager.context)
        val cl = ViewPager::class.java
        try {
            val field = cl.getDeclaredField("mScroller")
            field.isAccessible = true
            //利用反射设置mScroller域为自己定义的MScroller
            field.set(viewPager, scroller)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

}