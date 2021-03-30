package com.lihui.hilt.widget.user

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import com.lihui.hilt.R
import com.lihui.hilt.data.model.UserModel
import com.lihui.hilt.databinding.ViewUserInfoBinding
import com.lihui.hilt.enums.StarLevelEnum
import com.lihui.hilt.ui.vm.UserVm
import com.lihui.hilt.uitl.GlideUtil
import com.lihui.hilt.uitl.ToastUtil
import com.rui.libray.ext.onClick


class UserInfoView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var ivHeader: ImageView? = null
    private var ivLevel: ImageView? = null
    private var tvName: TextView? = null
    private var tvAge: TextView? = null

    init {
        View.inflate(context, R.layout.view_user_info_2, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ivHeader = findViewById(R.id.ivHeader)
        ivLevel = findViewById(R.id.ivLevel)
        tvName = findViewById(R.id.tvName)
        tvAge = findViewById(R.id.tvAge)
        ivHeader?.onClick { ToastUtil.toast("别摸我...") }
    }

    private fun setDataView(model: UserModel) {
        GlideUtil.loadHeader(ivHeader, model.avatar)
        ivLevel?.setBackgroundResource(StarLevelEnum.match(model.level))
        tvName?.text = model.name
        tvAge?.text = model.age + "岁"
    }


    //设置ViewModel 监听
    private fun bindVm(viewModel: UserVm) {
        val lifecycleOwner = findViewTreeLifecycleOwner()
        lifecycleOwner ?: return
        //等级改变
        viewModel.level.observe(lifecycleOwner, Observer {
            ivLevel?.setBackgroundResource(StarLevelEnum.match(it))
        })

        // 头像改变
        viewModel.changeResult.observe(lifecycleOwner, Observer {
            GlideUtil.loadHeader(ivHeader, it)
        })
    }

    companion object {
        //数据绑定
        @JvmStatic
        @BindingAdapter(value = ["userModelData2"])
        fun bindUserInfo(view: UserInfoView2, userModelData2: UserModel?) {
            userModelData2 ?: return
            view.setDataView(userModelData2)
        }

        //传入viewModel
        @JvmStatic
        @BindingAdapter(value = ["userInfoVm"])
        fun bindUserInfoVm(view: UserInfoView2, userInfoVm: UserVm?) {
            userInfoVm ?: return
            view.bindVm(userInfoVm)
            view.ivHeader?.onClick { userInfoVm.changeHeader() }
            view.ivLevel?.onClick { userInfoVm.level.value = (0..4).random() }
        }


    }

}