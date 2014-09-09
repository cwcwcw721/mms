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
package com.swrve.sdk.localstorage;

/**
 * Used internally to define a storage object that is capable of flushing.
 */
public interface IFlushableLocalStorage {
	public void flushEvents(IFastInsertLocalStorage externalStorage);
	public void flushCache(IFastInsertLocalStorage externalStorage);
	public void flushClickThrus(IFastInsertLocalStorage externalStorage);
}
