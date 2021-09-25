package com.mind.data.data.log

import android.content.Context
import com.apkfuns.logutils.file.LogFileEngine
import kotlin.jvm.Volatile
import me.pqpo.librarylog4a.LogBuffer
import com.apkfuns.logutils.file.LogFileParam
import com.apkfuns.logutils.LogLevel
import java.io.File
import java.lang.NullPointerException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rui
 * on 2021/8/12
 */
class LogFactory(context: Context?) : LogFileEngine {

    companion object {
        private const val LOG_CONTENT_FORMAT = "[%s][%s][%s:%s]%s\n"
        private const val LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS"

        /**
         * 该值表示当缓存文件的大小达到该值后，缓存里的文件就会被写入到log文件里
         */
        private const val BUFFER_SIZE = 1024 * 4
    }

    private val dateFormat: DateFormat

    @Volatile
    private var buffer: LogBuffer? = null
    private val context: Context

    override fun writeToFile(logFile: File, logContent: String, params: LogFileParam) {
        if (buffer == null) {
            synchronized(LogFileEngine::class.java) {
                if (buffer == null) {
                    val bufferFile = File(context.filesDir, ".log4aCache")
                    buffer =
                        LogBuffer(
                            bufferFile.absolutePath,
                            BUFFER_SIZE,
                            logFile.absolutePath,
                            false
                        )
                }
            }
        }
        buffer?.write(getWriteString(logContent, params))

    }

    /**
     * 写入文件的内容
     *
     * @param logContent log value
     * @param params     LogFileParam
     * @return file log content
     */
    private fun getWriteString(logContent: String, params: LogFileParam): String {
        val time = dateFormat.format(Date(params.time))
        return String.format(
            LOG_CONTENT_FORMAT, time, getLogLevelString(params.logLevel),
            params.threadName, params.tagName, logContent
        )
    }

    /**
     * 日志等级
     *
     * @param level level
     * @return level string
     */
    private fun getLogLevelString(level: Int): String {
        when (level) {
            LogLevel.TYPE_VERBOSE -> return "V"
            LogLevel.TYPE_ERROR -> return "E"
            LogLevel.TYPE_INFO -> return "I"
            LogLevel.TYPE_WARM -> return "W"
            LogLevel.TYPE_WTF -> return "Wtf"
        }
        return "D"
    }

    override fun flushAsync() {
        if (buffer != null) {
            buffer?.flushAsync()
        }
    }

    override fun release() {
        if (buffer != null) {
            buffer?.release()
            buffer = null
        }
    }



    init {
        if (context == null) {
            throw NullPointerException("Context must not null!")
        }
        this.context = context.applicationContext
        dateFormat = SimpleDateFormat(LOG_DATE_FORMAT, Locale.getDefault())
    }
}