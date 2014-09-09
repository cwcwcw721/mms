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

import android.content.res.Configuration;

/**
 * Used for device orientation and specifying orientation filters.
 */
public enum SwrveOrientation {
	Portrait, Landscape, Both;

	/**
	 * Convert from Android orientation to SwrveOrientation.
	 * @param androidOrientation
	 *        Android device orientation.
	 * @return SwrveOrientation
	 */
	public static SwrveOrientation parse(int androidOrientation) {
		if (androidOrientation == Configuration.ORIENTATION_PORTRAIT) {
			return SwrveOrientation.Portrait;
		}

		return SwrveOrientation.Landscape;
	}

	/**
	 * Convert from String to SwrveOrientation.
	 * @param orientation
	 *        String device orientation or filter.
	 * @return SwrveOrientation
	 */
	public static SwrveOrientation parse(String orientation) {
		if (orientation.equalsIgnoreCase("portrait")) {
			return SwrveOrientation.Portrait;
		} else if (orientation.equalsIgnoreCase("both")) {
			return SwrveOrientation.Both;
		}

		return SwrveOrientation.Landscape;
	}
}
