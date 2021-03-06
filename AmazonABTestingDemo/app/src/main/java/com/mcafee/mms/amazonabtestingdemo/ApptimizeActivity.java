package com.mcafee.mms.amazonabtestingdemo;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.insights.ABTestClient;
import com.amazon.insights.AmazonInsights;
import com.amazon.insights.Event;
import com.amazon.insights.EventClient;
import com.amazon.insights.InsightsCallback;
import com.amazon.insights.InsightsCredentials;
import com.amazon.insights.Variation;
import com.amazon.insights.VariationSet;
import com.amazon.insights.error.InsightsError;
import com.apptimize.Apptimize;
import com.apptimize.ApptimizeTest;

import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.GOAL_EVENT;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.PRIVATE_KEY;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.PROJECT_NAME;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.PUBLIC_KEY;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.VARIABLE_NAME;
import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.VIEW_EVENT;

public class ApptimizeActivity extends ActionBarActivity implements RecommendationDialog.NoticeDialogListener{

    private int countAccumulated = 0;
    private int thresholdCount = 0;
    private Boolean waitforRecommendation = true;
    private Boolean blocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);

        registerControlHandlers();
        initializeApptimize();
    }

    private void initializeApptimize() {
        Apptimize.setup(this, "DmIjjV0J592Katba4bdYIfDp-IgQTYE");
        displayLogMessage("Initialize Apptimize");

        onLoadLevel();
//        Apptimize.runTest("Test3", new ApptimizeTest() {
//            @Override
//            public void baseline() {
//                // Variant: original
//                displayLogMessage("Running baseline ...");
//            }
//
//            @SuppressWarnings("unused")
//            public void variation1() {
//                // Variant: variant
//                displayLogMessage("Running variant ...");
//            }
//        });
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
        thresholdCount = Apptimize.integerForTest("ConfigurationExp", 4);
        displayLogMessage("thresholdCount = " + String.valueOf(thresholdCount));
    }

    private void setViewEvent() {
//        Event viewEvent = eventClient.createEvent(VIEW_EVENT);
//        eventClient.recordEvent(viewEvent);
//        displayLogMessage("Set view event: " + VIEW_EVENT);
    }

    private void onRecordGoalEvent(int index) {
        Apptimize.metricAchieved("ConfigurationMetric");
//        Event goalEvent = eventClient.createEvent(GOAL_EVENT[index]);
//        eventClient.recordEvent(goalEvent);
        displayLogMessage("Set goal event: ConfigurationMetric" + GOAL_EVENT[index]);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Submit events to the server
        if(waitforRecommendation) {
//            eventClient.submitEvents();
//            displayLogMessage("onPause, submit all events");
        }
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
        onRecordGoalEvent(3);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        waitforRecommendation = false;
//        onRecordGoalEvent(2);
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        blocked = false;
//        onRecordGoalEvent(1);
    }

}
