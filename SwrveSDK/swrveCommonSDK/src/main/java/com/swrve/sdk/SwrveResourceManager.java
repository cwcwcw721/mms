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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SwrveResourceManager {

	protected static final String LOG_TAG = "SwrveSDK";
	protected Map<String, SwrveResource> resources;
	
	public SwrveResourceManager() {
		this.resources = new HashMap<String, SwrveResource>();
	}

	protected void _setResourcesFromJSON(JSONArray jsonResources) {
		try {
			// Convert to map
			int numResources = jsonResources.length();
			synchronized(this.resources) {
				this.resources = new HashMap<String, SwrveResource>();
				for (int i = 0; i < numResources; i++) {
					JSONObject resourceJSON = jsonResources.getJSONObject(i);
					String uid = resourceJSON.getString("uid");
					SwrveResource resource = new SwrveResource(SwrveHelper.JSONToMap(resourceJSON));
					this.resources.put(uid, resource);
				}
			}
		} catch (JSONException e) {
			Log.i(LOG_TAG, "Invalid JSON received for resources, resources not updated");
		}
	}
	
	protected Map<String, SwrveResource> _getResources() {
		return this.resources;
	}
	
	protected SwrveResource _getResource(String resourceId) {
		if (this.resources.containsKey(resourceId)) {
			return this.resources.get(resourceId);
		}
		return null;
	}
	
	protected String _getAttributeAsString(String resourceId, String attributeId, String defaultValue) {
		SwrveResource resource = this.getResource(resourceId);
		if (resource != null) {
			return resource.getAttributeAsString(attributeId, defaultValue);
		}
		return defaultValue;
	}
	
	protected int _getAttributeAsInt(String resourceId, String attributeId, int defaultValue) {
		SwrveResource resource = this.getResource(resourceId);
		if (resource != null) {
			return resource.getAttributeAsInt(attributeId, defaultValue);
		}
		return defaultValue;
	}
	
	protected float _getAttributeAsFloat(String resourceId, String attributeId, float defaultValue) {
		SwrveResource resource = this.getResource(resourceId);
		if (resource != null) {
			return resource.getAttributeAsFloat(attributeId, defaultValue);
		}
		return defaultValue;
	}
	
	protected boolean _getAttributeAsBoolean(String resourceId, String attributeId, boolean defaultValue) {
		SwrveResource resource = this.getResource(resourceId);
		if (resource != null) {
			return resource.getAttributeAsBoolean(attributeId, defaultValue);
		}
		return defaultValue;
	}

	/** 
	 * Public functions all wrapped in try/catch to avoid exceptions leaving the SDK and affecting the app itself.
	 */

	public void setResourcesFromJSON(JSONArray jsonResources) {
		try {
			_setResourcesFromJSON(jsonResources);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
	}
	
	public Map<String, SwrveResource> getResources() {
		try {
			return _getResources();
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return null;
	}
	
	public SwrveResource getResource(String resourceId) {
		try {
			return _getResource(resourceId); 
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return null;
	}
	
	public String getAttributeAsString(String resourceId, String attributeId, String defaultValue) {
		try {
			return _getAttributeAsString(resourceId, attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
	
	public int getAttributeAsInt(String resourceId, String attributeId, int defaultValue) {
		try {
			return _getAttributeAsInt(resourceId, attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
	
	public float getAttributeAsFloat(String resourceId, String attributeId, float defaultValue) {
		try {
			return _getAttributeAsFloat(resourceId, attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
	
	public boolean getAttributeAsBoolean(String resourceId, String attributeId, boolean defaultValue) {
		try {
			return _getAttributeAsBoolean(resourceId, attributeId, defaultValue);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception thrown in Swrve SDK", e);
		}
		return defaultValue;
	}
}
