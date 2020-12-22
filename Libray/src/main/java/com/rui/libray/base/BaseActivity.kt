package com.rui.libray.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kingja.loadsir.callback.Callback.OnReloadListener
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rui.libray.R

import com.rui.libray.data.net.ResCode
import com.rui.libray.util.AppManager
import com.rui.libray.util.StatusBarUtils
import com.rui.libray.widget.loadsir.EmptyCallback
import com.rui.libray.widget.loadsir.ErrorCallback
import com.rui.libray.widget.loadsir.LoadingCallback

import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel>() : AppCompatActivity() {

    protected lateinit var viewModel:  VM

    private var mBinding: ViewDataBinding? = null

    //界面状态管理者
    private  var loadService: LoadService<Any> ? = null

    var reloadListener:()->Unit = {}

    @Inject lateinit var appManager: AppManager


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
        initLoadSir()
        initBar()
        init()
    }

    private fun initLoadSir() {
        val view = setStatusLayoutAndListener()
        if (view!=null){
            loadService = LoadSir.getDefault().register(view) {
                loadSirShowLoading()
                reloadListener()
            }
        }
    }

    /**
     * 子类需要加载页面，需要复写此方法，并重新赋值reloadListener
     * override fun setStatusLayout(): View? {
        reloadListener = { refresh(true) }
        return swipeRefreshLayout
        }
     *
     */
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

        mBinding = DataBindingUtil.setContentView(this, config.getLayout())
        mBinding?.lifecycleOwner = this

        val variableId = config.getVmVariableId()
        viewModel = config.getViewModel()?:return
        if (variableId!=ViewModelConfig.VM_NO_BIND){
            mBinding?.setVariable(variableId, viewModel)
        }
        val bindingParams = config.getBindingParams()
        run {
            var i = 0
            val length = bindingParams.size()
            while (i < length) {
                mBinding?.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
                i++
            }
        }
        if (viewModel!=null){
            lifecycle.addObserver(viewModel)
        }

    }

    private fun registerUIChange() {
        if (viewModel==null)return
        viewModel.uiChange.showDialog.observe(this, Observer {
            showLoadingDialog()
        })
        viewModel.uiChange.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.uiChange.msgEvent.observe(this, Observer {
            handleEvent(it)
        })

        viewModel.uiChange.statueShowLoading.observe(this,{
            loadSirShowLoading()
        })

        viewModel.uiChange.statueSuccess.observe(this,{
            loadSirShowSuccess()
        })

        viewModel.uiChange.statueError.observe(this,{
            loadSirShowError()
        })

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


    private fun loadSirShowSuccess(){
        loadService?.showSuccess()
    }

    private fun loadSirShowLoading(){
        loadService?.showCallback(LoadingCallback::class.java)
    }

    private fun loadSirShowError(){
        loadService?.showCallback(ErrorCallback::class.java)
    }

    open fun loadSirShowEmpty(){
        loadService?.showCallback(EmptyCallback::class.java)
    }

    private fun dismissLoading() {
    }

    private fun showLoadingDialog() {

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