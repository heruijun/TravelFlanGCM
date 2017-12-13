package com.gcm.heruijun.base.ui.activity

import android.support.v7.app.AppCompatActivity
import com.androidadvance.topsnackbar.TSnackbar
import org.jetbrains.anko.find

/**
 * Created by heruijun on 2017/12/10.
 */

open class BaseCompatActivity : AppCompatActivity() {

    fun snackbarToast(message: CharSequence) {
        TSnackbar.make(find(android.R.id.content), message, TSnackbar.LENGTH_LONG).show()
    }
}