package gcm.heruijun.com.travelflangcm.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

import com.google.android.gms.gcm.GcmListenerService

import gcm.heruijun.com.travelflangcm.MainActivity
import gcm.heruijun.com.travelflangcm.R

/**
 * Created by heruijun on 2017/12/10.
 */

class MyGcmListenerService : GcmListenerService() {

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     * For Set of keys use data.keySet().
     */
    override fun onMessageReceived(from: String?, data: Bundle?) {
        val message = data!!.getString("message")
        Log.d(TAG, "From: " + from!!)
        Log.d(TAG, "Message: " + message!!)

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         * - Store message in local database.
         * - Update UI.
         */
        val intent = Intent(MainActivity.MESSAGE_RECEIVED)
        intent.putExtra("message", message)
        LocalBroadcastManager.getInstance(this@MyGcmListenerService).sendBroadcast(intent)

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message)
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private fun sendNotification(message: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TravelFlan Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {

        private val TAG = "MyGcmListenerService"
    }
}
