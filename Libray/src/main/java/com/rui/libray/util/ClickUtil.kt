package com.rui.libray.util

/**
 * 事件点击工具类，设置事件点击的响应时间间隔
 */
object ClickUtil {

    private var lastClickTime: Long = 0
    private var lastButtonId = -1
    private val DIFF: Long = 500    //时间间隔

    /**
     * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（任意两个view，固定时长1s）
     *
     * @return
     */
    val isFastDoubleClick: Boolean
        get() = isFastDoubleClick(-1, DIFF)

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（任意两个view，自定义间隔时长）
     *
     * @return
     */
    fun isFastDoubleClick(diff: Long): Boolean {
        return isFastDoubleClick(-1, diff)
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（同一按钮，自定义间隔时长）
     *
     * @param diff
     * @return
     */
    @JvmOverloads
    fun isFastDoubleClick(buttonId: Int, diff: Long = DIFF): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true
        }
        lastClickTime = time
        lastButtonId = buttonId
        return false
    }
}
/**
 * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（同一个view，固定时长1s）
 *
 * @return
 */