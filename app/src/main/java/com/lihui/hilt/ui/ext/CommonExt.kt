package com.lihui.hilt.ui.ext

import android.text.Editable
import android.text.TextWatcher

/**
 *Created by Rui
 *on 2020/12/25
 */
inline fun createTextWatcher(
        crossinline afterTextChanged: (s: Editable?) -> Unit = {},
        crossinline beforeTextChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
        crossinline onTextChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> }
) = object : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s, start, before, count)
    }

}