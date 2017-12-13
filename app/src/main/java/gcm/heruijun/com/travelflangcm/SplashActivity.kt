package gcm.heruijun.com.travelflangcm

import android.os.Bundle

import com.gcm.heruijun.base.ui.activity.BaseCompatActivity
import com.gcm.heruijun.base.utils.AppUtils
import org.jetbrains.anko.*

/**
 * Created by heruijun on 2017/12/10.
 */

class SplashActivity : BaseCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AppUtils.handler.postDelayed({
            startActivity(intentFor<MainActivity>())
            finish()
        }, 2000)
    }
}
