package com.rui.libray.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rui.libray.data.bean.Res
import com.rui.libray.data.net.ResCode
import com.rui.libray.util.NetworkHelper


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


open class BaseViewModel() : ViewModel(), LifecycleObserver {

    private var networkHelper: NetworkHelper? = null

    constructor(networkHelper: NetworkHelper) : this() {
        this.networkHelper = networkHelper
    }

    val uiChange: UIChange by lazy { UIChange() }

    //当组件结束时，会取消协程内的任务
    override fun onCleared() {
        viewModelScope.cancel()
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param loadMoreError 加载更多失败
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     * @param isLoadMore 是否加载更多
     * @param isToastFore 是否自行处理res.message ,默认基类处理
     * @param isStatueLayout 是否有加载替换的布局
     */
    fun <T> launchFlow(
        block: suspend CoroutineScope.() -> Res<T>,
        success: (T?) -> Unit,
        error: (String) -> Unit = {},
        loadMoreError: () -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        isStatueLayout: Boolean = false,
        isLoadMore: Boolean = false,
        isToastFore: Boolean = false
    ) {
        //是否显示对话框
        if (isShowDialog) {
            uiChange.showDialog.call()
        }
        //是否显示加载界面
        if (isStatueLayout) {
            uiChange.statueShowLoading.call()
        } //数据加载完成 界面切换}
        //网络监测
        if (networkHelper?.isNetworkConnected() == false) {
            uiChange.msgEvent.postValue(
                Message(
                    code = ResCode.NETWORK_ERROR.getCode(),
                    msg = ResCode.NETWORK_ERROR.getMessage()
                )
            )
            return
        }
        viewModelScope.launch {
            flow { emit(block()) }    //网络请求
                .flowOn(Dispatchers.IO)  //指定网络请求的线程
                .catch { t: Throwable ->   //异常捕获处理
                    error(t.message ?: "")   //有基类自行处理,业务层也可以自行处理
                    if (isLoadMore) {   //isLoadMore 为true 表示数据已经加载第二条数据 则不需要显示statueError
                        loadMoreError()  //交由业务层自行处理
                    } else {
                        if (isStatueLayout) {
                            uiChange.statueError.call()   //显示数据加载失败界面
                        } else {
                            uiChange.msgEvent.postValue(
                                Message(
                                    code = ResCode.OTHER_ERROR.getCode(),
                                    msg = t.message ?: ""
                                )
                            )
                        }
                    }
                }
                //数据请求返回处理  emit(block()) 返回的数据
                .collect {

                    when (it.code) {     //网络响应解析
                        ResCode.OK.getCode() -> {
                            if (isStatueLayout) {
                                uiChange.statueSuccess.call()
                            } //数据加载完成 界面切换}
                            success(it.data)  //数据加载完成交由业务层处理
                        }
                        //token 失效
                        ResCode.TOKEN_ERROR.getCode() -> uiChange.msgEvent.postValue(
                            Message(
                                code = ResCode.TOKEN_ERROR.getCode(),
                                msg = ResCode.TOKEN_ERROR.getMessage()
                            )
                        )
                        else -> {
                            error(it.message)
                            if (!isToastFore) { //toast 是否有业务层自行处理
                                uiChange.msgEvent.postValue(
                                    Message(code = it.code, msg = it.message)
                                )
                            }
                        }

                    }
                }
        }
        if (isShowDialog) {
            uiChange.dismissDialog.call()
        }
        complete()
    }


    /**
     * 数据直接返回
     */
    fun <T> launchData(
        block: suspend CoroutineScope.() -> Res<T>,
        ok: (T?) -> Unit,
        error: (String) -> Unit = {},
        isShowDialog: Boolean = false,
    ) {
        //是否显示对话框
        if (isShowDialog) {
            uiChange.showDialog.call()
        }
        //网络监测
        if (networkHelper?.isNetworkConnected() == false) {
            uiChange.msgEvent.postValue(
                Message(
                    code = ResCode.NETWORK_ERROR.getCode(),
                    msg = ResCode.NETWORK_ERROR.getMessage()
                )
            )
            return
        }
        viewModelScope.launch {
            flow { emit(block()) }
                .flowOn(Dispatchers.IO)
                .catch { t: Throwable ->
                    error(t.message ?: "")
                    uiChange.msgEvent.postValue(
                        Message(
                            code = ResCode.OTHER_ERROR.getCode(),
                            msg = t.message ?: ""
                        )
                    )
                }
                .collect {
                    when (it.code) {
                        ResCode.OK.getCode() -> {
                            ok(it.data)
                        }
                        //token 失效
                        ResCode.TOKEN_ERROR.getCode() -> uiChange.msgEvent.postValue(
                            Message(
                                code = ResCode.TOKEN_ERROR.getCode(),
                                msg = ResCode.TOKEN_ERROR.getMessage()
                            )
                        )
                        else -> {
                            error("")
                            uiChange.msgEvent.postValue(Message(code = it.code, msg = it.message))
                        }

                    }
                }
        }
        if (isShowDialog) {
            uiChange.dismissDialog.call()
        }

    }


    inner class UIChange {
        val showDialog by lazy { ViewModelEvent<String>() }    //显示加载框
        val dismissDialog by lazy { ViewModelEvent<Void>() }     //关闭加载框
        val msgEvent by lazy { ViewModelEvent<Message>() }    //发送消息
        val statueShowLoading by lazy { ViewModelEvent<Void>() } // 显示加载布局
        val statueSuccess by lazy { ViewModelEvent<Void>() }    //加载完成
        val statueError by lazy { ViewModelEvent<Void>() }     //加载错误，初次加载数据失败显示,后续如有adapter显示加载失败
    }




}