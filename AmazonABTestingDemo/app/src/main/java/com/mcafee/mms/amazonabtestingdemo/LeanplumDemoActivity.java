package com.mcafee.mms.amazonabtestingdemo;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.ARISE_EXP_NAME;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.GOAL_EVENT;
import static com.mcafee.mms.amazonabtestingdemo.RecommendationDialog.NoticeDialogListener;
import com.leanplum.Leanplum;
import com.leanplum.activities.LeanplumActivity;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.VariablesChangedCallback;

public class LeanplumDemoActivity extends ActionBarActivity implements NoticeDialogListener{

    private int countAccumulated = 0;
    @Variable public int thresholdCount = 3;
    @Variable public static String welcomeMessage = "Welcome to Leanplum!";
    private Boolean waitforRecommendation = true;
    private Boolean blocked = false;
    private LeanplumActivityHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leanplum);
        initializeApptimize();
        registerControlHandlers();
    }

    private void initializeApptimize() {
        // We've inserted your AbTestDemo API keys here for you :)
        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode("an0nmIX1ajgclry81F6P6ywFsruPByDbcGYvier818k", "YEPfYGhFCffLeDRP3CpQhbx6NYLJZXiQ4VD2eoolvTo");
        } else {
            Leanplum.setAppIdForProductionMode("an0nmIX1ajgclry81F6P6ywFsruPByDbcGYvier818k", "kXM7QQNckT5Zoebfxks6VCReKnQlhA6mDDCmpfGROYQ");
        }

        // This will only run once per session, even if the activity is restarted.
        Leanplum.start(this);
        displayLogMessage("Initialize Leanplum");
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
                    //eventClient.submitEvents();
                    displayLogMessage("Submit all events");
                }
            }
        });

        displayLogMessage("Register control handlers.");
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
        Leanplum.addVariablesChangedHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {
                displayLogMessage("thresholdCount = " + String.valueOf(thresholdCount));
                displayLogMessage(welcomeMessage);
                Leanplum.track("ABTestDemo.Launch");
            }
        });
    }

    private void setViewEvent() {
        Leanplum.track("ABTestDemo.ViewEvent");
        displayLogMessage("Set view event: ABTestDemo.ViewEvent");
    }

    private void onRecordGoalEvent(int index) {
        String eventName = "ABTestDemo." + GOAL_EVENT[index];
        Leanplum.track(eventName);
        displayLogMessage("Set goal event: " + eventName);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Submit events to the server
        if(waitforRecommendation) {
//            eventClient.submitEvents();
//            displayLogMessage("onPause, submit all events");
        }
        getHelper().onPause();
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
    protected void onStop() {
        super.onStop();
        getHelper().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHelper().onResume();
    }

    @Override
    public Resources getResources() {
        if (Leanplum.isTestModeEnabled()) {
            return super.getResources();
        }
        return getHelper().getLeanplumResources(super.getResources());
    }

    @Override
    public void setContentView(final int layoutResID) {
        if (Leanplum.isTestModeEnabled()) {
            super.setContentView(layoutResID);
        }
        getHelper().setContentView(layoutResID);
    }

    private LeanplumActivityHelper getHelper() {
        if (helper == null) {
            helper = new LeanplumActivityHelper(this);
        }
        return helper;
    }
}
