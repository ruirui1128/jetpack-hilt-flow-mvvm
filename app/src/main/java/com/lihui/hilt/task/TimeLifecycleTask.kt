package com.lihui.hilt.task

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.lihui.hilt.task.task.TaskTask1
import com.lihui.hilt.task.task.TaskTask2
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class TimeLifecycleTask() : LifecycleObserver {
    private var scheduled: ScheduledExecutorService? = null


    companion object {
        private const val CORE_POOL_SIZE = 2  //线程池大小
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        scheduled = Executors.newScheduledThreadPool(CORE_POOL_SIZE)
        scheduled?.scheduleWithFixedDelay(TaskTask1(), 2, 5, TimeUnit.SECONDS)
        scheduled?.scheduleWithFixedDelay(TaskTask2(), 5, 5, TimeUnit.SECONDS)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (scheduled?.isShutdown == false) {
            scheduled?.shutdownNow()
        }

    }

}