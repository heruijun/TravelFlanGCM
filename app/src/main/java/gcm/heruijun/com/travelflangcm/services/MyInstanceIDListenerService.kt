package gcm.heruijun.com.travelflangcm.services

import android.content.Intent

import com.google.android.gms.iid.InstanceIDListenerService

/**
 * Created by heruijun on 2017/12/10.
 */

class MyInstanceIDListenerService : InstanceIDListenerService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    override fun onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }

    companion object {

        private val TAG = "MyInstanceIDLS"
    }
}