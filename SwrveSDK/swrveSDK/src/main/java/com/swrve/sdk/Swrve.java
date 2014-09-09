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
package com.swrve.sdk;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.swrve.sdk.config.SwrveConfig;

/**
 * Main object used to implement the Swrve SDK. Clients may also use
 * SwrveInstance that creates a singleton Swrve object providing static access.
 */
public class Swrve extends SwrveBase<ISwrve, SwrveConfig> implements ISwrve {

	protected Swrve() {
	}

	@Override
	protected SwrveConfig defaultConfig() {
		return new SwrveConfig();
	}

	@Override
	protected void beforeSendDeviceInfo(Context context) {
	}

	@Override
	protected void afterInit() {
	}
	
	@Override
	protected void afterBind() {
	}

	@Override
	protected void extraDeviceInfo(JSONObject deviceInfo) throws JSONException {
	}
}
