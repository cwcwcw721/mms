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

/**
 * Type of button actions supported by SwrveButton.
 */
public enum SwrveActionType {
	// Cancel the message display
	Dismiss,
	// Handle the custom action string associated with the button
	Custom,
	// Go to the url specified in the action string
	Install;

	/**
	 * Convert from string to SwrveActionType.
	 * @param type
	 * 	      string to parse as SwrveActionType
	 * @return SwrveActionType
	 *         Parsed SwrveActionType value. If the type is unknown
	 * 		   it will default to custom.
	 */
	public static SwrveActionType parse(String type) {
		if(type.equalsIgnoreCase("INSTALL")) {
			return Install;
		} else if(type.equalsIgnoreCase("DISMISS")) {
			return Dismiss;
		}
		return Custom;
	}
}
