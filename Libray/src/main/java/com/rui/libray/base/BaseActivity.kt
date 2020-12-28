package com.rui.libray.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rui.libray.R
import com.rui.libray.data.net.ResCode
import com.rui.libray.databinding.ActivityBaseBinding
import com.rui.libray.util.AppManager
import com.rui.libray.widget.loadsir.EmptyCallback
import com.rui.libray.widget.loadsir.ErrorCallback
import com.rui.libray.widget.loadsir.LoadingCallback
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>() : AppCompatActivity() {

    protected lateinit var viewModel:  VM

    protected lateinit var bind : DB

    protected var baseBindView: ActivityBaseBinding? = null


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
        bind?.lifecycleOwner = this

        // content
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        bind?.root?.layoutParams = params
        baseBindView?.root?.findViewById<FrameLayout>(R.id.container)?.addView(bind?.root)
        setContentView(baseBindView?.root)



        val variableId = config.getVmVariableId()
        viewModel = config.getViewModel()?:return
        if (variableId!=ViewModelConfig.VM_NO_BIND){
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

        viewModel.uiChange.statueShowLoading.observe(this, {
            loadSirShowLoading()
        })

        viewModel.uiChange.statueSuccess.observe(this, {
            loadSirShowSuccess()
        })

        viewModel.uiChange.statueError.observe(this, {
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
    }

    private fun loadSirShowLoading(){
    }

    private fun loadSirShowError(){
    }

    open fun loadSirShowEmpty(){
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