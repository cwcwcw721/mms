package com.mcafee.mms.amazonabtestingdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean handled = true;
        int id = item.getItemId();

        switch (id){
            case R.id.action_amazoninsights:
                Intent intent = new Intent(this, AmazonActivity.class);
                startActivity(intent);
                break;
            case R.id.action_swrve:
                intent = new Intent(this, SwrveActivity.class);
                startActivity(intent);
                break;
            case R.id.action_apptimize:
                intent = new Intent(this, ApptimizeActivity.class);
                startActivity(intent);
                break;
            case R.id.action_arise:
                intent = new Intent(this, AriseActivity.class);
                startActivity(intent);
                break;
            case R.id.action_leanplum:
                intent = new Intent(this, LeanplumDemoActivity.class);
                startActivity(intent);
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }

        return handled;

    }
}
