package com.SwrveTest.GCMSample;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	/**
	 * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
	 */
	
	/*static final String SERVER_URL = "http://10.0.2.2:10739/WebService1.asmx/PostRegId";*/
	static final String SERVER_URL ="http://192.168.56.1/PushService/PushService.asmx/Post_AndroidRegId";
	
	/**
	 * Google API project id registered to use GCM.
	 */
	static final String SENDER_ID = "682366336746";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "SwrveDemo";

	/**
	 * Intent used to display a message in the screen.
	 */
	static final String DISPLAY_MESSAGE_ACTION = "com.SwrveTest.GCMSample.DISPLAY_MESSAGE";

	/**
	 * Intent's extra that contains the message to be displayed.
	 */
	static final String EXTRA_MESSAGE = "message";

    static final String API_Key = "tPu0KpZZKDVyezwMcQ";

    static final int GAME_ID = 1759;

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
