package com.rui.libray.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rui.libray.data.bean.Res
import com.rui.libray.data.net.ResCode
import com.rui.libray.util.NetworkHelper


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn



open class BaseViewModel(): ViewModel(), LifecycleObserver {

    private var networkHelper: NetworkHelper? = null

    constructor(networkHelper: NetworkHelper) : this() {
        this.networkHelper = networkHelper
    }

    val uiChange: UIChange by lazy { UIChange() }

    override fun onCleared() {
        viewModelScope.cancel()
    }
    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     * @param isLoadMore 是否加载更多
     * @param isToastFore 是否自行处理res.message ,默认基类处理
     */
    fun <T>launchFlow(
        block: suspend CoroutineScope.() -> Res<T>,
        success: (T?) -> Unit,
        error: (String) -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        isLoadMore: Boolean = false,
        isToastFore: Boolean = false
    ){
        //是否显示对话框
        if (isShowDialog){ uiChange.showDialog.call() }
        //网络监测
        if (networkHelper?.isNetworkConnected() == false){
            uiChange.msgEvent.postValue(Message(code = ResCode.NETWORK_ERROR.getCode(),msg =ResCode.NETWORK_ERROR.getMessage() ))
            return
        }

        viewModelScope.launch {
            flow { emit(block()) }    //网络请求
                .flowOn(Dispatchers.IO)  //指定网络请求的线程
                .catch { t: Throwable ->   //异常捕获处理
                    error(t.message?:"")
                    if (isLoadMore){
                        uiChange.msgEvent.postValue(Message(code = ResCode.LOAD_MORE_ERROR.getCode()))
                    }else  {
                        uiChange.msgEvent.postValue(Message(code = ResCode.OTHER_ERROR.getCode(),msg = t.message?:""))
                    }
                }
                .collect {
                when(it.status){     //网络响应解析
                    ResCode.OK.getCode()-> success(it.data)
                    ResCode.TOKEN_ERROR.getCode()->  uiChange.msgEvent.postValue(Message(code = ResCode.TOKEN_ERROR.getCode(),msg = ResCode.TOKEN_ERROR.getMessage()))
                    else->{
                        error(it.message)
                        if (!isToastFore){
                            uiChange.msgEvent.postValue(Message(code = it.status,msg = it.message))
                        }
                    }

                }
            }
        }

        if (isShowDialog){ uiChange.dismissDialog.call() }
        complete()
    }

    inner class UIChange {
        val showDialog by lazy { ViewModelEvent<String>() }
        val dismissDialog by lazy { ViewModelEvent<Void>() }
        val msgEvent by lazy { ViewModelEvent<Message>() }
    }


}