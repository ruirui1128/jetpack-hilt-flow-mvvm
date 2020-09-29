package com.rui.libray.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.rui.libray.R

import com.rui.libray.data.net.ResCode
import com.rui.libray.util.AppManager
import com.rui.libray.util.StatusBarUtils

import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel>() : AppCompatActivity() {

    protected lateinit var viewModel:  VM

    private var mBinding: ViewDataBinding? = null

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

    private fun initBar() {
        StatusBarUtils.with(this)
            .setColor(resources.getColor(R.color.white))
            .init()
    }

    abstract fun init()

    private fun initViewDataBinding() {
        val config = viewModelConfig

        mBinding = DataBindingUtil.setContentView(this, config.getLayout())
        mBinding?.lifecycleOwner = this

        val variableId = config.getVmVariableId()
        viewModel = config.getViewModel()?:return
        if (variableId!=ViewModelConfig.VM_NO_BIND){
            mBinding?.setVariable(variableId,viewModel)
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

    private fun dismissLoading() {
    }

    private fun showLoadingDialog() {

    }



    override fun onDestroy() {
        super.onDestroy()
        appManager.remove(this)
    }







}