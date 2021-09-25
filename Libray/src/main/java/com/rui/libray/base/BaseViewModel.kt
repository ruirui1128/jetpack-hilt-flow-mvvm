package com.rui.libray.base


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rui.libray.data.bean.Res
import com.rui.libray.data.net.ResCode
import com.rui.libray.util.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception


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
     * @param ok 成功回调
     * @param error 失败回调
     * @param loadMoreError 加载更多失败
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     * @param isLoadMore 是否加载更多
     * @param isStatueLayout 是否有加载替换的布局
     */
    fun <T> launchFlow(
        block: suspend CoroutineScope.() -> Res<T>,
        ok: (T?) -> Unit,
        error: (String) -> Unit = {},
        loadMoreError: () -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        isStatueLayout: Boolean = false,
        isLoadMore: Boolean = false
    ) {
        //请求之前操作
        if (prepare(isShowDialog, isStatueLayout)) {
            if (isLoadMore) {
                loadMoreError()
            }
            end(isShowDialog, complete)
            return
        }
        viewModelScope.launch {
            flow { emit(block()) }    //网络请求
                .flowOn(Dispatchers.IO)  //指定网络请求的线程
                .catch { t: Throwable ->   //异常捕获处理
                    catch(error, t, isLoadMore, loadMoreError, isStatueLayout)
                    end(isShowDialog, complete)
                }
                //数据请求返回处理  emit(block()) 返回的数据
                .collect {
                    handlerCode(it, isStatueLayout, ok, error)
                    end(isShowDialog, complete)
                }
        }

    }

    /**
     * 请求之前操作
     */
    private fun prepare(
        isShowDialog: Boolean,
        isStatueLayout: Boolean
    ): Boolean {
        if (isShowDialog) {
            uiChange.showDialog.call()
        }
        //是否显示加载界面  准备
        if (isStatueLayout) {
            uiChange.statueShowLoading.call()
        }
        //网络监测
        if (networkHelper?.isNetworkConnected() == false) {
            uiChange.msgEvent.postValue(ResCode.NETWORK_ERROR.getMessage())
            //关闭页面loading
            if (isStatueLayout) {
                uiChange.statueError.call()
            }
            return true
        }
        return false
    }

    /**
     * 异常处理
     */
    private fun catch(
        error: (String) -> Unit,
        t: Throwable,
        isLoadMore: Boolean,
        loadMoreError: () -> Unit,
        isStatueLayout: Boolean
    ) {
        error(t.message ?: "")   //有基类自行处理,业务层也可以自行处理
        if (isLoadMore) {   //isLoadMore 为true 表示数据已经加载第二页数据 则不需要显示statueError
            loadMoreError()  //交由业务层自行处理
        } else {
            if (isStatueLayout) {
                uiChange.statueError.call()   //显示数据加载失败界面
            }
            uiChange.msgEvent.postValue(t.message ?: "")
        }
    }

    /**
     * 最终执行
     */
    private fun end(isShowDialog: Boolean, complete: () -> Unit) {
        if (isShowDialog) {
            uiChange.dismissDialog.call()
        }
        complete()
    }

    /**
     * 响应处理
     */
    private fun <T> handlerCode(
        it: Res<T>,
        isStatueLayout: Boolean,
        success: (T?) -> Unit,
        error: (String) -> Unit
    ) {
        when (it.code) {     //网络响应解析
            ResCode.OK.getCode() -> {
                if (isStatueLayout) {
                    uiChange.statueSuccess.call()
                } //数据加载完成 界面切换}
                success(it.data)  //数据加载完成交由业务层处理
            }
            else -> {
                error(it.message)
                uiChange.msgEvent.postValue(it.message)
            }

        }
    }


    /**
     * 数据库插入
     */
    fun launchRoom(
        block: suspend CoroutineScope.() -> Unit,
        //   error: () -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                //发生异常 也可以自行处理
                uiChange.msgEvent.postValue(e.message)
                // error()
            }

        }
    }


    inner class UIChange {
        val showDialog by lazy { ViewModelEvent<String>() }    //显示加载框
        val dismissDialog by lazy { ViewModelEvent<Void>() }     //关闭加载框
        val msgEvent by lazy { ViewModelEvent<String>() }    //发送消息
        val statueShowLoading by lazy { ViewModelEvent<Void>() } // 显示加载布局
        val statueSuccess by lazy { ViewModelEvent<Void>() }    //加载完成
        val statueError by lazy { ViewModelEvent<Void>() }     //加载错误，初次加载数据失败显示,后续如有adapter显示加载失败
    }


}