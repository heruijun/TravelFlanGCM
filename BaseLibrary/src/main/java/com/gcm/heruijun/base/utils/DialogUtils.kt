package com.gcm.heruijun.base.utils

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

import com.gcm.heruijun.base.R

/**
 * Created by heruijun on 2017/12/12.
 */

object DialogUtils {

    @JvmOverloads
    fun createProgressDialog(context: Context, needCancle: Boolean = true): Dialog {
        val dialog = ProgressDialog(context)
        dialog.setMessage("Loading ...")
        dialog.setCancelable(needCancle)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun showCommonDialog(context: Context, message: String,
                         listener: DialogInterface.OnClickListener): Dialog {
        return showCommonDialog(context, message, context.getString(R.string.dialog_positive),
                context.getString(R.string.dialog_negative), listener)
    }

    fun showCommonDialog(context: Context, message: String, positiveText: String,
                         negativeText: String, listener: DialogInterface.OnClickListener): Dialog {
        return AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .setNegativeButton(negativeText, null)
                .show()
    }

    fun showConfirmDialog(context: Context, message: String,
                          listener: DialogInterface.OnClickListener): Dialog {
        return showConfirmDialog(context, message, context.getString(R.string.dialog_positive),
                listener)
    }

    fun showConfirmDialog(context: Context, message: String, positiveText: String,
                          listener: DialogInterface.OnClickListener): Dialog {
        return AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .show()
    }

}
