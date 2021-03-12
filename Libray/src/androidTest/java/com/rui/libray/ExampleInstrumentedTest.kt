package com.rui.libray

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.rui.libray.test", appContext.packageName)

        val list2 = mutableListOf(4, 5, 6, 7, 3, 8, 9)//不可变列表
        val mutableList = mutableListOf(1, 2, 3, 8, 5, 6)//可变列表，拥有add、remove、replace、clear等一系列操作函数

        mutableList.filter { it<8 }.forEach{
            println("-------------------$it")
        }



    }
}