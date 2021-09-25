package com.mind.lib.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils


import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.exitProcess

@Singleton
class AppManager @Inject constructor(){
    private val activityStack:Stack<Activity> = Stack()
      fun  addActivity(activity: Activity){
        activityStack.add(activity)
    }

    fun finishActivity(activity: Activity?){
        activity?.finish()
        activityStack.remove(activity)
    }

    fun remove(activity: Activity?){
        activityStack.remove(activity)
    }


      fun currentActivity():Activity{
        return activityStack.lastElement()
    }


      fun finishAllActivity(){
        for (act in activityStack){
            act.finish()
        }
        activityStack.clear()
    }

    /**
     * 结束除第一个以外的所有activity
     */
     fun popAllActivityToOne() {
        val size =  activityStack.size - 1
        for (i in size  downTo 1) {
            finishActivity(activityStack[i])
        }
    }


     fun exitApp(context: Context){
        finishAllActivity()
        val activityManager
                = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)

    }

    /**
     * 根据ActivityName获取堆中Activity实例
     *
     * @param activityName
     * @return
     */
      fun getActivity(activityName: String): Activity? {
        val iterator = activityStack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (activity != null && TextUtils.equals(activity.javaClass.name, activityName)) {
                return activity
            }
        }
        return null
    }
}