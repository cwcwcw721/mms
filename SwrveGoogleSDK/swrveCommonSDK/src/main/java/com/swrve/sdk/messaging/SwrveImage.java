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

/**
 * Background message image.
 */
public class SwrveImage extends SwrveWidget {
	protected String file; // The cached path of the image file on disk

	public SwrveImage() {
	}
	
	public SwrveImage(SwrveMessage message, JSONObject imageData) throws JSONException {
		setPosition(getCenterFrom(imageData));
		setSize(getSizeFrom(imageData));
		setFile(imageData.getJSONObject("image").getString("value"));
	}
	
	/**
	 * @return the cached path of the image file.
	 */
	public String getFile() {
		return file;
	}

	protected void setFile(String file) {
		this.file = file;
	}
}
