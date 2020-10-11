package com.lihui.hilt.ui.bindadapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lihui.hilt.data.bean.ArticleBean


object TextBA {

    /**
     * itemArticle
     */
    @JvmStatic
    @BindingAdapter(value = ["data"], requireAll = false)
    fun itemArticleTv(
        textView: TextView,
        data:ArticleBean
    ) {
        if (data.tags.isNotEmpty()){
            textView.visibility = View.VISIBLE
            textView.text = data.tags[0].name
        }else{
            textView.visibility = View.GONE
        }
    }

}