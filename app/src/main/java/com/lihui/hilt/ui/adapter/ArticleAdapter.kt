package com.lihui.hilt.ui.adapter

import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lihui.hilt.R
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.databinding.ItemArticleBinding
import com.lihui.hilt.ui.presenter.ItemHomePresenter
import com.lihui.hilt.ui.vm.HomeVm
import com.lihui.hilt.uitl.ToastUtil
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject

class ArticleAdapter  @Inject constructor(
       val itemHomePresenter: ItemHomePresenter,

):BaseQuickAdapter<ArticleModel,
        BaseDataBindingHolder<ItemArticleBinding>>(R.layout.item_article),LoadMoreModule {



//    @Inject lateinit var  itemHomePresenter: ItemHomePresenter

    init {
        //添加局部点击事件
        addChildClickViewIds(R.id.ivCollect)
        
    }

    override fun convert(holder: BaseDataBindingHolder<ItemArticleBinding>, item: ArticleModel) {
        holder.dataBinding?.itemArticleVm = item
        holder.dataBinding?.itemHomePresenter = itemHomePresenter
        holder.dataBinding?.executePendingBindings()


    }
}