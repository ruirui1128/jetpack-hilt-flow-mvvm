package com.mind.lib.data.net




enum class ResCode(private val code: Int, private val message:String) {

    /**
     * 响应成功
     */
    OK(110,"响应成功"),

    /**
     * 加载更多失败
     */
    LOAD_MORE_ERROR(9999,"加载更多失败"),


    /**
     * 未知错误
     */
    UNKNOWN_ERROR(1000,"未知错误"),
    /**
     * 解析错误
     */
    PARSE_ERROR(1001,"解析错误"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(1002,"net error"),
    /**
     * 协议出错
     */
    HTTP_ERROR(1003,"协议出错"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004,"证书出错"),

    PASSWORD_ERROR(1101,"密码错误"),

    /**
     * Token失效
     */
    TOKEN_ERROR(3906, "TOKEN失效"),

    /**
     * NO_User
     */
    OTHER_ERROR(9999,""),

    /**
     * TOKEN失效
     */
    TOKEN_OUT(40004,"TOKEN失效");




    fun getCode(): Int {
        return code
    }

    fun getMessage():String{
        return message
    }

}