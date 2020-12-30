package com.rui.libray.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.afollestad.materialdialogs.MaterialDialog
import com.rui.libray.R
import com.rui.libray.data.net.ResCode
import com.rui.libray.databinding.ActivityBaseBinding
import com.rui.libray.util.AppManager
import com.rui.libray.util.BaseDialogUtil
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>() : AppCompatActivity() {

    protected lateinit var viewModel: VM

    protected lateinit var bind: DB

    protected var baseBindView: ActivityBaseBinding? = null

    private var showLoadingDialog: MaterialDialog? = null

    var reloadListener: () -> Unit = {}

    @Inject
    lateinit var appManager: AppManager


    /**
     * 获取DataBinding配置
     */
    protected abstract val viewModelConfig: ViewModelConfig<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appManager.addActivity(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 禁用横屏
        initViewDataBinding()
        registerUIChange()
        initBar()
        init()
    }


    protected open fun setStatusLayoutAndListener(): View? {
        return null
    }

    private fun initBar() {
//        StatusBarUtils.with(this)
//            .setColor(resources.getColor(R.color.white))
//            .init()
    }

    abstract fun init()

    private fun initViewDataBinding() {
        val config = viewModelConfig
        baseBindView = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.activity_base,
            null,
            false
        )
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            config.getLayout(),
            null,
            false
        )
        bind.lifecycleOwner = this

        // content
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        bind.root.layoutParams = params
        baseBindView?.root?.findViewById<FrameLayout>(R.id.container)?.addView(bind.root)
        setContentView(baseBindView?.root)


        val variableId = config.getVmVariableId()
        viewModel = config.getViewModel() ?: return
        if (variableId != ViewModelConfig.VM_NO_BIND) {
            bind?.setVariable(variableId, viewModel)
        }
        val bindingParams = config.getBindingParams()
        run {
            var i = 0
            val length = bindingParams.size()
            while (i < length) {
                bind?.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
                i++
            }
        }
        if (viewModel != null) {
            lifecycle.addObserver(viewModel)
        }

    }

    private fun registerUIChange() {
        if (viewModel == null) return
        viewModel.uiChange.showDialog.observe(this, Observer { showLoadingDialog() })
        viewModel.uiChange.dismissDialog.observe(this, Observer { dismissLoading()})
        viewModel.uiChange.msgEvent.observe(this, Observer {  handleEvent(it)})
        viewModel.uiChange.statueShowLoading.observe(this, Observer { statueShowLoading()})
        viewModel.uiChange.statueSuccess.observe(this, Observer { statueSuccess()})
        viewModel.uiChange.statueError.observe(this, Observer {statueError() })

    }


    open fun handleEvent(msg: Message) {
        when (msg.code) {
            ResCode.NETWORK_ERROR.getCode() -> {
                Toast.makeText(this, msg.msg, Toast.LENGTH_LONG).show()
            }
            ResCode.TOKEN_ERROR.getCode() -> {
            }
            else -> Toast.makeText(this, msg.msg, Toast.LENGTH_LONG).show()
        }

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
        if (bind.root.visibility != View.GONE){
            bind.root.visibility = View.GONE
        }
        if (loadingView==null){
            val viewStub =
                findViewById<View>(R.id.vs_loading) as ViewStub
            loadingView = viewStub.inflate()
        }else{
            loadingView?.visibility = View.VISIBLE
        }

    }

    private fun statueError() {
        if (loadingView?.visibility != View.GONE) {
            loadingView?.visibility = View.GONE
        }

        if (errorView==null){
            val viewStub =
                findViewById<View>(R.id.vs_error_refresh) as ViewStub
            errorView = viewStub.inflate()
        }else{
            errorView?.visibility = View.VISIBLE
        }

    }

    private fun statueSuccess() {
        if (loadingView?.visibility != View.GONE) {
            loadingView?.visibility = View.GONE
        }
        if (errorView?.visibility != View.GONE) {
            errorView?.visibility = View.GONE
        }
        if (bind.root.visibility != View.VISIBLE){
            bind.root.visibility = View.VISIBLE
        }

    }


    private fun dismissLoading() {
        showLoadingDialog?.dismiss()
    }

    protected fun showLoadingDialog() {
        showLoadingDialog = BaseDialogUtil.showLoadingDialog(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        appManager.remove(this)
    }


    open fun goTo(aClass: Class<out AppCompatActivity?>?) {
        startActivity(Intent(this, aClass))
    }

    open fun goTo(
        aClass: Class<out AppCompatActivity?>?,
        bundle: Bundle?
    ) {
        val intent = Intent(this, aClass)
        if (null != bundle) intent.putExtras(bundle)
        startActivity(intent)
    }

    open fun goTo(
        aClass: Class<out AppCompatActivity?>?,
        bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent(this, aClass)
        if (null != bundle) intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    open fun goToAndFinish(aClass: Class<out AppCompatActivity?>?) {
        goTo(aClass)
        finish()
    }

    open fun goToAndFinish(
        aClass: Class<out AppCompatActivity?>?,
        bundle: Bundle?
    ) {
        goTo(aClass, bundle)
        finish()
    }


}