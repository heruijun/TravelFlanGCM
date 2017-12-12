package gcm.heruijun.com.travelflangcm.global

import android.content.Context
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager

import com.gcm.heruijun.base.common.GlobalApplication

/**
 * Created by heruijun on 2017/12/11.
 */

class MyApplication : GlobalApplication() {

    override fun onCreate() {
        super.onCreate()
        app = this

        getScreenSize()
    }

    fun getScreenSize() {
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        val display = windowManager.defaultDisplay
        display.getMetrics(dm)
        DIMEN_RATE = dm.density / 1.0f
        DIMEN_DPI = dm.densityDpi
        SCREEN_WIDTH = dm.widthPixels
        SCREEN_HEIGHT = dm.heightPixels
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            val t = SCREEN_HEIGHT
            SCREEN_HEIGHT = SCREEN_WIDTH
            SCREEN_WIDTH = t
        }
    }

    companion object {

        var SCREEN_WIDTH = -1
        var SCREEN_HEIGHT = -1
        var DIMEN_RATE = -1.0f
        var DIMEN_DPI = -1
        lateinit var app: MyApplication
    }
}
