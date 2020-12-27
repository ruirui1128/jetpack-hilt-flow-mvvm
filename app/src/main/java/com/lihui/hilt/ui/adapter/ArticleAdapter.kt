package com.lihui.hilt.ui.adapter


import androidx.lifecycle.LifecycleOwner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.R
import com.lihui.hilt.data.model.ArticleModel
import com.lihui.hilt.databinding.ItemArticleBinding
import com.lihui.hilt.event.MessageEvent


import com.lihui.hilt.ui.vm.HomeVm


class ArticleAdapter(
    private val viewModel: HomeVm,
    private val owner: LifecycleOwner
) : BaseQuickAdapter<ArticleModel,
        BaseDataBindingHolder<ItemArticleBinding>>(R.layout.item_article), LoadMoreModule {

    init {
        //添加局部点击事件
        addChildClickViewIds(R.id.ivCollect)
    }

    override fun convert(holder: BaseDataBindingHolder<ItemArticleBinding>,  item: ArticleModel) {
        if (item.random.length > 8) {
            item.random = item.random.substring(0, 8)
        }
        holder.dataBinding?.itemArticleVm = item
        holder.dataBinding?.itemHomeVm = viewModel
        holder.dataBinding?.executePendingBindings()

        LiveEventBus.get(
            MessageEvent.ITEM_JHF_EVENT,
            ArticleModel::class.java
        ).observe(owner) {
            if (item.random == it?.random) {
                item.isCollect = it.isCollect
                item.isFa = it.isFa
                item.isJie = it.isJie
                item.isHua = it.isHua
                item.notifyChange()
            }
        }
    }


}