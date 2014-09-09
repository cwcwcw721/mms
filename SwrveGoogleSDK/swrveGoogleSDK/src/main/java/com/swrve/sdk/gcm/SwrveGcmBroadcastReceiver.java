/* 
 * SWRVE CONFIDENTIAL
 * 
 * (c) Copyright 2010-2014 Swrve New Media, Inc. and its licensors.
 * All Rights Reserved.
 * 
 * NOTICE: All information contained herein is and remains the property of Swrve
 * New Media, Inc or its licensors.  The intellectual property and technical
 * concepts contained herein are proprietary to Swrve New Media, Inc. or its
 * licensors and are protected by trade secret and/or copyright law.
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from Swrve.
 */
package com.swrve.sdk.gcm;

import com.swrve.sdk.SwrveHelper;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class SwrveGcmBroadcastReceiver extends WakefulBroadcastReceiver {
	private Class<?> intentServiceClass;
	private static String workaroundRegistrationId;
	
	public SwrveGcmBroadcastReceiver() {
		this(SwrveGcmIntentService.class);
	}
	
	public SwrveGcmBroadcastReceiver(Class<?> intentServiceClass) {
		this.intentServiceClass = intentServiceClass;
	}
	
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String registrationId = intent.getStringExtra("registration_id");
            if (!SwrveHelper.IsNullOrEmpty(registrationId)) {
                // We got a registration id!
                workaroundRegistrationId = registrationId;
            }
            
            ComponentName comp = new ComponentName(context.getPackageName(),
            		intentServiceClass.getName());
            // Start the service, keeping the device awake while it is launching.
            startWakefulService(context, (intent.setComponent(comp)));
        }
        setResultCode(Activity.RESULT_OK);
    }

    public static String getWorkaroundRegistrationId() {
        return workaroundRegistrationId;
    }

    public static void clearWorkaroundRegistrationId() {
        workaroundRegistrationId = null;
    }
}