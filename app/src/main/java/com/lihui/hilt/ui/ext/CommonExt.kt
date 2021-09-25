package com.lihui.hilt.ui.ext



import com.lihui.hilt.enums.SvgPlayEnums
import com.mind.data.data.model.SvgModel
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAParser.ParseCompletion
import com.opensource.svgaplayer.SVGAVideoEntity

/**
 *Created by Rui
 *on 2020/12/25
 */

fun SVGAImageView.start(model: SvgModel, complete:()->Unit = {}, error:()->Unit={}) {
    val parser = SVGAParser(this.context)
    //"2.svga" 内存过高
    val name = if (model.type==SvgPlayEnums.NORMAL.type){
        "1.svga"
    }else{
        "1.svga"
    }
    parser.decodeFromAssets(name, object : ParseCompletion {
        override fun onError() {
           //  Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show()
            error()
        }
        override fun onComplete(entity: SVGAVideoEntity) {
            val drawable = SVGADrawable(entity)
            setImageDrawable(drawable)
            startAnimation()
            complete()
        }
    })

}