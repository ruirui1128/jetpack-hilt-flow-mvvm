package com.rui.libray.widget.loadsir

import com.kingja.loadsir.callback.Callback
import com.rui.libray.R


class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}