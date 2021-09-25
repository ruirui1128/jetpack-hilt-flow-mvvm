package com.mind.lib.util

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.mind.lib.R


/**
 *Created by Rui
 *on 2020/12/30
 */
object BaseDialogUtil {
    /**
     * LoadingDialog
     */
    fun showLoadingDialog(
        context: Activity?, lifecycleOwner: LifecycleOwner,
        dialogBehavior: DialogBehavior = ModalDialog,
    ): MaterialDialog? {
        context?:return null
        val view = context.layoutInflater.inflate(R.layout.dialog_loading, null, false)
        return MaterialDialog(context, dialogBehavior).show {
            customView(view = view, dialogWrapContent = false)
            lifecycleOwner(lifecycleOwner)
            cornerRadius(literalDp = 10f)
            maxWidth(res = R.dimen.dp150)
            cancelOnTouchOutside(true)
        }
    }


}