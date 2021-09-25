package com.lihui.hilt.uitl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.lihui.hilt.R
import com.mind.lib.util.DensityUtil


object GlideUtil {

    fun load(imageView: ImageView?, url: String?, isCircle: Boolean = false, corners: Float = 1f) {
        imageView ?: return
        val builder: RequestBuilder<Drawable> = Glide.with(imageView.context).load(url)
        val options = if (isCircle) {
            RequestOptions.circleCropTransform()
        } else {
            val roundedCorners =
                RoundedCorners(DensityUtil.dp2px(corners, imageView.context))
            RequestOptions.bitmapTransform(roundedCorners)
        }

        val layoutParams = imageView.layoutParams
        val width = layoutParams?.width ?: 0
        val height = layoutParams?.height ?: 0
        if (width > 0 && height > 0) {
            builder.override(width, height)
        }
        builder.apply(options).placeholder(R.color.white).error(R.color.color_ededed)
            .into(imageView)
    }

    fun loadRes(imageView: ImageView?, imageUrl: Int = R.color.color_999) {
        imageView?.setBackgroundResource(imageUrl)
    }

    fun downloadBitmap(context: Context?, url: String?): Bitmap? {
        try {
            return Glide.with(context!!)
                .asBitmap()
                .load(url)
                .submit().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun loadHeader(imageView: ImageView?, url: String?) {
        imageView ?: return
        val builder: RequestBuilder<Drawable> = Glide.with(imageView.context).load(url)
        val options = RequestOptions.circleCropTransform()
        builder.apply(options)
            .placeholder(R.drawable.ic_header_default)
            .error(R.drawable.ic_header_default)
            .into(imageView)
    }



}