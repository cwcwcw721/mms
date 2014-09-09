package com.SwrveTest.GCMSample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.swrve.sdk.ISwrve;
import com.swrve.sdk.SwrveInstance;
import com.swrve.sdk.SwrveIAPRewards;
import com.swrve.sdk.UIThreadSwrveUserResourcesDiffListener;
import com.swrve.sdk.UIThreadSwrveUserResourcesListener;
import com.swrve.sdk.runnable.UIThreadSwrveResourcesDiffRunnable;
import com.swrve.sdk.runnable.UIThreadSwrveResourcesRunnable;
import com.swrve.sdk.config.*;
import static com.SwrveTest.GCMSample.CommonUtilities.SENDER_ID;


public class SwrveActivity extends Activity {
    private boolean appAvailable = true;
    private static final String LOG_TAG = "SwrveDemo";

    private ISwrve swrve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load layout
        setContentView(R.layout.activity_swrve);

        // Configure SDK
        int gameId = 1759;
        String apiKey = "tPu0KpZZKDVyezwMcQ";

        try {
            // Initialize SDK
            swrve = SwrveInstance.getInstance().init(this, gameId, apiKey, SwrveConfig.withPush(SENDER_ID)
            );
        } catch(IllegalArgumentException exp) {
            Log.e(LOG_TAG, "Could not initialize Swrve", exp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.swrve, menu);
        return true;
    }

    public void btnSendEvent(View v) {
        // Queue custom event
        HashMap<String, String> payloads = new HashMap<String, String>();
        payloads.put("level", "" + (new Random().nextInt(2000)));
        swrve.event("TUTORIAL.END", payloads);
    }

    public void btnPurchase(View v) {
        // Queue purchase event
        swrve.purchase("BANANA_PACK", "gold", 120, 99);
    }

    public void btnCurrencyGiven(View v) {
        // Queue currency given event
        swrve.currencyGiven("gold", 999999);
    }

    public void btnIap(View v) {
        // Queue IAP event
        SwrveIAPRewards rewards = new SwrveIAPRewards("gold", 200);
        swrve.iap(1, "CURRENCY_PACK", 9.99, "USD", rewards);
    }

    public void btnUserUpdate(View v) {
        // Queue user update event
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("level", "12");
        attributes.put("coins", "999");
        swrve.userUpdate(attributes);
    }

    public void btnGetResources(View v) {
        // Get resources
        swrve.getUserResources(new UIThreadSwrveUserResourcesListener(SwrveActivity.this, new UIThreadSwrveResourcesRunnable() {
            @Override
            public void onUserResourcesSuccess(Map<String, Map<String, String>> resources, String resourcesAsJSON) {
                if (appAvailable) {
                    // Runs on UI thread
                    String resourcesTxt = resources.keySet().toString();
                    Toast.makeText(SwrveActivity.this, "Resources: " + resourcesTxt, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onUserResourcesError(Exception exception) {
                String exceptionTxt = exception.getMessage();
                Toast.makeText(SwrveActivity.this, "EXCEPTION!: " + exceptionTxt, Toast.LENGTH_LONG).show();
            }
        }));
    }

    public void btnGetResourcesDiffs(View v) {
        // Get AB test resources diffs
        swrve.getUserResourcesDiff(new UIThreadSwrveUserResourcesDiffListener(SwrveActivity.this, new UIThreadSwrveResourcesDiffRunnable() {
            @Override
            public void onUserResourcesDiffSuccess(Map<String, Map<String, String>> oldResourcesValues, Map<String, Map<String, String>> newResourcesValues, String resourcesAsJSON) {
                if (appAvailable) {
                    // Runs on UI thread
                    String resourcesTxt = newResourcesValues.keySet().toString();
                    Toast.makeText(SwrveActivity.this, "New resources: " + resourcesTxt, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onUserResourcesDiffError(Exception exception) {
                String exceptionTxt = exception.getMessage();
                Toast.makeText(SwrveActivity.this, "EXCEPTION!: " + exceptionTxt, Toast.LENGTH_LONG).show();
            }
        }));
    }

    public void btnShowTalkMessage(View v) {
        // Swrve Talk - Trigger message for Swrve.Demo.OfferMessage
        swrve.event("Swrve.Demo.OfferMessage");
    }

    public void btnSendQueuedEvents(View v) {
        // Send data to Swrve
        swrve.sendQueuedEvents();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
