package gcm.heruijun.com.travelflangcm

import android.content.Intent
import android.os.Bundle

import com.gcm.heruijun.base.ui.activity.BaseCompatActivity
import com.gcm.heruijun.base.utils.AppUtils

/**
 * Created by heruijun on 2017/12/10.
 */

class SplashActivity : BaseCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AppUtils.getHandler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
