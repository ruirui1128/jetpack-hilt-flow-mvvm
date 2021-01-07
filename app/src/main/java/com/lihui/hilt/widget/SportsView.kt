package com.lihui.hilt.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lihui.hilt.uitl.Utils

/**
 *Created by Rui
 *on 2021/1/6
 */
class SportsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val RADIUS = Utils.dp2px(150f)
    private val RING_WIDTH = Utils.dp2px(15f)
    private val CIRCLR_COLOR = Color.parseColor("#90A4AE")
    private val HIGH_LIGHT_COLOR =  Color.parseColor("#FF4081")

    //绘制文字居中
    private val fontMetrics  = Paint.FontMetrics()

    private val rect = Rect()

    init {
        paint.textSize = Utils.dp2px(50f)
        paint.textAlign = Paint.Align.CENTER
        paint.getFontMetrics(fontMetrics)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLR_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas?.drawCircle(width/2f,height/2f,RADIUS,paint)

        //绘制进度条
        paint.color = HIGH_LIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawArc(
            width/2f-RADIUS,
            height/2f - RADIUS,
            width/2f+RADIUS,
            height/2f +RADIUS,
            -90f,
            225f,
            false,
            paint
        )


        //绘制文字  计算偏移 文字居中
        paint.style = Paint.Style.FILL
       // canvas?.drawText("叹希奇",width/2f,height/2f,paint)
//        paint.getTextBounds("叹希奇",0,"叹希奇".length,rect)
//        val offset = (rect.top+rect.bottom)/2f
//        canvas?.drawText("叹希奇",width/2f,height/2f-offset,paint)
        val offset = (fontMetrics.ascent+fontMetrics.descent)/2f

        canvas?.drawText("叹希奇",width/2f,height/2f-offset,paint)




    }
}