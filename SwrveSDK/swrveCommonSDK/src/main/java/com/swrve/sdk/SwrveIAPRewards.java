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

import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SwrveIAPRewards {
	protected static final String LOG_TAG = "SwrveSDK";
	
	/**
	 * Store the content (reward currency + reward items) of the IAP
	 */
	protected Map<String,Map<String,Object>> rewards;

	public SwrveIAPRewards() {
		this.rewards = new HashMap<String,Map<String,Object>>();
	}
	
	public SwrveIAPRewards(String currencyName, long amount) {
		this.rewards = new HashMap<String,Map<String,Object>>();
		this.addCurrency(currencyName, amount);
	}

	protected void _addItem(String resourceName, long quantity) {
		this.addObject(resourceName, quantity, "item");
	}
	
	protected void _addCurrency(String currencyName, long amount) {
		this.addObject(currencyName, amount, "currency");
	}
	
	protected JSONObject _getRewardsJSON() throws JSONException {
		JSONObject json = new JSONObject();
		
		for(Map.Entry<String,Map<String,Object>> entry : this.rewards.entrySet()) {
			json.put(entry.getKey(), new JSONObject(entry.getValue()));
		}
		
		return json;
	}
	
	protected void addObject(String name, long quantity, String type) {
		if (checkParameters(name, quantity, type)) {
			Map<String,Object> item = new HashMap<String,Object>();
			item.put("amount", quantity);
			item.put("type", type);
			this.rewards.put(name, item);
		}
	}
	
	protected boolean checkParameters(String name, long quantity, String type) throws IllegalArgumentException {
		if (SwrveHelper.IsNullOrEmpty(type)) {
			Log.e(LOG_TAG, "SwrveIAPRewards illegal argument: type cannot be empty");
			return false;
		}    
		if (SwrveHelper.IsNullOrEmpty(name)) {
			Log.e(LOG_TAG, "SwrveIAPRewards illegal argument: reward name cannot be empty");
			return false;
		}
		if (quantity <= 0) {
			Log.e(LOG_TAG, "SwrveIAPRewards illegal argument: reward amount must be greater than zero");
			return false;
		}
				
		return true;
	}

	/** 
	 * Public functions all wrapped in try/catch to avoid exceptions leaving the SDK and affecting the app itself.
	 */
	
	public void addItem(String resourceName, long quantity) {
		try {
			_addItem(resourceName, quantity);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
	}
	
	public void addCurrency(String currencyName, long amount) {
		try {
			_addCurrency(currencyName, amount);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
	}
	
	public JSONObject getRewardsJSON() {
		try {
			return _getRewardsJSON();
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return null;
	}
}