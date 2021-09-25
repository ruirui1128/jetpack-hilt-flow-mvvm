package com.lihui.hilt.data.repository



import com.mind.data.data.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService){

    suspend fun getPageList(page:String) =  apiService.getPageList(page)

}