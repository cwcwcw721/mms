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

import io.arise.ABTest;
import io.arise.Arise;
import io.arise.VariationListener;

import static com.mcafee.mms.amazonabtestingdemo.CommonUtilities.ARISE_EXP_NAME;


public class AriseActivity extends ActionBarActivity implements RecommendationDialog.NoticeDialogListener{

    private int countAccumulated = 0;
    private int thresholdCount = 10;
    private Boolean waitforRecommendation = true;
    private Boolean blocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arise);
        initializeApptimize();
        registerControlHandlers();
    }

    private void initializeApptimize() {
        // Initialize the arise library
        String authKey = "5d0df32e2c786398db0a802310c2ab43ec462371";
        String appName = "AbTestDemo";
        Arise.initialize(getApplicationContext(), authKey, appName);
        displayLogMessage("Initialize Arise");
        //onLoadLevel();
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
        onLoadLevel();
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
        // Get and setup the variation
        ABTest.getVariationWithListener(ARISE_EXP_NAME, "10", new VariationListener() {
            @Override
            public void onVariationAvailable(String value) {
                thresholdCount = Integer.parseInt(value);
                //displayLogMessage("thresholdCount = " + String.valueOf(thresholdCount));
            }
        });
    }

    private void setViewEvent() {
        ABTest.recordView(ARISE_EXP_NAME);
        displayLogMessage("Set view event for " + ARISE_EXP_NAME);
    }

    private void onRecordGoalEvent() {
        ABTest.recordConversion(ARISE_EXP_NAME);
        displayLogMessage("Set goal event for " + ARISE_EXP_NAME);
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
        onRecordGoalEvent();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        waitforRecommendation = false;
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        blocked = false;
    }


}
