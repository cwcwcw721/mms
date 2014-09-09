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
package com.swrve.sdk.messaging;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Point;

/**
 * Base widget class.
 */
abstract class SwrveWidget {
	protected Point position; // The position of the button
	protected Point size; // The size of the button

	/**
	 * @return the position of the widget.
	 */
	public Point getPosition() {
		return position;
	}

	protected void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * @return the size of the element
	 */
	public Point getSize() {
		return size;
	}

	protected void setSize(Point size) {
		this.size = size;
	}
	
	protected static Point getSizeFrom(JSONObject data) throws JSONException {
		return new Point(data.getJSONObject("w").getInt("value"), data.getJSONObject("h").getInt("value"));
	}

	protected static Point getCenterFrom(JSONObject data) throws JSONException {
		return new Point(data.getJSONObject("x").getInt("value"), data.getJSONObject("y").getInt("value"));
	}
}
