package com.lihui.hilt.ui.bindadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.lihui.hilt.enums.StarLevelEnum
import com.lihui.hilt.ui.fragment.user.UserVm
import com.lihui.hilt.uitl.GlideUtil
import com.rui.libray.ext.onClick

object ImageBA {
    /**
     * 等级绑定
     */
    @JvmStatic
    @BindingAdapter(value = ["userLevel"])
    fun bindLevel(imageView: ImageView, userLevel: Int?) {
        imageView.setBackgroundResource(StarLevelEnum.match(userLevel))
    }

    /**
     * 头像改变
     */
    @JvmStatic
    @BindingAdapter(value = ["userHeaderVm"])
    fun bindUserHeader(imageView: ImageView?, userHeaderVm: UserVm?) {
        val lifecycleOwner = imageView?.findViewTreeLifecycleOwner()
        lifecycleOwner ?: return
        userHeaderVm?.changeResult?.observe(lifecycleOwner, Observer {
            GlideUtil.loadHeader(imageView, it)
        })
        imageView.onClick { userHeaderVm?.changeHeader() }
    }

    /**
     * 用户等级改变
     */
    @JvmStatic
    @BindingAdapter(value = ["userLevelVm"])
    fun bindUserLevel(imageView: ImageView?, userLevelVm: UserVm?) {
        val lifecycleOwner = imageView?.findViewTreeLifecycleOwner()
        lifecycleOwner ?: return
        userLevelVm?.level?.observe(lifecycleOwner, Observer {
            imageView.setBackgroundResource(StarLevelEnum.match(it))
        })
        imageView.onClick { userLevelVm?.level?.value = (0..4).random() }
    }

}