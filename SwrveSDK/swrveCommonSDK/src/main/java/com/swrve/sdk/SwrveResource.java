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
import android.util.Log;

public class SwrveResource {
	
	protected static final String LOG_TAG = "SwrveSDK";
	protected Map<String, String> attributes;
	
	public SwrveResource(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	protected String _getAttributeAsString(String attributeId, String defaultValue) {
		if (this.attributes.containsKey(attributeId)) {
			return this.attributes.get(attributeId);
		}
		return defaultValue;
	}
	
	protected int _getAttributeAsInt(String attributeId, int defaultValue) {
		if (this.attributes.containsKey(attributeId)) {
			try {
				return Integer.parseInt(this.attributes.get(attributeId));
			} catch (NumberFormatException e) {
				Log.e(LOG_TAG, "Could not retrieve attribute " + attributeId + " as integer value, returning default value instead");
			}
		}
		return defaultValue;
	}
	
	protected float _getAttributeAsFloat(String attributeId, float defaultValue) {
		if (this.attributes.containsKey(attributeId)) {
			try {
				return Float.parseFloat(this.attributes.get(attributeId));
			} catch (NumberFormatException e) {
				Log.e(LOG_TAG, "Could not retrieve attribute " + attributeId + " as float value, returning default value instead");
			}
		}
		return defaultValue;
	}

	protected boolean _getAttributeAsBoolean(String attributeId, boolean defaultValue) {
		if (this.attributes.containsKey(attributeId)) {
			String value = this.attributes.get(attributeId);
			if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")) {
				return true;
			} else {
				return false;
			}
		}
		return defaultValue;
	}

	/** 
	 * Public functions all wrapped in try/catch to avoid exceptions leaving the SDK and affecting the app itself.
	 */
	
	public String getAttributeAsString(String attributeId, String defaultValue) {
		try {
			return _getAttributeAsString(attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}

	public int getAttributeAsInt(String attributeId, int defaultValue) {
		try {
			return _getAttributeAsInt(attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
	
	public float getAttributeAsFloat(String attributeId, float defaultValue) {
		try {
			return _getAttributeAsFloat(attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
	
	public boolean getAttributeAsBoolean(String attributeId, boolean defaultValue) {
		try {
			return _getAttributeAsBoolean(attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
}
