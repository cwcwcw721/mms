package com.swrve.sdk.messaging.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.swrve.sdk.messaging.SwrveMessage;

public class SwrveDialog extends Dialog {

	private SwrveMessageView innerView;
	private SwrveMessage message;
	private LayoutParams originalParams;
	
	private boolean dismissed = false;
	
	public SwrveDialog(Activity context, SwrveMessage message, SwrveMessageView innerView, int theme) {
		super(context, theme);
		this.message = message;
		this.innerView = innerView;
		this.originalParams = context.getWindow().getAttributes();
		setContentView(innerView);
		innerView.setContainerDialog(this);
	}
	
	public SwrveMessage getMessage() {
		return message;
	}
	
	public SwrveMessageView getInnerView() {
		return innerView;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// Remove the status bar from the activity
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
	}
	
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		goneAway();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		goneAway();
	}
	
	private void goneAway() {
		if (!dismissed) {
			dismissed = true;
			try {
				// Restore the window attributes 
				getWindow().setAttributes(originalParams);
			} catch(IllegalArgumentException exp) {
				// Dialog was not on assigned to a parent view
				exp.printStackTrace();
			}
		}
	}
}
