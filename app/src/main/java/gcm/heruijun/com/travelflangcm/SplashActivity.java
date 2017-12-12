package gcm.heruijun.com.travelflangcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gcm.heruijun.base.ui.activity.BaseCompatActivity;
import com.gcm.heruijun.base.utils.AppUtils;

/**
 * Created by heruijun on 2017/12/10.
 */

public class SplashActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppUtils.getHandler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }
}
