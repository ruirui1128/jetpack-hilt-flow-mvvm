package com.lihui.hilt.enums

import com.lihui.hilt.R


enum class StarLevelEnum(val level: Int, val res: Int) {
    /**
     * 未开通
     */
    START_0(0, R.drawable.ic_star_level_0),

    /**
     * 星耀1
     */
    START_1(1, R.drawable.ic_star_level_1),

    /**
     * 星耀2
     */
    START_2(2, R.drawable.ic_star_level_2),

    /**
     * 星耀3
     */
    START_3(3, R.drawable.ic_star_level_3),

    /**
     * 星耀4
     */
    START_4(4, R.drawable.ic_star_level_4);

    companion object {
        fun match(level: Int?): Int {
            values().forEach {
                if (it.level == level) {
                    return it.res
                }
            }
            return R.drawable.ic_star_level_0
        }
    }
}