package com.lihui.hilt.uitl

import android.content.res.Resources
import android.util.TypedValue

/**
 *Created by Rui
 *on 2021/1/5
 */
object Utils {

    fun dp2px(dp: Float):Float{
        return  TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            Resources.getSystem().displayMetrics)
    }
}