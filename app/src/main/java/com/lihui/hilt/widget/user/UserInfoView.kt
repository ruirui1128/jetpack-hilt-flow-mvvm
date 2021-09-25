package com.lihui.hilt.widget.user

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.lihui.hilt.R

import com.lihui.hilt.databinding.ViewUserInfoBinding
import com.mind.data.data.model.UserModel


class UserInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var bind: ViewUserInfoBinding? = null

    init {
        val inflate = View.inflate(context, R.layout.view_user_info, null)
        bind = DataBindingUtil.bind(inflate)
        addView(bind?.root)
        bind?.lifecycleOwner = findViewTreeLifecycleOwner()
    }


    companion object {
        @JvmStatic
        @BindingAdapter(value = ["userModelData"])
        fun bindUserInfo(view: UserInfoView, userModelData: UserModel?) {
            view.bind?.userModel = userModelData
        }
    }

}