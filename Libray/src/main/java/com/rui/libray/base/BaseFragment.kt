package com.rui.libray.base


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rui.libray.R
import com.rui.libray.data.net.ResCode
import com.rui.libray.widget.loadsir.EmptyCallback
import com.rui.libray.widget.loadsir.ErrorCallback
import com.rui.libray.widget.loadsir.LoadingCallback


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: VM

    private var mActivity: AppCompatActivity? = null

    protected  lateinit var bind: DB

    protected abstract val viewModelConfig: ViewModelConfig<VM>



    var reloadListener: () -> Unit = {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val config = viewModelConfig
        val inflate = inflater.inflate(R.layout.fragment_base, null)
        bind = DataBindingUtil.inflate(layoutInflater, config.getLayout(), null, false)

        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        bind.root.layoutParams = params
        val mContainer = inflate.findViewById<FrameLayout>(R.id.container)
        mContainer.addView(bind.root)

        bind.lifecycleOwner = this
        val variableId = config.getVmVariableId()
        viewModel = config.getViewModel() ?: return null
        if (variableId != ViewModelConfig.VM_NO_BIND) {
            bind.setVariable(variableId, viewModel)
        }

        val bindingParams: SparseArray<*> = config.getBindingParams()
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            bind.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUIChange()
        init(savedInstanceState)
    }


    protected open fun setStatusLayoutAndListener(): View? {
        return null
    }

    open fun dismissLoading() {
    }

    open fun showLoadingDialog() {
    }

    private fun loadSirShowSuccess() {

    }

    private fun loadSirShowLoading() {

    }

    private fun loadSirShowError() {

    }

    open fun loadSirShowEmpty() {

    }


    private fun registerUIChange() {
        viewModel.uiChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoadingDialog()
        })
        viewModel.uiChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })

        viewModel.uiChange.msgEvent.observe(viewLifecycleOwner, Observer {
            handleEvent(it)
        })


        viewModel.uiChange.statueShowLoading.observe(this, Observer {
            loadSirShowLoading()
        })
        viewModel.uiChange.statueSuccess.observe(this, Observer {
            loadSirShowSuccess()
        })

        viewModel.uiChange.statueError.observe(this, Observer {
            loadSirShowError()
        })

    }

    open fun handleEvent(msg: Message) {
        when (msg.code) {
            ResCode.NETWORK_ERROR.getCode() -> {
                Toast.makeText(activity, msg.msg, Toast.LENGTH_LONG).show()
            }
            ResCode.TOKEN_ERROR.getCode() -> {
//                LiveEventBus.get(MsgBusEvent.TOKEN_OUT_EVENT).post("")
            }
            else                            -> Toast.makeText(activity, msg.msg, Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
    }

    abstract fun init(savedInstanceState: Bundle?)

}