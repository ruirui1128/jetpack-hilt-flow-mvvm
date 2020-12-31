package com.lihui.hilt.uitl

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lihui.hilt.ui.act.LoginActivity

/**
 *Created by Rui
 *on 2020/12/31
 */
class LoginHandler private constructor() {

    private var loginLiveData: MutableLiveData<String>? = null


    fun postLoginHandler(login: String?) {

        if (getLoginLiveData()?.hasObservers() == true) {
            getLoginLiveData()?.postValue(login)
            loginLiveData = null
        }
    }

    fun login(context: Context): LiveData<String>? {
        val intent = Intent(context, LoginActivity::class.java)
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        return getLoginLiveData()
    }

    private fun getLoginLiveData(): MutableLiveData<String>? {
        if (loginLiveData == null) {
            loginLiveData = MutableLiveData()
        }
        return loginLiveData
    }

    companion object {
        private val mLoginHandler = LoginHandler()
        fun get(): LoginHandler {
            return mLoginHandler
        }
    }
}