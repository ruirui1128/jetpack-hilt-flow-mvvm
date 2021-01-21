package com.lihui.hilt.uitl


class CacheManager private constructor() {

    companion object {
        private const val TOKEN = "token"
        val instance: CacheManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CacheManager()
        }
    }

    val map = HashMap<String, String>()

    /**
     * 存入
     */
     fun  put(
        key: String, value: String
    ) {
        this.map[key] = value
    }

    /**
     * 取数据
     */
      fun get(key: String):String? {
       return map[key]
    }

    /**
     * 放入token
     */
    fun putToken(value: String){
        map[TOKEN] = value
    }

    /**
     * 取出Token
     */
    fun getToken():String {
       return map[TOKEN]?:""
    }

}