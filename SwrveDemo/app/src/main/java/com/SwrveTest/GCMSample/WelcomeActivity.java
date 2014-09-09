package com.SwrveTest.GCMSample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

import com.swrve.sdk.ISwrve;
import com.swrve.sdk.ISwrveResourcesListener;
import com.swrve.sdk.SwrveInstance;
import com.swrve.sdk.SwrveResourceManager;
import com.swrve.sdk.config.SwrveConfig;
import com.swrve.sdk.gcm.ISwrvePushNotificationListener;
import com.swrve.sdk.messaging.ISwrveCustomButtonListener;

import static com.SwrveTest.GCMSample.CommonUtilities.SENDER_ID;
import static com.SwrveTest.GCMSample.CommonUtilities.API_Key;
import static com.SwrveTest.GCMSample.CommonUtilities.GAME_ID;
import static com.SwrveTest.GCMSample.CommonUtilities.TAG;

public class WelcomeActivity extends Activity {
    private ISwrve swrve;
    private boolean appAvailable = true;
    private static final String LOG_TAG = TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Register IAM deeplink listeners
        setCustomerInAppMessageDeeplinkListerner();

        setPushNotificationListener();
        setResourceChangeListener();

        // Initialize SDK
        try {
            swrve = SwrveInstance.getInstance().init(this, GAME_ID, API_Key, SwrveConfig.withPush(SENDER_ID));
        } catch(IllegalArgumentException exp) {
            Log.e(LOG_TAG, "Could not initialize Swrve", exp);
        }
        setWelcomeMessage();
    }

    private void setResourceChangeListener() {
        SwrveInstance.getInstance().setResourcesListener(new ISwrveResourcesListener() {
            public void onResourcesUpdated() {
                setWelcomeMessage();
            }
        });
    }

    private void setWelcomeMessage() {
        SwrveResourceManager resourceManager = SwrveInstance.getInstance().getResourceManager();
        TextView welcomeScreen = (TextView) findViewById(R.id.textView_welcome);
        String strMessage = resourceManager.getAttributeAsString("swrve_demo_app_navigation_config", "Welcome_message", "Welcome!");
        HashMap<String, String> payloads = new HashMap<String, String>();
        payloads.put("Message", strMessage);
        swrve.event("setWelcomeMessage", payloads);
        welcomeScreen.setText(strMessage);
    }

    private void sendEvent(String eventtype) {
        //HashMap<String, String> payloads = new HashMap<String, String>();
        //payloads.put("AcceptIncomingCall", "Yes");
        swrve.event(eventtype);
    }

    private void setPushNotificationListener() {
        SwrveInstance.getInstance().setPushNotificationListener(new ISwrvePushNotificationListener() {
            @Override
            public void onPushNotification(Bundle bundle) {
                //HashMap<String, String> payloads = new HashMap<String, String>();
                //payloads.put("AcceptIncomingCall", "Yes");
                swrve.event("setPushNotificationListener");
            }
        });
    }

    private void setCustomerInAppMessageDeeplinkListerner() {
        SwrveInstance.getInstance().setCustomButtonListener(new ISwrveCustomButtonListener() {
            @Override
            public void onAction(String customAction) {

                HashMap<String, String> payloads = new HashMap<String, String>();

                if(customAction.equals("AcceptIncomingCall")) {
                    payloads.put("AcceptIncomingCall", "Yes");
                } else if(customAction.equals("RejectIncomingCall")) {
                    payloads.put("AcceptIncomingCall", "No");
                } else return;

                swrve.event("IAMDeepLink4IncomingCallDecision", payloads);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean handled = true;
        int id = item.getItemId();

        switch (id){
            case R.id.action_GCM:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_Swrve:
                intent = new Intent(this, SwrveActivity.class);
                startActivity(intent);
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }

        return handled;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Notify the SDK of app resume
        appAvailable = true;
        swrve.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Notify the SDK of app pause
        appAvailable = false;
        swrve.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Notify the SDK of app shutdown. Only shutdown Swrve if
        // it is instrumented in only one activity or when
        // closing the application.
        appAvailable = false;
        swrve.onDestroy();
    }


}
