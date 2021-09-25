package com.lihui.hilt.ui.act.room

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lihui.hilt.R
import com.mind.data.data.db.entity.WordEntity

import javax.inject.Inject

class RoomAdapter @Inject constructor() :
    BaseQuickAdapter<WordEntity, BaseViewHolder>(R.layout.item_room) {
    override fun convert(holder: BaseViewHolder, item: WordEntity) {
        holder.setText(R.id.tvName, item.word + item.id)
    }
}