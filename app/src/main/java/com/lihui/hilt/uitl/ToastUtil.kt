package com.lihui.hilt.uitl


import android.widget.Toast
import com.lihui.hilt.app.MyApp


object ToastUtil  {
    fun toast(content:String){
        Toast.makeText(MyApp.getApp(),content,Toast.LENGTH_LONG).show()
    }
}
