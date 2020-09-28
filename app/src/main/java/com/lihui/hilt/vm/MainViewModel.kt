package com.lihui.hilt.vm

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*



import com.lihui.hilt.data.api.ApiService
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper


class MainViewModel @ViewModelInject constructor(
         private val apiService: ApiService,
         networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {

     val result = MutableLiveData<String>()
     fun fetchUsers(  listener: (String) -> Unit) {}

}