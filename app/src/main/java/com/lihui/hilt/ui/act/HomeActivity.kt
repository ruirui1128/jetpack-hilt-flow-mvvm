package com.lihui.hilt.ui.act


import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.lihui.hilt.R
import com.lihui.hilt.ui.adapter.HomePageAdapter
import com.lihui.hilt.ui.fragment.DashboardFragment
import com.lihui.hilt.ui.fragment.HomeFragment
import com.lihui.hilt.ui.fragment.UserFragment
import com.rui.libray.base.BaseActivity
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.BaseViewModel
import com.rui.libray.base.ViewModelConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : BaseActivity<BaseViewModel>() {

    private val SELECTED_INDEX = "selected_index"

    /**
     * 当前点击的tab索引
     */
    private var mCurrentPosition: Int = 0


    private val list: MutableList<BaseFragment<out BaseViewModel>>
            = arrayListOf(HomeFragment(), DashboardFragment(), UserFragment())

    override val viewModelConfig: ViewModelConfig<BaseViewModel>
        get() = ViewModelConfig<BaseViewModel>(R.layout.activity_home)
            .addViewModel(viewModels<BaseViewModel>().value)
    override fun init() {
        initBottomNav()
    }

    private fun initBottomNav() {
        homeViewPage.offscreenPageLimit = 3
        val adapter = HomePageAdapter(supportFragmentManager,list)
        homeViewPage.adapter = adapter

        for (i in list.indices) {
            homeTablaLayout.addTab(homeTablaLayout.newTab())
            val tab = homeTablaLayout.getTabAt(i)
            tab?.customView = adapter?.getTabView(baseContext, i)
        }

        homeTablaLayout.addOnTabSelectedListener(object :OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchTab(tab?.position?:0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    /**
     * 切换Tab，切换对应的Fragment
     */
    private fun switchTab(position: Int) {
        homeViewPage.currentItem = position
        mCurrentPosition = position
    }

    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_INDEX, mCurrentPosition)
    }

    protected override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SELECTED_INDEX)
            homeTablaLayout.getTabAt(mCurrentPosition)?.select()
        }
    }
}