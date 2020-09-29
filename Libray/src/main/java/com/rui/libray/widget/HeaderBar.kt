package com.rui.libray.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rui.libray.R
import com.rui.libray.ext.onClick
import kotlinx.android.synthetic.main.layout_header_bar.view.*


class HeaderBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    //是否显示"返回"图标
    private var isShowBack = true

    //Title文字
    private var titleText: String? = null
    //右侧文字
    private var rightText: String? = null
    //背景颜色
    private var titleColor:Int?=null
    //右边图片
    private var  rightIv:Int?=null

    //左边图片
    private var  leftIv:Int?=null

    //是否显示右边图像
    private var isShowRighIv = false

    //右边颜色
    private var rightColor :Int ? = null


    init {
        //获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)
        titleColor = typedArray.getColor(R.styleable.HeaderBar_titleColor,getContext().resources.getColor(R.color.white))
        rightColor = typedArray.getColor(R.styleable.HeaderBar_rightColor,getContext().resources.getColor(R.color.black))
        rightIv = typedArray.getResourceId(R.styleable.HeaderBar_rightIv,0)
        leftIv = typedArray.getResourceId(R.styleable.HeaderBar_leftIv,R.drawable.i_black_back)
        isShowRighIv = typedArray.getBoolean(R.styleable.HeaderBar_isRightIvShow,false)
        initView()
        typedArray.recycle()
    }

    /*
      初始化视图
     */
    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)
        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE
        mRightIv.visibility = if (isShowRighIv) View.VISIBLE else View.GONE

        //背景颜色
        titleColor?.let {
            rlTitle.setBackgroundColor(it)
        }

        //标题不为空，设置值
        titleText?.let {
            mTitleTv.text = it
        }

        //右侧文字不为空，设置值
        rightText?.let {
            mRightTv.text = it
            mRightTv.visibility = View.VISIBLE
        }

        //右边文字大小
        mRightTv.setTextColor(rightColor?:R.color.black)

        //返回图标默认实现（关闭Activity）
        flBack.onClick {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
        //这是右边图案
        rightIv?.let{
            mRightIv.setBackgroundResource(it)
        }

        leftIv?.let {
            mLeftIv.setBackgroundResource(it)
        }


    }


    fun setTitleColor(color:Int){
        rlTitle.setBackgroundColor(color)
    }

    fun setTitle(title:String){
        mTitleTv.text = title
    }

    /*
      获取左侧视图
     */
    fun getLeftView(): ImageView {
        return mLeftIv
    }

    /*
      获取右侧视图
     */
    fun getRightView(): TextView {
        return mRightTv
    }

    /**
     * 获取右边iv
     */

    fun getRightIv():ImageView{
        return mRightIv
    }

    fun getFlBack():LinearLayout{
        return  flBack
    }
}
