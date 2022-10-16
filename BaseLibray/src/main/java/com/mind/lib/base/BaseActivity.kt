package com.mind.lib.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mind.lib.R
import com.mind.lib.databinding.ActivityBaseBinding


import com.mind.lib.util.AppManager
import com.mind.lib.util.toast
import java.lang.reflect.ParameterizedType
import javax.inject.Inject


/**
 * Created by rui
 *  on 2021/8/5
 */
/**
 * 获取DataBinding配置
 */

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>() : AppCompatActivity() {

    protected lateinit var viewModel: VM

    protected lateinit var bind: DB

    protected var baseBindView: ActivityBaseBinding? = null

    var reloadListener: () -> Unit = {}

    @Inject
    lateinit var appManager: AppManager

    /**
     * 获取DataBinding配置
     */
    protected abstract val viewModelConfig: ViewModelConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewTreeLifecycleOwner.set(window.decorView, this)
        appManager.addActivity(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 禁用横屏
        initViewDataBinding()
        registerUIChange()
        initialize()
    }


    protected open fun setStatusLayoutAndListener(): View? {
        return null
    }

    /**
     * 初始化
     */
    abstract fun initialize()

    private fun initViewDataBinding() {
        val config = viewModelConfig
        baseBindView = DataBindingUtil.inflate(layoutInflater, R.layout.activity_base, null, false)
        bind = DataBindingUtil.inflate(layoutInflater, config.getLayout(), null, false)
        baseBindView?.root?.findViewById<FrameLayout>(R.id.container)?.addView(bind.root)
        setContentView(baseBindView?.root)
        bind.lifecycleOwner = this
        val variableId = config.getVmVariableId()
        getViewModel()
        if (variableId != ViewModelConfig.VM_NO_BIND) {
            bind.setVariable(variableId, viewModel)
        }
        val bindingParams = config.getBindingParams()
        run {
            var i = 0
            val length = bindingParams.size()
            while (i < length) {
                bind.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
                i++
            }
        }
        lifecycle.addObserver(viewModel)


    }

    private fun getViewModel() {
        val type = javaClass.genericSuperclass
        viewModel = if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            ViewModelProvider(this).get(tClass) as VM
        } else {
            viewModels<BaseViewModel>().value as VM
        }

    }

    private fun registerUIChange() {
        viewModel.uiChange.showDialog.observe(this, Observer { showLoadingDialog() })
        viewModel.uiChange.dismissDialog.observe(this, Observer { dismissLoading() })
//        viewModel.uiChange.msgEvent.observe(this, Observer { handleEvent(it) })
        viewModel.uiChange.statueShowLoading.observe(this, Observer { statueShowLoading() })
        viewModel.uiChange.statueSuccess.observe(this, Observer { statueSuccess() })
        viewModel.uiChange.statueError.observe(this, Observer { statueError() })
    }

    open fun handleEvent(msg: String) {
        toast(msg)
    }

    // 加载中
    private var loadingView: View? = null

    // 加载失败
    private var errorView: View? = null

    // 空布局
    private val emptyView: View? = null


    private fun statueShowLoading() {
        if (errorView?.visibility != View.GONE) {
            errorView?.visibility = View.GONE
        }
        if (bind.root.visibility != View.GONE) {
            bind.root.visibility = View.GONE
        }
        if (loadingView == null) {
            val viewStub =
                findViewById<View>(R.id.vs_loading) as ViewStub
            loadingView = viewStub.inflate()
        } else {
            loadingView?.visibility = View.VISIBLE
        }

    }

    private fun statueError() {
        if (loadingView?.visibility != View.GONE) {
            loadingView?.visibility = View.GONE
        }

        if (errorView == null) {
            val viewStub =
                findViewById<View>(R.id.vs_error_refresh) as ViewStub
            errorView = viewStub.inflate()

        } else {
            errorView?.visibility = View.VISIBLE
        }
//        errorView?.findViewById<Button>(R.id.btnReLoad)?.onClick {
//            reLoad()
//        }

    }

    open fun reLoad() {}

    private fun statueSuccess() {
        if (loadingView?.visibility != View.GONE) {
            loadingView?.visibility = View.GONE
        }
        if (errorView?.visibility != View.GONE) {
            errorView?.visibility = View.GONE
        }
        if (bind.root.visibility != View.VISIBLE) {
            bind.root.visibility = View.VISIBLE
        }

    }


    private fun dismissLoading() {
        // showLoadingDialog?.dismiss()
    }

    private fun showLoadingDialog() {
        //  showLoadingDialog = BaseDialogUtil.showLoadingDialog(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        appManager.remove(this)
    }


}