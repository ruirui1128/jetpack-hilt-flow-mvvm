package com.lihui.hilt.ui.bindadapter

import android.text.Selection
import android.text.Spannable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lihui.hilt.ui.ext.createTextWatcher
import com.lihui.hilt.ui.vm.LoginVm


object TextBA {

    /**
     * 登录界面 显示隐藏监听
     */
    @JvmStatic
    @BindingAdapter(value = ["change", "owner"], requireAll = false)
    fun edTextChange(editText: EditText, change: LoginVm, owner: LifecycleOwner) {
        // 这里的监听可以 直接写在Activity里监听  这里仅仅是演示
        //  在复杂场景下 这样操作 可以细分化Act粒度
        //当点击密码显示隐藏 光标置于字符串后
        change.isClose.observe(owner, Observer {
            val close = change.isClose.value?:true
            editText.transformationMethod = if (close)  PasswordTransformationMethod.getInstance()
            else HideReturnsTransformationMethod.getInstance()
            editText.setSelection(editText.text.toString().length)
        })



    }

}