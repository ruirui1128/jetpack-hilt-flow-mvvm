package com.lihui.hilt.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ItemInfoBinding
import com.mind.data.data.model.ArticleModel
import javax.inject.Inject

class InfoAdapter @Inject constructor() :BaseQuickAdapter<ArticleModel,
        BaseDataBindingHolder<ItemInfoBinding>>(R.layout.item_info), LoadMoreModule {
    override fun convert(holder: BaseDataBindingHolder<ItemInfoBinding>, item: ArticleModel) {
        holder.dataBinding?.infoVm = item
        holder.dataBinding?.executePendingBindings()
    }
}