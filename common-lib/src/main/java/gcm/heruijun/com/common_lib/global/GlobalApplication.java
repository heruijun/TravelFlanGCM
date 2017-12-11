package gcm.heruijun.com.common_lib.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by heruijun on 2017/12/11.
 */

public class GlobalApplication extends Application {

    protected static Context mContext;
    protected static Handler mHandler;
    protected static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
    }

    /**
     * get application context
     *
     * @return context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * get global handler
     *
     * @return handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * get main thread id
     *
     * @return id
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }
}
