package com.mind.lib.util

import android.content.Context
import android.view.WindowManager


object DensityUtil {
    /**
     * 将px值转换为dip或dp值
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    fun px2dip(pxValue: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    fun dp2px(dipValue: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(pxValue: Float, context: Context): Int {
        val fontScale = context.resources.displayMetrics.density
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(spValue: Float, context: Context): Int {
        val fontScale = context.resources.displayMetrics.density
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 获取屏幕分辨率
     */
    fun getScreenDispaly(context: Context): IntArray? {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // 手机屏幕的宽度
        val width = windowManager.defaultDisplay.width
        // 手机屏幕的高度
        val height = windowManager.defaultDisplay.height
        return intArrayOf(width, height)
    }


    /**
     * 获取屏幕高度(px)
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取屏幕宽度(px)
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

}