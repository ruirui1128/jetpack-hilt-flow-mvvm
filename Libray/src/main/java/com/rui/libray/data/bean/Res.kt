package com.rui.libray.data.bean


data class Res<T> (
    var code: Int = 0,
    var message: String="",
    var data: T?
)