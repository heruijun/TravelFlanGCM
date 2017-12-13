package gcm.heruijun.com.travelflangcm

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.EditText

import com.gcm.heruijun.base.ui.activity.BaseCompatActivity
import com.gcm.heruijun.base.utils.DialogUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

import java.util.ArrayList

import gcm.heruijun.com.travelflangcm.adapter.MessageAdapter
import gcm.heruijun.com.travelflangcm.data.protocol.ChatMessage
import gcm.heruijun.com.travelflangcm.services.QuickstartPreferences
import gcm.heruijun.com.travelflangcm.services.RegistrationIntentService
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by heruijun on 2017/12/10.
 */

class MainActivity : BaseCompatActivity(), AnkoLogger {

    private lateinit var mRegistrationBroadcastReceiver: BroadcastReceiver
    private var isReceiverRegistered: Boolean = false
    private lateinit var mMessageListView: RecyclerView
    private lateinit var mMessages: MutableList<ChatMessage>
    private lateinit var mChatAdapter: MessageAdapter<ChatMessage>
    private lateinit var mEditText: EditText
    private lateinit var mProgressDialog: Dialog
    private var mSentToken: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            mMessageListView = recyclerView().lparams(width = matchParent, height = 0, weight = 1f) {
                topPadding = 15
                bottomPadding = 15
            }

            linearLayout {
                mEditText = editText().lparams(width = 0, height = wrapContent, weight = 1f)
                button {
                    text = "Send"
                    backgroundColorResource = R.color.colorPrimary
                    textColorResource = R.color.colorWhite

                    onClick {
                        if (TextUtils.isEmpty(mEditText.text.toString())) {
                            snackbarToast("message can not be null !")
                        } else {
                            val chat = ChatMessage(mEditText.text.toString(), false)
                            mChatAdapter.add(chat)
                            mEditText.setText("")
                            mMessageListView.scrollToPosition(mMessages.size - 1)
                        }
                    }
                }
            }
        }

        obtainSentTokenStatus(this)
        mProgressDialog = DialogUtils.createProgressDialog(this)
        if (!mSentToken) {
            mProgressDialog.show()
        }

        registerGCMReceiver()

        initData()
    }

    private fun initData() {
        mMessages = ArrayList()
        mChatAdapter = MessageAdapter(this, mMessages)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mMessageListView.layoutManager = layoutManager
        mMessageListView.adapter = mChatAdapter
    }

    /**
     * server to client
     *
     * @param message
     */
    private fun receiveMessage(message: String) {
        val chat = ChatMessage(message, true)
        mChatAdapter.add(chat)
        mMessageListView.scrollToPosition(mMessages.size - 1)
    }

    private fun obtainSentTokenStatus(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mSentToken = sharedPreferences
                .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false)
    }

    private fun registerGCMReceiver() {
        mRegistrationBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == QuickstartPreferences.REGISTRATION_COMPLETE) {
                    mProgressDialog.dismiss()
                    obtainSentTokenStatus(context)
                    if (mSentToken) {
                        snackbarToast("GCM bind token success!")
                    } else {
                        snackbarToast("GCM bind token failed!")
                    }
                } else if (intent.action == MESSAGE_RECEIVED) {
                    val message = intent.getStringExtra("message")
                    receiveMessage(message)
                }
            }
        }

        // Registering BroadcastReceiver
        registerReceiver()

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            startService(intentFor<RegistrationIntentService>())
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver)
        isReceiverRegistered = false
        super.onPause()
    }

    private fun registerReceiver() {
        if (!isReceiverRegistered) {
            val intentFilter = IntentFilter()
            intentFilter.addAction(QuickstartPreferences.REGISTRATION_COMPLETE)
            intentFilter.addAction(MESSAGE_RECEIVED)
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, intentFilter)
            isReceiverRegistered = true
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show()
            } else {
                info("This device is not supported.")
                finish()
            }
            return false
        }
        return true
    }

    override fun onBackPressed() {
        alert("exit ?" ) {
            yesButton { finish() }
            noButton {}
        }.show()
    }

    companion object {
        private val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
        private val TAG = "MainActivity"
        val MESSAGE_RECEIVED = "message_received"
    }
}
