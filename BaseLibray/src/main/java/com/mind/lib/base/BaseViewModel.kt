package com.mind.lib.base


import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mind.lib.data.model.Res
import com.mind.lib.data.net.ResCode

import com.mind.lib.util.Util
import com.mind.lib.util.toast
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by rui
 *  on 2021/8/2
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val uiChange: UIChange by lazy { UIChange() }

    //当组件结束时，会取消协程内的任务
    override fun onCleared() {
        viewModelScope.cancel()
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param request 请求体
     * @param resp 成功回调 响应
     * @param error 失败回调
     * @param loadMoreError 加载更多失败
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     * @param isLoadMore 是否加载更多
     * @param isStatueLayout 是否有加载替换的布局
     */
    fun <T> launchFlow(
        request: suspend CoroutineScope.() -> Res<T>,
        resp: (T?) -> Unit,
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
            flow { emit(request()) }    //网络请求
                .flowOn(Dispatchers.IO)  //指定网络请求的线程
                .catch { t: Throwable ->   //异常捕获处理
                    catch(error, t, isLoadMore, loadMoreError, isStatueLayout)
                    end(isShowDialog, complete)
                }
                //数据请求返回处理  emit(block()) 返回的数据
                .collect {
                    handlerCode(it, isStatueLayout, resp, error)
                    end(isShowDialog, complete)
                }
        }

    }


    fun <T> launchData(
        request: suspend CoroutineScope.() -> Res<T>,
        resp: (T?) -> Unit,
        error: (String) -> Unit = {},
        loadMoreError: () -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        isStatueLayout: Boolean = false,
        isLoadMore: Boolean = false
    ) {

        if (prepare(isShowDialog, isStatueLayout)) {
            if (isLoadMore) {
                loadMoreError()
            }
            end(isShowDialog, complete)
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = request()
                handlerCode(result, isStatueLayout, resp, error)
                end(isShowDialog, complete)
            } catch (t: Throwable) {
                catch(error, t, isLoadMore, loadMoreError, isStatueLayout)
                end(isShowDialog, complete)
            }
        }
    }


    fun http() = this


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

        if (!Util.isNetworkConnected(BaseApp.getApp())) {
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
        resp: (T?) -> Unit,
        error: (String) -> Unit
    ) {
        when (it.code) {     //网络响应解析
            ResCode.OK.getCode() -> {
                if (isStatueLayout) {
                    uiChange.statueSuccess.call()
                } //数据加载完成 界面切换}
                resp(it.data)  //数据加载完成交由业务层处理
            }
            else -> {
                if (isStatueLayout) {
                    uiChange.statueError.call()
                }
                error(it.msg ?: "")
                uiChange.msgEvent.postValue(it.msg)
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


    // -----------------------------------------------------------------------------------------------

    fun <T> loadHttp(
        request: suspend CoroutineScope.() -> Res<T>,  // 请求
        resp: (T?) -> Unit,                            // 相应
        err: (String) -> Unit = { },                   // 错误处理
        end: () -> Unit = {},                          // 最后执行方法
        isShowToast: Boolean = true,                   // 是否toast
        isShowDialog: Boolean = true,                  // 是否显示加载框
    ) {
        viewModelScope.launch {
            try {
                showDialog(isShowDialog)
                val data = request()                   // 请求+响应数据
                if (data.code == ResCode.OK.getCode()) {    //业务响应成功
                    resp(data.data)                   // 响应回调
                } else {
                    showToast(isShowDialog, data.msg)
                    err(data.msg)                       // 业务失败处理
                }
            } catch (e: Exception) {
                err(e.message ?: "")  //可根据具体异常显示具体错误提示   异常处理
                showToast(isShowToast, e.message ?: "")
            } finally {
                end()
                dismissDialog(isShowDialog)
            }
        }

    }


    /**
     * toast
     */
    private fun showToast(show: Boolean, msg: String) {
        if (show) {
            toast(msg)
        }
    }

    /**
     * 显示对话框
     */
    private fun showDialog(show: Boolean) {
        if (show) {
            uiChange.showDialog.call()
        }
    }

    /**
     * 关闭对话框
     */
    private fun dismissDialog(show: Boolean) {
        if (show) {
            uiChange.dismissDialog.call()
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