package com.lihui.hilt.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lihui.hilt.R
import com.lihui.hilt.uitl.Utils
import kotlin.math.cos
import kotlin.math.sin

class Dashboard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val ANGLE = 120f
    private val RADIUS = Utils.dp2px(150f)
    private val LENGTH = Utils.dp2px(120f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dash = Path()
    private lateinit var pathEffect: PathDashPathEffect

    init {
        //只绘制边
        paint.style = Paint.Style.STROKE
        //设置画笔的宽度
        paint.strokeWidth = Utils.dp2px(2f)
        //绘制Dash
        dash.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(10f), Path.Direction.CW)
        //advance 间距   phase 第一个偏移量

        //计算弧形长度
        val arc = Path()
        arc.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + ANGLE / 2,
            360 - ANGLE,
        )
        val pathMeasure = PathMeasure(arc, false)
        pathEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - Utils.dp2px(2f)) / 20f,  //需要减去一个Dash 宽度  留一个刻度位置
            0f,
            PathDashPathEffect.Style.ROTATE
        )


    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //---------------------画Dash-----------------------------
        canvas?.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + ANGLE / 2,
            360 - ANGLE,
            //不需要连圆心的线
            false,
            paint
        )
        paint.pathEffect = pathEffect
        //---------------------画弧-----------------------------
        canvas?.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + ANGLE / 2,
            360 - ANGLE,
            //不需要连圆心的线
            false,
            paint
        )
        paint.pathEffect = null

        //画指针
        paint.color = resources.getColor(R.color.colorAccent)
        canvas?.drawLine(
            width / 2f,
            height / 2f,
            (cos(Math.toRadians(getAngleMark(5))) * LENGTH + width / 2f).toFloat(),
            (sin(Math.toRadians(getAngleMark(5))) * LENGTH + height / 2f).toFloat(),
            paint
        )

    }


    private fun getAngleMark(mark: Int): Double {
        //起始位置+（每个刻度的角度）* mark
        return (90f + ANGLE / 2f + ((360 - ANGLE ) / 20f) * mark).toDouble()
    }


}