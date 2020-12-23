package com.lihui.hilt.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lihui.hilt.R
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.databinding.ItemArticleBinding
import javax.inject.Inject

class ArticleAdapter  @Inject constructor():BaseQuickAdapter<ArticleModel,
        BaseDataBindingHolder<ItemArticleBinding>>(R.layout.item_article),LoadMoreModule {

    init {
        //添加局部点击事件
//        addChildClickViewIds(R.id.ivCollect)
    }

    override fun convert(holder: BaseDataBindingHolder<ItemArticleBinding>, item: ArticleModel) {
        holder.dataBinding?.itemArticleVm = item
        holder.dataBinding?.executePendingBindings()
        addChildClickViewIds(R.id.ivCollect)
    }
}