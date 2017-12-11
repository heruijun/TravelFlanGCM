package gcm.heruijun.com.travelflangcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

import gcm.heruijun.com.common_lib.activity.BaseCompatActivity;
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
    private ProgressBar mRegistrationProgressBar;
    private boolean isReceiverRegistered;
    private RecyclerView mMessageListView;
    private List<ChatMessage> mMessages;
    private MessageAdapter mChatAdapter;
    private AppCompatEditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationProgressBar = findViewById(R.id.registrationProgressBar);
        mMessageListView = findViewById(R.id.list_chat_messages);
        mEditText = findViewById(R.id.message_edit);

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

    public void sendMessage(View v) {
        if (TextUtils.isEmpty(mEditText.getText().toString())) {
            TSnackbar.make(findViewById(android.R.id.content), "message can not be null !", TSnackbar.LENGTH_LONG).show();
            return;
        }
        ChatMessage chat = new ChatMessage();
        chat.setMessage(mEditText.getText().toString());
        chat.setLeftMessage(false);
        mChatAdapter.add(chat);
    }

    private void registerGCMReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    TSnackbar.make(findViewById(android.R.id.content), "GCM bind token success!", TSnackbar.LENGTH_LONG).show();
                } else {
                    TSnackbar.make(findViewById(android.R.id.content), "GCM bind token failed!", TSnackbar.LENGTH_LONG).show();
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
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
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

}
