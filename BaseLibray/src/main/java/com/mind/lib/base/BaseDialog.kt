package com.mind.lib.base

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup


class BaseDialog(context: Context, theme: Int, private val res: Int) : Dialog(context, theme) {


    private var dialog: Dialog? = null

    init {
        setContentView(res)
        dialog = this
        setDialogCancelable()
        setDialogTouchOutside()
        setWidth()
    }


    /**
     * 设置宽度
     */
    fun setWidth(ratio: Float = 0.85f): Dialog {
        val window = dialog?.window
        val outMetrics = DisplayMetrics()
        window?.windowManager?.defaultDisplay?.getRealMetrics(outMetrics)
        val widthPixel = outMetrics.widthPixels
        window?.setLayout(
            (widthPixel * ratio).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return this
    }


    /**
     * 设置是否可返回
     */
    fun setDialogCancelable(boolean: Boolean = true): Dialog {
        dialog?.setCancelable(boolean)
        return this
    }

    /**
     * 设置返回键是否有效
     */
    fun setDialogTouchOutside(boolean: Boolean = true): Dialog {
        dialog?.setCanceledOnTouchOutside(boolean)
        return this
    }


}