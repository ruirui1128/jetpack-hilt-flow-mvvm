package com.lihui.hilt.ui.bindadapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mind.data.data.model.ArticleModel


object AnmiBa {
    /**
     * 动画
     */
    @JvmStatic
    @BindingAdapter(value = ["min"], requireAll = false)
    fun itemAnmiIv(
            view: ImageView,
            min: ArticleModel
    ) {
        if (min.isCollect&&view.isShown){
            val a1: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.3f,1f)
            val a2: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f,1.3f, 1f)
            val set = AnimatorSet()
            set.duration = 1500L
            set.play(a1).with(a2)
            set.start()
        }


    }
}