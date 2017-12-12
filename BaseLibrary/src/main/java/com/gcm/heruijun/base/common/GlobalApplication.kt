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
        context = applicationContext
        handler = Handler()
        mainThreadId = android.os.Process.myTid()
    }

    companion object {

        /**
         * get application context
         *
         * @return context
         */
        lateinit var context: Context
            protected set
        /**
         * get global handler
         *
         * @return handler
         */
        lateinit var handler: Handler
            protected set
        /**
         * get main thread id
         *
         * @return id
         */
        var mainThreadId: Int = 0
            protected set
    }
}
