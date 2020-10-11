package com.lihui.hilt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lihui.hilt.R
import com.rui.libray.base.BaseFragment
import com.rui.libray.base.BaseViewModel

class HomePageAdapter (private val mFm: FragmentManager,
                       private val list: MutableList<BaseFragment<out BaseViewModel>>) :
    FragmentPagerAdapter(mFm) {

    private val titles = arrayOf("主页", "项目", "我的")

    private val images = intArrayOf(
        R.drawable.btm_home_selector,
        R.drawable.btm_dash_selector,
        R.drawable.btm_user_selector
    )

    override fun getItem(position: Int): Fragment {
        return   list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        mFm.beginTransaction().show(fragment).commitAllowingStateLoss()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        mFm.beginTransaction().hide(`object` as Fragment).commitAllowingStateLoss()
    }


    fun getTabView(context: Context, position: Int): View {
        val v = LayoutInflater.from(context).inflate(R.layout.home_tablayout_icon, null)
        val textView = v.findViewById<TextView>(R.id.tlText)
        val imageView = v.findViewById<ImageView>(R.id.tlIcon)
        textView.text = titles[position]
        imageView.setImageResource(images[position])
        return v
    }


}
