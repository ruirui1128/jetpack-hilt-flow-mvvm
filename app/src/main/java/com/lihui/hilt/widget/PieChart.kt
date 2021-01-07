package com.lihui.hilt.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.lihui.hilt.uitl.Utils
import kotlin.math.cos
import kotlin.math.sin

/**
 *Created by Rui
 *on 2021/1/6
 */
class PieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val RADIUS = Utils.dp2px(150f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF = RectF()
    private val TRAN_INDEX = 2
    private val LENGTH = Utils.dp2px(20f)

    private val angles = mutableListOf(60f, 100f, 120f, 80f)
    private val colors = mutableListOf(
        Color.parseColor("#0000ff"),
        Color.parseColor("#ff569f"),
        Color.parseColor("#03DAC5"),
        Color.parseColor("#ff0000")
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //--------------------------画饼图---------------------------------
        var currentAngle = 0f
        for (index in 0 until angles.size) {
            paint.color = colors[index]

            //画布将当前的状态保存
            canvas?.save()
            //移动指定扇形
            if (TRAN_INDEX==index){
                canvas?.translate(
                    (cos(Math.toRadians((currentAngle+angles[index]/2f).toDouble())).toFloat()*LENGTH),
                    (sin(Math.toRadians((currentAngle+angles[index]/2f).toDouble())).toFloat()*LENGTH),
                )
            }

            canvas?.drawArc(
                rectF, currentAngle,
                angles[index],
                true,
                paint
            )
            //画布取出原来所保存的状态
            canvas?.restore()
            currentAngle += angles[index]
        }


    }
}