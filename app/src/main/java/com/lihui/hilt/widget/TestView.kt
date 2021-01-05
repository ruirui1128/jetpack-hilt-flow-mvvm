package com.lihui.hilt.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.lihui.hilt.R
import com.lihui.hilt.uitl.Utils

/**
 *Created by Rui
 *on 2021/1/5
 */
class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var paint:Paint = Paint()
    var path  = Path()


    init {
        paint.color = resources.getColor(R.color.colorAccent)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.reset()
        path.addRect(width/2f-150f,height/2f-300f,
        width/2f+150f,height/2f,Path.Direction.CCW)//逆时针
        path.addCircle((width/2f),height/2f, 150f,Path.Direction.CCW)

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.fillType = Path.FillType.EVEN_ODD  //相交镂空
        canvas?.drawPath(path,paint)
    }
}