package com.rui.libray.base


import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rui.libray.data.net.ResCode




abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM

    private var mActivity: AppCompatActivity? = null

    private var mBinding: ViewDataBinding? = null

    protected abstract val viewModelConfig: ViewModelConfig<VM>


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =viewModelConfig.getViewModel()?:return
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val config = viewModelConfig
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            config.getLayout(),
            container,
            false
        )
        binding.lifecycleOwner = this

        val variableId = config.getVmVariableId()
        viewModel = config.getViewModel()?:return null
        if (variableId!=ViewModelConfig.VM_NO_BIND){
            mBinding?.setVariable(variableId,viewModel)
        }

        val bindingParams: SparseArray<*> = config.getBindingParams()
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }
        mBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUIChange()
        init(savedInstanceState)
    }

    open fun dismissLoading() {
    }

    open fun showLoadingDialog() {
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

    }

    open fun handleEvent(msg: Message) {
        when (msg.code) {
            ResCode.NETWORK_ERROR.getCode() -> {
                Toast.makeText(activity,msg.msg,Toast.LENGTH_LONG).show()
            }
            ResCode.TOKEN_ERROR.getCode()->{
//                LiveEventBus.get(MsgBusEvent.TOKEN_OUT_EVENT).post("")
            }
            else ->  Toast.makeText(activity,msg.msg,Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
    }

    abstract fun init(savedInstanceState: Bundle?)



}