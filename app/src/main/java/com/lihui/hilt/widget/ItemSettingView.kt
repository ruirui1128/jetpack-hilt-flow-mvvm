package com.lihui.hilt.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.lihui.hilt.R


class ItemSettingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var leftText = ""
    var rightText = ""
    var rightTextColor = 0
    var rightTextBold = true
    var rightTextShow = false
    var rightShowNext = true
    var leftIvShow = false
    var rightIvShow = false
    var bottomViewShow = true




    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ItemSetting)
        leftText = attr.getString(R.styleable.ItemSetting_leftText) ?: ""
        rightText = attr.getString(R.styleable.ItemSetting_rightTexts) ?: ""
        rightTextColor =
            attr.getColor(R.styleable.ItemSetting_rightTextColor, resources.getColor(R.color.color_333))
        rightTextBold = attr.getBoolean(R.styleable.ItemSetting_rightTextBold, true)
        rightTextShow = attr.getBoolean(R.styleable.ItemSetting_rightTextShow, false)
        rightShowNext = attr.getBoolean(R.styleable.ItemSetting_rightShowNext, true)
        leftIvShow = attr.getBoolean(R.styleable.ItemSetting_leftIvShow, false)
        rightIvShow = attr.getBoolean(R.styleable.ItemSetting_rightIvShow, false)
        bottomViewShow = attr.getBoolean(R.styleable.ItemSetting_bottomViewShow, true)
        View.inflate(context, R.layout.layout_item_setting, this)
        attr.recycle()
    }

    var ivLeft: ImageView? = null
    var tvLeft: TextView? = null
    var ivRight: ImageView? = null
    var tvRight: TextView? = null
    var ivNext: ImageView? = null
    var vBottom: View? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        ivLeft = findViewById(R.id.ivLeft)
        tvLeft = findViewById(R.id.tvLeft)
        ivRight = findViewById(R.id.ivRight)
        tvRight = findViewById(R.id.tvRight)
        ivNext = findViewById(R.id.ivNext)
        vBottom = findViewById(R.id.vBottom)
        initView()
    }

    private fun initView() {
        //左边图片是否显示
        ivLeft?.visibility = if (leftIvShow) View.VISIBLE else View.GONE

        //右边文字粗体
        if (rightTextBold){
            tvRight?.setTypeface(Typeface.SERIF, Typeface.BOLD)
        }else{
            tvRight?.setTypeface(Typeface.SERIF, Typeface.ITALIC)
        }

        //右边字体颜色
        tvRight?.setTextColor(rightTextColor)

        //左边文字
        tvLeft?.text = leftText

        //右边文字
        tvRight?.text = rightText


        if (rightIvShow){
            ivRight?.visibility = View.VISIBLE
            tvRight?.visibility = View.GONE
        }else {
            ivRight?.visibility = View.GONE
            tvRight?.visibility = View.VISIBLE
        }
        //右边文字是否显示
        tvRight?.visibility = if (rightTextShow) View.VISIBLE else View.GONE

        //next 是否显示
        ivNext?.visibility = if (rightShowNext) View.VISIBLE else View.GONE

        //下划线是否显示
        vBottom?.visibility = if (bottomViewShow) View.VISIBLE else View.GONE
    }

}