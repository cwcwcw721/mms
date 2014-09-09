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

import java.lang.ref.WeakReference;

import android.content.Context;

import com.swrve.sdk.ISwrveEventListener;
import com.swrve.sdk.SwrveBase;

public class SwrveEventListener implements ISwrveEventListener  {
	
	private WeakReference<SwrveBase<?, ?>> talk;
	private ISwrveMessageListener messageListener;
	
	public SwrveEventListener(SwrveBase<?, ?> talk, ISwrveMessageListener messageListener) {
		this.talk = new WeakReference<SwrveBase<?, ?>>(talk);
		this.messageListener = messageListener;
	}

	@Override
	public void onEvent(String eventName) {
		if (messageListener != null) {
			SwrveBase<?, ?> talkRef = talk.get();
			if(talkRef != null && talkRef.getConfig().isTalkEnabled()) {
				SwrveOrientation deviceOrientation = SwrveOrientation.Both;
				Context ctx = talkRef.getContext();
				if (ctx != null) {
					deviceOrientation = SwrveOrientation.parse(ctx.getResources().getConfiguration().orientation);
				}
				SwrveMessage message = talkRef.getMessageForEvent(eventName, deviceOrientation);
				if(message != null) {
					messageListener.onMessage(message, true);
				}
			}
		}
	}
}
