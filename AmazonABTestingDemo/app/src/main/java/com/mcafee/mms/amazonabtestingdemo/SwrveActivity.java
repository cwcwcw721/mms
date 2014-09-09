package com.mcafee.mms.amazonabtestingdemo;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.swrve.sdk.ISwrve;
import com.swrve.sdk.ISwrveResourcesListener;
import com.swrve.sdk.SwrveInstance;
import com.swrve.sdk.SwrveResourceManager;
import com.swrve.sdk.config.SwrveConfig;
import com.swrve.sdk.gcm.ISwrvePushNotificationListener;
import com.swrve.sdk.messaging.ISwrveCustomButtonListener;

import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.SENDER_ID;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.API_Key;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.GAME_ID;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.TAG;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.VARIABLE_NAME;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.SWRVE_RESOURCE_NAME;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.GOAL_EVENT;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.VIEW_EVENT;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.COUNT_EVENT_NAME;

public class SwrveActivity extends ActionBarActivity implements RecommendationDialog.NoticeDialogListener{

    private int countAccumulated = 0;
    private int thresholdCount = 0;
    private Boolean waitforRecommendation = true;
    private Boolean blocked = false;

    private ISwrve swrve;
    private boolean appAvailable = true;
    private static final String LOG_TAG = TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swrve);

        registerControlHandlers();
        initializeSwrve();
    }

    private void initializeSwrve() {
        // Initialize SDK
        try {
            swrve = SwrveInstance.getInstance().init(this, GAME_ID, API_Key, SwrveConfig.withPush(SENDER_ID));
        } catch(IllegalArgumentException exp) {
            Log.e(LOG_TAG, "Could not initialize Swrve", exp);
        }

        displayLogMessage("Initialize Swrve SDK");

        onLoadLevel();
    }

    private void registerControlHandlers() {
        Button addCountButton = (Button) findViewById(R.id.button_add);
        addCountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onAddCountButtonClick();
            }
        });

        Button loadButton = (Button) findViewById(R.id.button_resetCounter);
        loadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                countAccumulated = 0;
                blocked = false;
                waitforRecommendation = true;
                setCount();
            }
        });

        Button submitButton = (Button) findViewById(R.id.button_submitEvents);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(waitforRecommendation) {
                    swrve.sendQueuedEvents();
                    displayLogMessage("Submit all events");
                }
            }
        });

        // Register IAM deeplink listeners
        setCustomerInAppMessageDeeplinkListerner();
        setPushNotificationListener();
        setResourceChangeListener();

        displayLogMessage("Register control handlers.");
    }

    private void setResourceChangeListener() {
        SwrveInstance.getInstance().setResourcesListener(new ISwrveResourcesListener() {
            public void onResourcesUpdated() {
                onLoadLevel();
                countAccumulated = 0;
                blocked = false;
                waitforRecommendation = true;
                setCount();
            }
        });
    }

    private void setPushNotificationListener() {
//        SwrveInstance.getInstance().setPushNotificationListener(new ISwrvePushNotificationListener() {
//            @Override
//            public void onPushNotification(Bundle bundle) {
//                HashMap<String, String> payloads = new HashMap<String, String>();
//                payloads.put("AcceptIncomingCall", "Yes");
//                swrve.event("setPushNotificationListener");
//            }
//        });
    }

    private void setCustomerInAppMessageDeeplinkListerner() {
        SwrveInstance.getInstance().setCustomButtonListener(new ISwrveCustomButtonListener() {
            @Override
            public void onAction(String customAction) {

                HashMap<String, String> payloads = new HashMap<String, String>();

//                if(customAction.equals("AcceptIncomingCall")) {
//                    payloads.put("AcceptIncomingCall", "Yes");
//                } else if(customAction.equals("RejectIncomingCall")) {
//                    payloads.put("AcceptIncomingCall", "No");
//                } else return;

                payloads.put("Decision", customAction);

                swrve.event("WhatAnimalDoesUserLikeResultEvent", payloads);
            }
        });
    }

    private void displayLogMessage(String s) {
        TextView view = (TextView) findViewById(R.id.text_defaultStatusText);
        view.append(s + "\n");
    }

    private void onAddCountButtonClick(){
        countAccumulated ++;

        setCount();

        if(!waitforRecommendation) return;

        if(blocked){
            showToast(getString(R.string.caller_is_blocked_message));
            return;
        }

        if(countAccumulated >= thresholdCount){
            showRecommendationDialog();
        }
        else
        {
            HashMap<String, String> payloads = new HashMap<String, String>();
            payloads.put("Count", String.valueOf(countAccumulated));
            swrve.event(COUNT_EVENT_NAME, payloads);
        }
    }

    private void setCount() {
        TextView countView = (TextView) findViewById(R.id.Count);
        countView.setText(String.valueOf(countAccumulated));
    }

    private void showToast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void showRecommendationDialog() {
        RecommendationDialog dialog = new RecommendationDialog();
        setViewEvent();
        dialog.show(getFragmentManager(), "Recommendation for Incoming calls");
    }

    private void onLoadLevel() {
        SwrveResourceManager resourceManager = SwrveInstance.getInstance().getResourceManager();
        thresholdCount = resourceManager.getAttributeAsInt(SWRVE_RESOURCE_NAME, VARIABLE_NAME, 10);
    }

    private void setViewEvent() {
        swrve.event(VIEW_EVENT);
        displayLogMessage("Set view event: " + VIEW_EVENT);
    }

    private void onRecordGoalEvent(int index) {
          HashMap<String, String> payloads = new HashMap<String, String>();
          payloads.put(GOAL_EVENT[index], "Yes");
          swrve.event(GOAL_EVENT[0], payloads);
          displayLogMessage("Set view event: " + GOAL_EVENT[0] + " with " + GOAL_EVENT[index] + " is set to Yes");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        blocked = true;
        onRecordGoalEvent(0);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        waitforRecommendation = false;
        onRecordGoalEvent(2);
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        blocked = false;
        onRecordGoalEvent(1);
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

        // Submit events to the server
        if(waitforRecommendation) {
            swrve.sendQueuedEvents();
            displayLogMessage("onPause, submit all events");
        }

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
