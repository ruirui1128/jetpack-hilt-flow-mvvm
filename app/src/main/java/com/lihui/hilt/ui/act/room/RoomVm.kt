package com.lihui.hilt.ui.act.room

import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.room.dao.WordDao
import com.lihui.hilt.data.room.entity.WordEntity
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomVm @Inject constructor(
    private val wordDao: WordDao?,
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {

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