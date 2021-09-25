package com.mind.data.data.viewmodel

import androidx.lifecycle.viewModelScope
import com.mind.data.data.api.LoadApi
import com.mind.lib.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by rui
 *  on 2021/8/10
 */
@Suppress("LABEL_NAME_CLASH")
open class LoadViewModel(private val loadApi: LoadApi) : BaseViewModel() {

    /**
     * 下载
     */
    fun download(
        url: String,
        loadPath: String,
        map: HashMap<String, String> = HashMap(),
        start: () -> Unit = {},
        progress: (Int) -> Unit = {},
        end: () -> Unit = {},
        error: () -> Unit = {}

    ) {

        start()
        loadApi.download(url, map).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                viewModelScope.launch(Dispatchers.IO) {
                    var inputStream: InputStream? = null
                    var outputStream: OutputStream? = null
                    if (response.isSuccessful) {
                        try {
                            val file = File(loadPath)
                            val body = response.body()
                            if (body == null) {
                                withContext(Dispatchers.Main) { error() }
                                return@launch
                            }
                            val fileReader = ByteArray(1024)
                            val fileSize = body.contentLength()
                            var fileSizeDownloaded: Long = 0

                            inputStream = body.byteStream()
                            outputStream = FileOutputStream(file)

                            while (true) {
                                val read = inputStream.read(fileReader)
                                if (read == -1) {
                                    break
                                }
                                outputStream.write(fileReader, 0, read)
                                fileSizeDownloaded += read
                                //计算当前下载百分比，并经由回调传出  进度条 progress
                                val pro = (100 * fileSizeDownloaded / fileSize).toInt()
                                withContext(Dispatchers.Main) { progress(pro) }

                            }
                            outputStream.flush()
                            withContext(Dispatchers.Main) { end() }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) { error() }
                        } finally {
                            inputStream?.close();
                            outputStream?.close();
                        }
                    } else {
                        withContext(Dispatchers.Main) { error() }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                error()
            }
        })


    }


}