package com.lihui.hilt.widget.user

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.lihui.hilt.R
import com.lihui.hilt.uitl.ToastUtil
import com.lihui.hilt.widget.ItemSettingView
import com.rui.libray.ext.onClick

class UserActionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) , View.OnClickListener {
    init {
        View.inflate(context, R.layout.view_user_action,this)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<ItemSettingView>(R.id.llService).onClick(this)
        findViewById<ItemSettingView>(R.id.llProtocol).onClick(this)
        findViewById<ItemSettingView>(R.id.llAd).onClick(this)
        findViewById<ItemSettingView>(R.id.llFeedback).onClick(this)
        findViewById<ItemSettingView>(R.id.llAboutUs).onClick(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llService-> ToastUtil.toast("llService")
            R.id.llProtocol-> ToastUtil.toast("llProtocol")
            R.id.llAd-> ToastUtil.toast("llAd")
            R.id.llFeedback-> ToastUtil.toast("llFeedback")
            R.id.llAboutUs-> ToastUtil.toast("llAboutUs")
        }
    }

}