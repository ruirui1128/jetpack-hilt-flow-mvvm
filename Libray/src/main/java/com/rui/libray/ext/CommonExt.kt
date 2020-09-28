package com.rui.libray.ext

import android.view.View


fun View.onClick(method:()->Unit):View{
    setOnClickListener { method() }
    return this
}


fun View.onClick(listener: View.OnClickListener):View{
    setOnClickListener(listener)
    return this
}

