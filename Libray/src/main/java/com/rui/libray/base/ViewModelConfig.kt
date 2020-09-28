package com.rui.libray.base

import android.util.SparseArray


class ViewModelConfig<VM : BaseViewModel>(private var layout: Int) {

    companion object{
        //viewModel 与 layout 没有绑定的默认值
       const val VM_NO_BIND = -9999
    }

    private  val bindingParams: SparseArray<Any> = SparseArray()

    private  var viewModel: VM ? = null

    //viewModel 绑定的 variableId
    private  var vmVariableId: Int  = VM_NO_BIND

    fun getLayout(): Int {
        return layout
    }

    fun getViewModel():VM?{
        return viewModel
    }



    fun getBindingParams(): SparseArray<Any> {
        return bindingParams
    }

    fun getVmVariableId():Int{
        return vmVariableId
    }


    /**
     * 与layout绑定
     */
    fun addBindingParam(variableId: Int, obj: Any): ViewModelConfig<VM> {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, obj)
        }
        return this
    }

    /**
     * 将viewModel 传给基类
     */
    fun addViewModel(vm: BaseViewModel): ViewModelConfig<VM>{
        viewModel = vm as VM
        return this
    }

    /**
     * 将viewModel 传给基类并与layout 绑定
     */
    fun addViewModel(vm: BaseViewModel,vmVariableId:Int):ViewModelConfig<VM>{
        this.viewModel = vm as VM
        this.vmVariableId = vmVariableId
        return this
    }

}