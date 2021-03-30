package com.lihui.hilt.ui.bindadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.lihui.hilt.enums.StarLevelEnum

object ImageBA {
    /**
     * 等级绑定
     */
    @JvmStatic
    @BindingAdapter(value = ["userLevel"])
    fun bindLevel(imageView: ImageView, userLevel: Int?) {
        imageView.setBackgroundResource(StarLevelEnum.match(userLevel))
    }
}