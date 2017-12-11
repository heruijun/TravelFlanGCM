package gcm.heruijun.com.travelflangcm;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.androidadvance.topsnackbar.TSnackbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

import gcm.heruijun.com.common_lib.activity.BaseCompatActivity;
import gcm.heruijun.com.common_lib.utils.DialogUtils;
import gcm.heruijun.com.travelflangcm.adapter.MessageAdapter;
import gcm.heruijun.com.travelflangcm.model.ChatMessage;
import gcm.heruijun.com.travelflangcm.services.QuickstartPreferences;
import gcm.heruijun.com.travelflangcm.services.RegistrationIntentService;

/**
 * Created by heruijun on 2017/12/10.
 */

public class MainActivity extends BaseCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private RecyclerView mMessageListView;
    private List<ChatMessage> mMessages;
    private MessageAdapter mChatAdapter;
    private AppCompatEditText mEditText;
    private Dialog mProgressDialog;
    private boolean mSentToken;
    public static final String MESSAGE_RECEIVED = "message_received";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageListView = findViewById(R.id.list_chat_messages);
        mEditText = findViewById(R.id.message_edit);

        obtainSentTokenStatus(MainActivity.this);
        mProgressDialog = DialogUtils.createProgressDialog(MainActivity.this);
        if (!mSentToken) {
            mProgressDialog.show();
        }

        registerGCMReceiver();

        initData();
    }

    private void initData() {
        mMessages = new ArrayList<>();
        mChatAdapter = new MessageAdapter(MainActivity.this, mMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mMessageListView.setLayoutManager(layoutManager);
        mMessageListView.setAdapter(mChatAdapter);
    }

    /**
     * client to server
     *
     * @param v
     */
    public void sendMessage(View v) {
        if (TextUtils.isEmpty(mEditText.getText().toString())) {
            TSnackbar.make(findViewById(android.R.id.content), "message can not be null !", TSnackbar.LENGTH_LONG).show();
            return;
        }
        ChatMessage chat = new ChatMessage();
        chat.setMessage(mEditText.getText().toString());
        chat.setLeftMessage(false);
        mChatAdapter.add(chat);
        mEditText.setText("");
        mMessageListView.scrollToPosition(mMessages.size() - 1);
    }

    /**
     * server to client
     *
     * @param message
     */
    private void receiveMessage(String message) {
        ChatMessage chat = new ChatMessage();
        chat.setMessage(message);
        chat.setLeftMessage(true);
        mChatAdapter.add(chat);
        mMessageListView.scrollToPosition(mMessages.size() - 1);
    }

    private void obtainSentTokenStatus(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        mSentToken = sharedPreferences
                .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
    }

    private void registerGCMReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(QuickstartPreferences.REGISTRATION_COMPLETE)) {
                    mProgressDialog.dismiss();
                    obtainSentTokenStatus(context);
                    if (mSentToken) {
                        TSnackbar.make(findViewById(android.R.id.content), "GCM bind token success!", TSnackbar.LENGTH_LONG).show();
                    } else {
                        TSnackbar.make(findViewById(android.R.id.content), "GCM bind token failed!", TSnackbar.LENGTH_LONG).show();
                    }
                } else if (intent.getAction().equals(MESSAGE_RECEIVED)) {
                    String message = intent.getStringExtra("message");
                    receiveMessage(message);
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(QuickstartPreferences.REGISTRATION_COMPLETE);
            intentFilter.addAction(MESSAGE_RECEIVED);
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, intentFilter);
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DialogUtils.showCommonDialog(MainActivity.this, "exit ?", (dialogInterface, i) -> finish());
    }
}
