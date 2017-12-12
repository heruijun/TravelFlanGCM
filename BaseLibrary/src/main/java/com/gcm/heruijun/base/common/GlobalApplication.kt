package com.gcm.heruijun.base.common

import android.app.Application
import android.content.Context
import android.os.Handler

/**
 * Created by heruijun on 2017/12/11.
 */

open class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this
        handler = Handler()
        mainThreadId = android.os.Process.myTid()
    }

    companion object {

        lateinit var context: Context
        lateinit var handler: Handler
        var mainThreadId: Int = 0
    }
}
