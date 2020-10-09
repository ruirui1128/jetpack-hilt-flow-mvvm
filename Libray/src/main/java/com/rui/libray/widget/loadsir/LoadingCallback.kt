package com.rui.libray.widget.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.rui.libray.R


class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}