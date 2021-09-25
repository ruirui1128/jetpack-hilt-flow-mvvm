package com.mind.lib.base


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.mind.lib.R
import com.mind.lib.ext.onClick
import com.mind.lib.util.BaseDialogUtil

import java.lang.reflect.ParameterizedType
import javax.inject.Inject


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: VM

    private var mActivity: Activity? = null

    protected lateinit var bind: DB

    protected abstract val viewModelConfig: ViewModelConfig

    private var showLoadingDialog: MaterialDialog? = null


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
        mActivity ?: return null
        val config = viewModelConfig
        val inflate = inflater.inflate(R.layout.fragment_base, null)
        bind = DataBindingUtil.inflate(layoutInflater, config.getLayout(), null, false)
        inflate.findViewById<FrameLayout>(R.id.container)?.addView(bind.root)
        bind.lifecycleOwner = this
        val variableId = config.getVmVariableId()
        getViewModel()
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
        return inflate
    }


    private fun getViewModel() {
        val type = javaClass.genericSuperclass
        viewModel = if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            ViewModelProvider(this).get(tClass) as VM
        } else {
            activity?.viewModels<BaseViewModel>()?.value as VM
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUIChange()
        init(savedInstanceState)
    }


    protected open fun setStatusLayoutAndListener(): View? {
        return null
    }


    private fun registerUIChange() {
        viewModel.uiChange.showDialog.observe(this, Observer { showLoadingDialog() })
        viewModel.uiChange.dismissDialog.observe(this, Observer { dismissLoading() })
        viewModel.uiChange.statueShowLoading.observe(this, Observer { statueShowLoading() })
        viewModel.uiChange.statueSuccess.observe(this, Observer { statueSuccess() })
        viewModel.uiChange.statueError.observe(this, Observer { statueError() })

    }

    open fun handleEvent(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    // 加载中
    private var loadingView: View? = null

    // 加载失败
    private var errorView: View? = null

    // 空布局
    private val emptyView: View? = null


    private fun statueShowLoading() {
        if (loadingView == null) {
            val viewStub = view?.findViewById<ViewStub>(R.id.vs_loading)
            loadingView = viewStub?.inflate()
        }
        loadingView?.visibility = View.VISIBLE

        if (errorView?.visibility != View.GONE) {
            errorView?.visibility = View.GONE
        }
        if (bind.root.visibility != View.GONE) {
            bind.root.visibility = View.GONE
        }

    }

    protected fun statueError() {
        if (loadingView?.visibility != View.GONE) {
            loadingView?.visibility = View.GONE
        }
        if (bind.root.visibility != View.GONE) {
            bind.root.visibility = View.GONE
        }

        if (errorView == null) {
            val viewStub =
                view?.findViewById<ViewStub>(R.id.vs_error_refresh)
            errorView = viewStub?.inflate()
        } else {
            errorView?.visibility = View.VISIBLE
        }

        errorView?.findViewById<Button>(R.id.btnReLoad)?.onClick {
            reLoad()
        }

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
        showLoadingDialog?.dismiss()
    }

    protected fun showLoadingDialog() {
        showLoadingDialog = BaseDialogUtil.showLoadingDialog(activity, this)
    }

    protected open fun <T : View?> getView(id: Int): T {
        return view?.findViewById<View>(id) as T
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
    }

    abstract fun init(savedInstanceState: Bundle?)

}