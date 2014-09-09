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
 * Button with an associated action and click logic.
 */
public class SwrveButton extends SwrveWidget {
	// The name of this button
	protected String name;
	// The cached path of the button image on disk
	protected String image;
	// The custom action string for the button
	protected String action;
	// The message identifier associated with this button
	protected SwrveMessage message;
	// The id of target installation app
	protected int appId;
	// The action associated with this button
	protected SwrveActionType actionType;

	public SwrveButton() {
	}
	
	public SwrveButton(SwrveMessage message, JSONObject buttonData) throws JSONException {
		if(buttonData.has("name")) {
			setName(buttonData.getString("name"));
		}
		setPosition(getCenterFrom(buttonData));
		setSize(getSizeFrom(buttonData));
		setImage(buttonData.getJSONObject("image_up").getString("value"));
		setMessage(message);
		if(buttonData.has("game_id")) {
			String appIdStr = buttonData.getJSONObject("game_id").getString("value");
			if(appIdStr != null && !appIdStr.equals("")) {
				int appId = Integer.parseInt(appIdStr);
				setAppId(appId);
			}
		}
		setAction(buttonData.getJSONObject("action").getString("value"));
		setActionType(SwrveActionType.parse(buttonData.getJSONObject("type").getString("value")));
	}
	/**
	 * @return the name of the button.
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the cached path of the button image.
	 */
	public String getImage() {
		return image;
	}

	protected void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the button custom action.
	 */
	public String getAction() {
		return action;
	}

	protected void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the parent message.
	 */
	public SwrveMessage getMessage() {
		return message;
	}

	protected void setMessage(SwrveMessage message) {
		this.message = message;
	}

	/**
	 * @return the app id to install.
	 */
	public int getAppId() {
		return appId;
	}

	protected void setAppId(int appId) {
		this.appId = appId;
	}

	/**
	 * @return the button action type.
	 */
	public SwrveActionType getActionType() {
		return actionType;
	}

	protected void setActionType(SwrveActionType actionType) {
		this.actionType = actionType;
	}
}
