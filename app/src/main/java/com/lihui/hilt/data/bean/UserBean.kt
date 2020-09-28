package com.lihui.hilt.data.bean

import com.squareup.moshi.Json


data class UserBean(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val avatar: String = ""
)