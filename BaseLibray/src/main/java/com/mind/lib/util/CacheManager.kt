package com.mind.lib.util


class CacheManager private constructor() {

    companion object {
        private const val TOKEN = "token"
        private const val VERSION = "version";
        val instance: CacheManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CacheManager()
        }
    }

    val map = HashMap<String, String>()

    /**
     * 存入
     */
    fun put(
        key: String, value: String
    ) {
        this.map[key] = value
    }

    /**
     * 取数据
     */
    fun get(key: String): String? {
        return map[key]
    }

    /**
     * 放入token
     */
    fun putToken(value: String) {
        map[TOKEN] = value
    }

    /**
     * 取出Token
     */
    fun getToken() = map[TOKEN] ?: ""

    /**
     * 放入版本号
     */
    fun putVersion(version: String) {
        map[VERSION] = version
    }

    /**
     * 取出版本号
     */
    fun getVersion() = map[VERSION] ?: ""

}