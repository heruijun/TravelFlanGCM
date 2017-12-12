package gcm.heruijun.com.travelflangcm.services

import android.app.IntentService
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

import com.google.android.gms.gcm.GcmPubSub
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID

import java.io.IOException

import gcm.heruijun.com.travelflangcm.R

/**
 * Created by heruijun on 2017/12/10.
 */

class RegistrationIntentService : IntentService(TAG) {

    override fun onHandleIntent(intent: Intent?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        try {
            val instanceID = InstanceID.getInstance(this)
            val token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)
            Log.i(TAG, "GCM Registration Token: " + token)

            sendRegistrationToServer(token)

            // Subscribe to topic channels
            subscribeTopics(token)

            // store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply()
            // [END register_for_gcm]
        } catch (e: Exception) {
            Log.d(TAG, "Failed to complete token refresh", e)
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply()
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        val registrationComplete = Intent(QuickstartPreferences.REGISTRATION_COMPLETE)
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
    }

    /**
     * Persist registration to travel flan servers.
     *
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String) {
        // send user data to registration server
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    @Throws(IOException::class)
    private fun subscribeTopics(token: String) {
        val pubSub = GcmPubSub.getInstance(this)
        for (topic in TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null)
        }
    }

    companion object {

        private val TAG = "RegIntentService"
        private val TOPICS = arrayOf("global")
    }

}