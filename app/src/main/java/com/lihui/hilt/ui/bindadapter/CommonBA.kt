package com.lihui.hilt.ui.bindadapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lihui.hilt.R
import com.rui.libray.util.DensityUtil
import kotlinx.android.synthetic.main.activity_info.*

import java.io.File


object CommonBA {

    /**
     * item中图片绑定
     * imageUrl:图片地址
     * placeHolder：占位图
     * errorHolder：错误加载图
     * isCircle：是否圆角
     * corners: 矩形角度
     */
    @JvmStatic
    @BindingAdapter(
        value = [
            "imageUrl",
            "isCircle",
            "corners",
            "placeHolder",
            "errorHolder"],
        requireAll = false
    )
    fun imageLoadUrl(
        imageView: ImageView,
        imageUrl: String?,
        isCircle: Boolean?,
        corners: Int?,
        placeHolder: Drawable?,
        errorHolder: Drawable?
    ) {

        val builder: RequestBuilder<Drawable> = Glide.with(imageView.context).load(imageUrl)
        loadImage(isCircle, corners, imageView, builder, placeHolder, errorHolder)

    }



    @JvmStatic
    @BindingAdapter(
        value = [
            "imageFile",
            "placeHolder",
            "errorHolder",
            "isCircle",
            "corners"], requireAll = false
    )
    fun imgageLoadFile(
        imageView: ImageView,
        imageFile: File,
        placeHolder: Drawable?,
        errorHolder: Drawable?,
        isCircle: Boolean?,
        corners: Int?
    ) {
        val builder: RequestBuilder<Drawable> = Glide.with(imageView).load(imageFile)
        loadImage(isCircle, corners, imageView, builder, placeHolder, errorHolder)
    }

    @BindingAdapter(
        value = [
            "resIdImage",
            "placeHolder",
            "errorHolder",
            "isCircle",
            "corners"], requireAll = false
    )
    fun imgageLoadRes(
        imageView: ImageView,
        resIdImage: Int,
        placeHolder: Drawable?,
        errorHolder: Drawable?,
        isCircle: Boolean?,
        corners: Int?
    ) {
        val builder: RequestBuilder<Drawable> = Glide.with(imageView).load(resIdImage)
        loadImage(isCircle, corners, imageView, builder, placeHolder, errorHolder)
    }

    /**
     * 加载图片
     */
    @SuppressLint("CheckResult")
    private fun loadImage(
        isCircle: Boolean?,
        corners: Int?,
        imageView: ImageView,
        builder: RequestBuilder<Drawable>,
        placeHolder: Drawable?,
        errorHolder: Drawable?
    ) {
        var options = if (isCircle == true) {
            RequestOptions.circleCropTransform()
        } else {

            val roundedCorners = RoundedCorners( DensityUtil.dp2px(corners?.toFloat()?:1f,imageView.context))
            RequestOptions.bitmapTransform(roundedCorners)
        }

        val layoutParams = imageView.layoutParams
        val width = layoutParams?.width ?: 0
        val height = layoutParams?.height ?: 0
        if (width > 0 && height > 0) {
            builder.override(width, height)
        }
        if (errorHolder!=null){
            builder
                .apply(options)
                .placeholder(R.color.white)
                .error(errorHolder)
                .into(imageView)
        }else{
            builder.apply(options).placeholder(R.color.white).error(R.color.color_ededed)
                .into(imageView)
        }

    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "imageRes",
            "isCircle",
            "corners",
            "placeHolder",
            "errorHolder"],
        requireAll = false
    )
    fun imageLoadRes(
        imageView: ImageView,
        imageUrl: Int?,
        isCircle: Boolean?,
        corners: Int?,
        placeHolder: Drawable?,
        errorHolder: Drawable?

    ) {
        imageView.setBackgroundResource(imageUrl?:R.color.color_999)
    }

    /**
     * SwipeRefreshLayout
     */
    @JvmStatic
    @BindingAdapter(
        value = [
            "isRefreshing"],
        requireAll = false
    )
    fun swipeRefresh(
        swipeRefresh: SwipeRefreshLayout,
        isRefreshing:Boolean = false
    ) {
        if (swipeRefresh.isRefreshing){
            swipeRefresh.isRefreshing = false
        }

    }




}