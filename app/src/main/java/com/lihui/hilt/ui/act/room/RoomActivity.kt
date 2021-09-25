package com.lihui.hilt.ui.act.room

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.lihui.hilt.R
import com.lihui.hilt.databinding.ActivityRoomBinding
import com.mind.data.data.db.entity.WordEntity
import com.mind.lib.base.BaseActivity
import com.mind.lib.base.ViewModelConfig
import com.mind.lib.ext.onClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomActivity : BaseActivity<RoomVm, ActivityRoomBinding>() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, RoomActivity::class.java))
        }
    }

    @Inject
    lateinit var roomAdapter: RoomAdapter

    override val viewModelConfig: ViewModelConfig
        get() = ViewModelConfig(R.layout.activity_room)

    override fun initialize() {
        initView()
        bind.rcv.adapter = roomAdapter
        viewModel.listResult.observe(this, Observer {
            roomAdapter.setList(it)
        })
    }



    private fun initView() {
        bind.btnInsert.onClick { insertWord() }
        bind.btnGetAll.onClick { getAll() }
    }

    private fun getAll() {
        viewModel.getAllWords()
    }

    private fun insertWord() {
        val wordEntity = WordEntity(word = "张三丰", id = 0)
        viewModel.insertWord(wordEntity)
    }
}