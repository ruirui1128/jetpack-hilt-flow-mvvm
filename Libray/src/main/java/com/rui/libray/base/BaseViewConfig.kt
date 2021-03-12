package com.rui.libray.base

import android.util.Log
import java.lang.reflect.ParameterizedType

open class BaseViewConfig<VM : BaseViewModel> {


    init {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            Log.e("BaseViewConfig", "------------------$tClass")
        }
    }
}