package com.lihui.hilt.ui.act.home


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityHomeBinding
import com.lihui.hilt.event.MessageEvent.TOKEN_OUT
import com.lihui.hilt.task.TimeLifecycleTask
import com.lihui.hilt.ui.adapter.HomePageAdapter
import com.lihui.hilt.ui.fragment.DashboardFragment
import com.lihui.hilt.ui.fragment.home.HomeFragment
import com.lihui.hilt.ui.fragment.user.UserFragment
import com.lihui.hilt.uitl.ToastUtil
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.BaseFragment
import com.mind.lib.base.BaseViewModel
import com.mind.lib.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<BaseViewModel, ActivityHomeBinding>() {

    private val SELECTED_INDEX = "selected_index"

    /**
     * 当前点击的tab索引
     */
    private var mCurrentPosition: Int = 0


    private val list: MutableList<BaseFragment<out BaseViewModel, out ViewDataBinding>> =
        arrayListOf(
            HomeFragment(), DashboardFragment(),
            UserFragment()
        )

    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_home)

    override fun initialize() {
        // 执行异步定时任务
        lifecycle.addObserver(TimeLifecycleTask())
        initBottomNav()
        initBus()
    }

    private fun initBus() {
        LiveEventBus.get(TOKEN_OUT)
            .observe(this, Observer { ToastUtil.toast(Thread.currentThread().name) })

    }

    private fun initBottomNav() {
        bind.homeViewPage.offscreenPageLimit = 3
        val adapter = HomePageAdapter(supportFragmentManager, list)
        bind.homeViewPage.adapter = adapter

        for (i in list.indices) {
            bind.homeTablaLayout.addTab(bind.homeTablaLayout.newTab())
            val tab = bind.homeTablaLayout.getTabAt(i)
            tab?.customView = adapter?.getTabView(baseContext, i)
        }

        bind.homeTablaLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchTab(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    /**
     * 切换Tab，切换对应的Fragment
     */
    private fun switchTab(position: Int) {
        bind.homeViewPage.currentItem = position
        mCurrentPosition = position
    }

    @SuppressLint("MissingSuperCall")
    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_INDEX, mCurrentPosition)
    }

    protected override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SELECTED_INDEX)
            bind.homeTablaLayout.getTabAt(mCurrentPosition)?.select()
        }
    }
}