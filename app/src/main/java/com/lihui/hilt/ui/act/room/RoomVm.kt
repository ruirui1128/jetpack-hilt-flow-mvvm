package com.lihui.hilt.ui.act.room

import androidx.lifecycle.MutableLiveData
import com.mind.data.data.db.dao.WordDao
import com.mind.data.data.db.entity.WordEntity
import com.mind.lib.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomVm @Inject constructor(
    private val wordDao: WordDao?,
) : BaseViewModel() {

    /**
     * 插入数据
     */
    fun insertWord(wordEntity: WordEntity) {
        launchRoom { wordDao?.insertWords(wordEntity) }
    }

    /**
     * 数据库查看
     */
    val listResult = MutableLiveData<MutableList<WordEntity>>()
    fun getAllWords() {
        // launchRoom 中指定了 Dispatchers.IO ,接受返回值必须用postValue
        launchRoom { listResult.postValue(wordDao?.getAllWords()) }
    }
}