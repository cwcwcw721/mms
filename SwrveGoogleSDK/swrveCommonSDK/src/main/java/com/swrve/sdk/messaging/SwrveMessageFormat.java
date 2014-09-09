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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Point;
import android.util.Log;

/**
 * Message representation with a given language, size and orientation.
 */
public class SwrveMessageFormat {
	protected static final String LOG_TAG = "SwrveMessagingSDK";
	
	// An array of SwrveButton objects
	protected List<SwrveButton> buttons;
	// An array of SwrveImage objects
	protected List<SwrveImage> images;
	// The name of the format
	protected String name;
	// The language for the format
	protected String language;
	// The scale for the format in the device
	protected float scale;
	// The size of the format
	protected Point size;
	// The parent message
	protected SwrveMessage message;
	// The orientation of the format
	protected SwrveOrientation orientation;

	public SwrveMessageFormat(SwrveMessage message) {
		this.message = message;
		this.buttons = new ArrayList<SwrveButton>();
		this.images = new ArrayList<SwrveImage>();
		this.scale = 1f;
	}

	/**
	 * @return the parent message of the format.
	 */
	public SwrveMessage getMessage() {
		return message;
	}
	
	/**
	 * @return list of buttons contained in the format.
	 */
	public List<SwrveButton> getButtons() {
		return buttons;
	}

	protected void setButtons(List<SwrveButton> buttons) {
		this.buttons = buttons;
	}

	/**
	 * @return list of images contained in the format.
	 */
	public List<SwrveImage> getImages() {
		return images;
	}

	protected void setImages(List<SwrveImage> images) {
		this.images = images;
	}

	/**
	 * @return the name of the format.
	 */
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the language of the format.
	 */
	public String getLanguage() {
		return language;
	}

	protected void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the size of the format.
	 */
	public Point getSize() {
		return size;
	}

	protected void setSize(Point size) {
		this.size = size;
	}

	/**
	 * @return the orientation of the format.
	 */
	public SwrveOrientation getOrientation() {
		return orientation;
	}

	protected void setOrientation(SwrveOrientation orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * @return the scale for the format on the device.
	 */
	public float getScale() {
		return scale;
	}
	
	protected void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * Load format from JSON data.
	 * 
	 * @param message
	 * @param messageFormatData
	 * @return SwrveMessageFormat new instance
	 * @throws JSONException
	 */
	public SwrveMessageFormat(SwrveMessage message, JSONObject messageFormatData) throws JSONException {
		this(message);

		setName(messageFormatData.getString("name"));
		setLanguage(messageFormatData.getString("language"));
		// Orientation
		if (messageFormatData.has("orientation")) {
			setOrientation(SwrveOrientation.parse(messageFormatData.getString("orientation")));
		}
		
		// Scale
		if (messageFormatData.has("scale")) {
			setScale(Float.parseFloat(messageFormatData.getString("scale")));
		}

		setSize(getSizeFrom(messageFormatData.getJSONObject("size")));

		Log.i(LOG_TAG, "Format " + getName() + " Size: " + size.x + "x" + size.y + " scale " + scale);
		JSONArray jsonButtons = messageFormatData.getJSONArray("buttons");
		for (int i = 0, j = jsonButtons.length(); i < j; i++) {
			SwrveButton button = new SwrveButton(message, jsonButtons.getJSONObject(i));
			getButtons().add(button);
		}

		JSONArray jsonImages = messageFormatData.getJSONArray("images");
		for (int ii = 0, ji = jsonImages.length(); ii < ji; ii++) {
			SwrveImage image = new SwrveImage(message, jsonImages.getJSONObject(ii));
			getImages().add(image);
		}
	}
	
	protected static Point getSizeFrom(JSONObject data) throws JSONException {
		return new Point(data.getJSONObject("w").getInt("value"), data.getJSONObject("h").getInt("value"));
	}
}
