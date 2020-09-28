package com.rui.libray.data.bean


data class Res<T> (
    val status: Int,
    val message: String,
    val data: T?
)