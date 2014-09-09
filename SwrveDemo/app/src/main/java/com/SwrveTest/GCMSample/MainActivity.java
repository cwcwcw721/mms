package com.SwrveTest.GCMSample;



import java.util.HashMap;

import com.google.android.gcm.GCMRegistrar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import static com.SwrveTest.GCMSample.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.SwrveTest.GCMSample.CommonUtilities.EXTRA_MESSAGE;
import static com.SwrveTest.GCMSample.CommonUtilities.SENDER_ID;
import static com.SwrveTest.GCMSample.CommonUtilities.SERVER_URL;

// From swrve
import java.util.Map;
import com.swrve.sdk.ISwrve;
import com.swrve.sdk.SwrveInstance;

public class MainActivity extends Activity {

	//private String TAG = " PushActivity";
	private boolean appAvailable = true;
	private static final String LOG_TAG = "SwrveDemo";
	private TextView msg;
	private ISwrve swrve;

    AsyncTask<Void, Void, Void> mRegisterTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		// Configure SDK
		int gameId = 1759;
		String apiKey = "tPu0KpZZKDVyezwMcQ";
				
		try {
			// Initialize SDK
			//swrve = SwrveInstance.getInstance().init(this, gameId, apiKey, SwrveConfig.withPush(SENDER_ID));
			swrve = SwrveInstance.getInstance().init(this, gameId, apiKey);
		} catch(IllegalArgumentException exp) {
			Log.e(LOG_TAG, "Could not initialize Swrve", exp);
		}

		checkNotNull(SERVER_URL, "SERVER_URL");
        checkNotNull(SENDER_ID, "SENDER_ID");
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
        setContentView(R.layout.main);
        msg = (TextView) findViewById(R.id.display);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
            	msg.append(getString(R.string.already_registered) + "\n");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        boolean registered = ServerUtilities.register(context, regId);
                        // At this point all attempts to register with the app
                        // server failed, so we need to unregister the device
                        // from GCM - the app will try to register again when
                        // it is restarted. Note that GCM will send an
                        // unregistered callback upon completion, but
                        // GCMIntentService.onUnregistered() will ignore it.
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
        
        swrveEventsPush(regId);
    }
    
    private boolean swrveEventsPush(String regId)
    {
        // Log a custom event to show regId
		HashMap<String, String> payloads = new HashMap<String, String>();
		payloads.put("Device Id", regId);
		swrve.event("Device_Registration", payloads);

		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("Name", "Yuxing Sun");
		attributes.put("Email", "Yuxing_Sun@mcafee.com");
		swrve.userUpdate(attributes);

    	return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            /*
             * Typically, an application registers automatically, so options
             * below are disabled. Uncomment them if you want to manually
             * register or unregister the device (you will also need to
             * uncomment the equivalent options on options_menu.xml).
             */
            /*
            case R.id.options_register:
                GCMRegistrar.register(this, SENDER_ID);
                return true;
            case R.id.options_unregister:
                GCMRegistrar.unregister(this);
                return true;
             */
            case R.id.options_clear:
            	msg.setText(null);
                return true;
            case R.id.options_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        unregisterReceiver(mHandleMessageReceiver);
        GCMRegistrar.onDestroy(this);
        super.onDestroy();
		// Notify the SDK of app shutdown. Only shutdown Swrve if
		// it is instrumented in only one activity or when
		// closing the application.
		appAvailable = false;
		swrve.onDestroy();
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }

    private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            msg.append(newMessage + "\n");
            HashMap<String, String> payloads = new HashMap<String, String>();
    		payloads.put("Message", newMessage);
    		swrve.event("Message_Received", payloads);
        }
    };
}
	


