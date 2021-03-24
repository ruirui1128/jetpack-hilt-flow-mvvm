package com.lihui.hilt.ui.vm


import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.data.api.ApiService
import com.rui.libray.base.BaseViewModel
import com.rui.libray.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
         private val apiService: ApiService,
         networkHelper: NetworkHelper):BaseViewModel(networkHelper)
{

     val result = MutableLiveData<String>().apply {
         this.value = "谜一样的男人"
     }
     fun fetchUsers(  listener: (String) -> Unit) {}

}