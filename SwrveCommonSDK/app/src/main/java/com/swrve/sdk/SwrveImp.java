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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.swrve.sdk.config.SwrveConfigBase;
import com.swrve.sdk.localstorage.ILocalStorage;
import com.swrve.sdk.localstorage.MemoryCachedLocalStorage;
import com.swrve.sdk.localstorage.MemoryLocalStorage;
import com.swrve.sdk.localstorage.SQLiteLocalStorage;
import com.swrve.sdk.messaging.*;
import com.swrve.sdk.messaging.view.SwrveDialog;
import com.swrve.sdk.messaging.view.SwrveMessageView;
import com.swrve.sdk.messaging.view.SwrveMessageViewFactory;
import com.swrve.sdk.qa.SwrveQAUser;
import com.swrve.sdk.rest.*;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

abstract class SwrveImp<T, C extends SwrveConfigBase> {
	protected static String version = "3.1";
	protected static final String PLATFORM = "Android ";

	protected static final String CAMPAIGN_CATEGORY = "CMCC";
	protected static final String CAMPAIGN_SETTINGS_CATEGORY = "SwrveCampaignSettings";
	protected static final String APP_VERSION_CATEGORY = "AppVersion";
	protected final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss ZZZZ", Locale.US);
	protected final SimpleDateFormat installTimeFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
	protected static final String EMPTY_STRING = "";
	protected static final int CAMPAIGN_ENDPOINT_VERSION = 4;
	protected static final String TEMPLATE_VERSION = "1";
	protected static int DEFAULT_DELAY_FIRST_MESSAGE = 150;
	protected static long DEFAULT_MAX_SHOWS = 99999;
	protected static int DEFAULT_MIN_DELAY = 55;
	protected static long MESSAGE_REAPPEAR_TIMEOUT = 1500;

	protected static final String CAMPAIGNS_AND_RESOURCES_ACTION = "/api/1/user_resources_and_campaigns";
	protected static final String USER_RESOURCES_DIFF_ACTION = "/api/1/user_resources_diff";
	protected static final String APP_LAUNCH_ACTION = "/1/app_launch";
	protected static final String CLICK_THRU_ACTION = "/1/click_thru";
	protected static final String BATCH_EVENTS_ACTION = "/1/batch";

	protected static final String RESOURCES_CACHE_CATEGORY = "srcngt";
	protected static final String RESOURCES_DIFF_CACHE_CATEGORY = "rsdfngt";

	protected static final String SDK_PREFS_NAME = "swrve_prefs";
	protected static final String EMPTY_JSON_ARRAY = "[]";
	protected static final int SHUTDOWN_TIMEOUT_SECONDS = 5;

	protected static final String SWRVE_DEVICE_NAME = "swrve.device_name";
	protected static final String SWRVE_OS = "swrve.os";
	protected static final String SWRVE_OS_VERSION = "swrve.os_version";
	protected static final String SWRVE_DEVICE_WIDTH = "swrve.device_width";
	protected static final String SWRVE_DEVICE_HEIGHT = "swrve.device_height";
	protected static final String SWRVE_DEVICE_DPI = "swrve.device_dpi";
	protected static final String SWRVE_ANDROID_DEVICE_XDPI = "swrve.android_device_xdpi";
	protected static final String SWRVE_ANDROID_DEVICE_YDPI = "swrve.android_device_ydpi";
	protected static final String SWRVE_LANGUAGE = "swrve.language";
	protected static final String SWRVE_UTC_OFFSET_SECONDS = "swrve.utc_offset_seconds";
	protected static final String SWRVE_TIMEZONE_NAME = "swrve.timezone_name";
	protected static final String SWRVE_SDK_VERSION = "swrve.sdk_version";
	protected static final String SWRVE_APP_STORE = "swrve.app_store";
	protected static final String SWRVE_INSTALL_DATE = "swrve.install_date";

	protected static final int SWRVE_DEFAULT_CAMPAIGN_RESOURCES_FLUSH_FREQUENCY = 60000;
	protected static final int SWRVE_DEFAULT_CAMPAIGN_RESOURCES_FLUSH_REFRESH_DELAY = 5000;
	protected static final String SWRVE_AUTOSHOW_AT_SESSION_START_TRIGGER = "Swrve.Messages.showAtSessionStart";

	protected static final String LOG_TAG = "SwrveSDK";

	protected static SwrveMessage messageDisplayed;
	protected static long lastMessageDestroyed;

	protected WeakReference<Context> context;
	protected WeakReference<Activity> activityContext;
	protected WeakReference<SwrveDialog> currentDialog;
	protected String appVersion;
	protected int appId;
	protected String apiKey;
	protected String userId;
	protected String sessionToken;
	protected String linkToken;
	protected String language;
	protected C config;
	protected ISwrveEventListener eventListener;
	protected ISwrveMessageListener messageListener;
	protected ISwrveInstallButtonListener installButtonListener;
	protected ISwrveCustomButtonListener customButtonListener;
	protected ISwrveDialogListener dialogListener;
	protected ISwrveResourcesListener resourcesListener;
	
	protected ExecutorService autoShowExecutor;
	protected String userInstallTime;
	protected String lastProcessedMessage;
	protected AtomicInteger bindCounter; 

	protected PendingState appLaunchPendingState;
	protected PendingState clickThruPendingState;
	protected AtomicLong installTime;
	protected CountDownLatch installTimeLatch;
	protected long newSessionInterval;
	protected long lastSessionTick;
	protected boolean destroyed;
	protected MemoryCachedLocalStorage cachedLocalStorage;
	protected IRESTClient restClient;
	protected ExecutorService storageExecutor;
	protected ExecutorService restClientExecutor;
	protected ScheduledThreadPoolExecutor campaignsAndResourcesExecutor;
	protected SwrveResourceManager resourceManager;

	protected List<SwrveCampaign> campaigns;
	protected Set<String> assetsOnDisk;
	protected boolean assetsCurrentlyDownloading;
	protected SparseArray<String> appStoreURLs;
	protected boolean autoShowMessagesEnabled;
	protected Integer campaignsAndResourcesFlushFrequency;
	protected Integer campaignsAndResourcesFlushRefreshDelay;
	protected String campaignsAndResourcesLastETag;
	protected Date campaignsAndResourcesLastRefreshed;
	protected boolean campaignsAndResourcesInitialized = false;
	protected boolean eventsWereSent = false;

	protected String cdnRoot = "http://content-cdn.swrve.com/messaging/message_image/";
	protected boolean initialised = false;
	protected boolean mustCleanInstance;
	protected Date initialisedTime;
	protected Date showMessagesAfterLaunch;
	protected Date showMessagesAfterDelay;
	protected long messagesLeftToShow;
	protected int minDelayBetweenMessage;

	protected File cacheDir;

	protected int device_width;
	protected int device_height;
	protected float device_dpi;
	protected float android_device_xdpi;
	protected float android_device_ydpi;

	protected SwrveQAUser qaUser;

	public SwrveImp() {
		appLaunchPendingState = new PendingState(false, false);
		clickThruPendingState = new PendingState(false, false);
		installTime = new AtomicLong();
		installTimeLatch = new CountDownLatch(1);
		destroyed = false;
		autoShowExecutor = Executors.newSingleThreadExecutor();
		bindCounter = new AtomicInteger();
		autoShowMessagesEnabled = true;
		assetsOnDisk = new HashSet<String>();
		assetsCurrentlyDownloading = false;
	}
	
	protected void showPreviousMessage() {
	    if (config.isTalkEnabled()) {
	        // Re-launch message that was displayed before
	        if (messageDisplayed != null && messageListener != null) {
                long currentTime = getNow().getTime();
                if (currentTime < (lastMessageDestroyed + MESSAGE_REAPPEAR_TIMEOUT)) {
                    messageDisplayed.setMessageController((SwrveBase<?, ?>) this);
                    messageListener.onMessage(messageDisplayed, false);
                }
                messageDisplayed = null;
            }
	    }
	}
	
	protected void queueSessionStart() {
		queueEvent("session_start", null, null);
	}
	
	protected Context bindToContext(Context context) {
		bindCounter.incrementAndGet();
    	// Obtain activity context if possible
		Activity activityContext = null;
		if (context instanceof Activity) {
			activityContext = (Activity) context;
			// Attach to the application context
			this.context = new WeakReference<Context>(context.getApplicationContext());
			this.activityContext = new WeakReference<Activity>(activityContext);
		} else {
			this.context = new WeakReference<Context>(context);
		}
		
		return this.context.get();
    }

	protected Context resolveContext(Context context) {
		// Obtain the activity context if possible
		Context ctx = null;
		Activity activityContext = null;
		if (context instanceof Activity) {
			activityContext = (Activity)context;
			// Attach to the activity and application context
			ctx = context.getApplicationContext();
			this.activityContext = new WeakReference<Activity>(activityContext);
		} else {
			ctx = context;
		}
		this.context = new WeakReference<Context>(ctx);
		return ctx;
	}

	protected String getUniqueUserId(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SDK_PREFS_NAME, 0);
		String newUserId = settings.getString("userId", null);
		if (SwrveHelper.IsNullOrEmpty(newUserId)) {
			String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			newUserId = SwrveHelper.md5(androidId);
			// Blacklisted ANDROID_ID
			if (SwrveHelper.IsNullOrEmpty(newUserId) || (newUserId != null && newUserId.equals("94c24a0bc4fb8d342f0db892a5d39b4a"))) { 
				// Create a random UUID
				newUserId = UUID.randomUUID().toString();
			}

			// Save new user id
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("userId", newUserId);
			editor.commit();
		}
		
		return newUserId;
	}

	protected void checkUserId(String userId) {
		if (userId != null && userId.matches("^.*\\..*@\\w+$")) {
			Log.w(LOG_TAG, "Please double-check your user id. It seems to be Object.toString(): " + userId);
		}
	}

	/**
	 * Send user identifiers to Swrve
	 */
	protected void sendIdentifiers() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		JSONObject identifiers = getIdentifiers();
		parameters.put("identifiers", identifiers);
		queueEvent("identifiers", parameters, null);
	}

	private JSONObject getIdentifiers() {
		JSONObject identifiers = new JSONObject();

		Context contextRef = context.get();
		if (contextRef != null) {
			String androidId = Secure.getString(contextRef.getContentResolver(), Secure.ANDROID_ID);

			try {
				identifiers.put("android_id", androidId);
			} catch (JSONException e) {
			}
		}

		return identifiers;
	}

	/**
	 * Internal function to add a Swrve.iap event to the event queue.
	 */
	protected void _iap(int quantity, String productId, double productPrice, String currency, SwrveIAPRewards rewards, String receipt, String receiptSignature, String paymentProvider) {
		if (_iap_check_parameters(quantity, productId, productPrice, currency, paymentProvider)) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("local_currency", currency);
			parameters.put("cost", productPrice);
			parameters.put("product_id", productId);
			parameters.put("quantity", quantity);
			parameters.put("app_store", paymentProvider);
			parameters.put("rewards", rewards.getRewardsJSON());

			if (!SwrveHelper.IsNullOrEmpty(receipt)) {
				parameters.put("receipt", receipt);
			}

			if (!SwrveHelper.IsNullOrEmpty(receiptSignature)) {
				parameters.put("receipt_signature", receiptSignature);
			}

			queueEvent("iap", parameters, null);
			
			if (config.isAutoDownloadCampaingsAndResources()) {
				checkForCampaignAndResourcesUpdates(false);
			}
		}
	}

	protected boolean _iap_check_parameters(int quantity, String productId, double productPrice, String currency, String paymentProvider) throws IllegalArgumentException {
		// Strings cannot be null or empty
		if (SwrveHelper.IsNullOrEmpty(productId)) {
			Log.e(LOG_TAG, "IAP event illegal argument: productId cannot be empty");
			return false;
		}
		if (SwrveHelper.IsNullOrEmpty(currency)) {
			Log.e(LOG_TAG, "IAP event illegal argument: currency cannot be empty");
			return false;
		}
		if (SwrveHelper.IsNullOrEmpty(paymentProvider)) {
			Log.e(LOG_TAG, "IAP event illegal argument: paymentProvider cannot be empty");
			return false;
		}
		if (quantity <= 0) {
			Log.e(LOG_TAG, "IAP event illegal argument: quantity must be greater than zero");
			return false;
		}
		if (productPrice < 0) {
			Log.e(LOG_TAG, "IAP event illegal argument: productPrice must be greater than or equal to zero");
			return false;
		}
		return true;
	}

	protected void openLocalStorageConnection() {
		try {
			ILocalStorage newlocalStorage = createLocalStorage();
			cachedLocalStorage.setSecondaryStorage(newlocalStorage);
		} catch (Exception exp) {
			Log.e(LOG_TAG, "Error opening database", exp);
		}
	}

	protected ILocalStorage createLocalStorage() {
		return new SQLiteLocalStorage(context.get(), config.getDbName(), config.getMaxSqliteDbSize());
	}

	protected IRESTClient createRESTClient() {
		return new RESTClient();
	}

	protected MemoryCachedLocalStorage createCachedLocalStorage() {
		return new MemoryCachedLocalStorage(new MemoryLocalStorage(), null);
	}

	protected ExecutorService createStorageExecutor() {
		return Executors.newSingleThreadExecutor();
	}

	protected ExecutorService createRESTClientExecutor() {
		return Executors.newSingleThreadExecutor();
	}
	
	protected String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return model;
		} else {
			return manufacturer + " " + model;
		}
	}

	protected void processUserResourcesDiffData(String resourcesAsJSON, final ISwrveUserResourcesDiffListener listener) {
		// Parse raw response
		JSONArray jsonResourcesDiff;
		try {
			jsonResourcesDiff = new JSONArray(resourcesAsJSON);
			// Convert to map
			Map<String, Map<String, String>> mapOldResources = new HashMap<String, Map<String, String>>();
			Map<String, Map<String, String>> mapNewResources = new HashMap<String, Map<String, String>>();
			for (int i = 0, j = jsonResourcesDiff.length(); i < j; i++) {
				Map<String, String> mapOldResourceValues = new HashMap<String, String>();
				Map<String, String> mapNewResourceValues = new HashMap<String, String>();
				JSONObject resourceJSON = jsonResourcesDiff.getJSONObject(i);
				String uid = resourceJSON.getString("uid");
				JSONObject resourceDiffsJSON = resourceJSON.getJSONObject("diff");
				@SuppressWarnings("unchecked")
				Iterator<String> it = resourceDiffsJSON.keys();
				while (it.hasNext()) {
					String key = it.next();
					mapOldResourceValues.put(key, resourceDiffsJSON.getJSONObject(key).getString("old"));
					mapNewResourceValues.put(key, resourceDiffsJSON.getJSONObject(key).getString("new"));
				}
				mapOldResources.put(uid, mapOldResourceValues);
				mapNewResources.put(uid, mapNewResourceValues);
			}
			// Execute callback (NOTE: Executed in same thread!)
			listener.onUserResourcesDiffSuccess(mapOldResources, mapNewResources, resourcesAsJSON);
		} catch (Exception exp) {
			// Launch exception
			listener.onUserResourcesDiffError(exp);
		}
	}

	protected void notifySentAppLaunch() {
		synchronized (appLaunchPendingState) {
			appLaunchPendingState.setPending(false);
			appLaunchPendingState.setSending(false);
		}
	}

	protected void notifySentAppLaunchError() {
		synchronized (appLaunchPendingState) {
			appLaunchPendingState.setPending(true);
			appLaunchPendingState.setSending(false);
		}
	}

	protected void notifySentClickThru() {
		synchronized (clickThruPendingState) {
			clickThruPendingState.setPending(false);
			clickThruPendingState.setSending(false);
		}
	}

	protected void notifySentClickThruError() {
		synchronized (clickThruPendingState) {
			clickThruPendingState.setPending(true);
			clickThruPendingState.setSending(false);
		}
	}

	private static String INSTALL_TIME_CATEGORY = "SwrveSDK.installTime";

	protected long getInstallTime() {
		long installTime = (new Date()).getTime();
		try {
			// Try to get install time from storage
			String installTimeRaw = getSavedInstallTime();
			if (!SwrveHelper.IsNullOrEmpty(installTimeRaw)) {
				installTime = Long.parseLong(installTimeRaw);
			} else {
				// Save to memory and secondary storage
				cachedLocalStorage.setAndFlushSharedEntry(INSTALL_TIME_CATEGORY, String.valueOf(installTime));
			}
		} catch (Exception exp) {
			Log.e(LOG_TAG, "Could not get or save install time", exp);
		}

		return installTime;
	}

	protected String getSavedInstallTime() {
		return cachedLocalStorage.getSharedCacheEntry(INSTALL_TIME_CATEGORY);
	}

	protected long getOrWaitForInstallTime() {
		try {
			installTimeLatch.await();
			return this.installTime.get();
		} catch (Exception e) {
			return (new Date()).getTime(); // Assume install was now.
		}
	}

	private class QueueEventRunnable implements Runnable {
		private String eventType;
		private Map<String, Object> parameters;
		private Map<String, String> payload;

		public QueueEventRunnable(String eventType, Map<String, Object> parameters, Map<String, String> payload) {
			this.eventType = eventType;
			this.parameters = parameters;
			this.payload = payload;
		}

		@Override
		public void run() {
			try {
				String eventString = EventHelper.eventAsJSON(eventType, parameters, payload, cachedLocalStorage);
				parameters = null;
				payload = null;
				cachedLocalStorage.addEvent(eventString);
				Log.i(LOG_TAG, eventType + " event queued");
			} catch (JSONException je) {
				Log.e(LOG_TAG, "Parameter or payload data not encodable as JSON", je);
			} catch (Exception se) {
				Log.e(LOG_TAG, "Unable to insert into local storage", se);
			}
			taskCompleted();
		}
	}

	protected void queueEvent(String eventType, Map<String, Object> parameters, Map<String, String> payload) {
		try {
			storageExecutorExecute(new QueueEventRunnable(eventType, parameters, payload));

			if (eventListener != null) {
				eventListener.onEvent(EventHelper.getEventName(eventType, parameters));
			}
		} catch (Exception exp) {
			Log.e(LOG_TAG, "Unable to queue event", exp);
		}
	}

	protected void userUpdate(JSONObject attributes) {
		if (attributes != null && attributes.length() != 0) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("attributes", attributes);
			queueEvent("user", parameters, null);
		}
	}

	protected void postBatchRequest(C config, String postData, final IPostBatchRequestListener listener) {
		restClient.post(config.getEventsUrl() + BATCH_EVENTS_ACTION, postData, new IRESTResponseListener() {
			@Override
			public void onResponse(RESTResponse response) {
				if (SwrveHelper.userErrorResponseCode(response.responseCode)) {
					Log.e(LOG_TAG, "Error sending events to Swrve: " + response.responseBody);
				} else if (SwrveHelper.successResponseCode(response.responseCode)) {
					Log.i(LOG_TAG, "Events sent to Swrve");
				}

				// Do not resend if we got a response body back from the server
				// (2XX, 4XX)
				listener.onResponse(response.responseBody != null);
			}

			@Override
			public void onException(Exception exp) {
			}
		});
	}

	protected void trySendAppLaunch() {
		if (userId != null) {
			try {
				boolean sendAppLaunch = false;
				synchronized (appLaunchPendingState) {
					sendAppLaunch = (appLaunchPendingState.isPending() && !appLaunchPendingState.isSending());
					if (sendAppLaunch) {
						appLaunchPendingState.setSending(true);
					}
				}
				if (sendAppLaunch) {
					Log.i(LOG_TAG, "Sending app launch");
					restClientExecutorExecute(new Runnable() {
						@Override
						public void run() {
							Map<String, String> params = new HashMap<String, String>();
							params.put("api_key", apiKey);
							params.put("user", userId);
							params.put("app_version", appVersion);
							params.put("link_token", linkToken);
							try {
								restClient.get(config.getLinkUrl() + APP_LAUNCH_ACTION, params, new IRESTResponseListener() {
									@Override
									public void onResponse(RESTResponse response) {
										if (response.responseCode == HttpStatus.SC_OK) {
											Log.i(LOG_TAG, "App launch succesfully sent");
										}
										if (SwrveHelper.doNotResendResponseCode(response.responseCode)) {
											Log.e(LOG_TAG, "App launch error. Scheduled resend");
											notifySentAppLaunchError();
										} else {
											// The data was successfully sent or
											// there was not a network error, do
											// not send it again
											notifySentAppLaunch();
										}
									}

									@Override
									public void onException(Exception exp) {
										notifySentAppLaunchError();
									}
								});
								appLaunchPendingState.setSending(false);
							} catch (Exception exp) {
								notifySentAppLaunchError();
							}

							taskCompleted();
						}
					});
				}
			} catch (Exception exp) {
				Log.e(LOG_TAG, "Could not send app launch", exp);
			}
		}
	}

	protected void trySendClickThru() {
		if (userId != null) {
			try {
				boolean sendClickThru = false;
				synchronized (clickThruPendingState) {
					sendClickThru = (clickThruPendingState.isPending() && !clickThruPendingState.isSending());
					if (sendClickThru) {
						clickThruPendingState.setSending(true);
					}
				}
				if (sendClickThru) {
					final Map<ILocalStorage, Map<Long, Entry<Integer, String>>> combinedClickThrus = cachedLocalStorage.getCombinedFirstNClickThrus(config.getMaxClickThrusPerFlush());
					if (!combinedClickThrus.isEmpty()) {
						Log.i(LOG_TAG, "Sending click thru");
						restClientExecutorExecute(new Runnable() {
							@Override
							public void run() {
								sendClickThruImp(combinedClickThrus);
								taskCompleted();
							}
						});
					}
				}
			} catch (Exception exp) {
				Log.e(LOG_TAG, "Could not send click thru", exp);
			}
		}
	}

	private void sendClickThruImp(Map<ILocalStorage, Map<Long, Entry<Integer, String>>> combinedClickThrus) {
		Iterator<ILocalStorage> storageIt = combinedClickThrus.keySet().iterator();
		while (storageIt.hasNext()) {
			final ILocalStorage storage = storageIt.next();
			Map<Long, Entry<Integer, String>> clickThrus = combinedClickThrus.get(storage);
			Iterator<Long> clickThruIt = clickThrus.keySet().iterator();
			while (clickThruIt.hasNext()) {
				final Long clickThruId = clickThruIt.next();
				final Entry<Integer, String> clickThru = clickThrus.get(clickThruId);
				Map<String, String> params = new HashMap<String, String>();
				params.put("api_key", apiKey);
				params.put("user", userId);
				params.put("link_token", linkToken);
				params.put("destination", clickThru.getKey().toString());
				params.put("source", clickThru.getValue());
				try {
					restClient.get(config.getLinkUrl() + CLICK_THRU_ACTION, params, new IRESTResponseListener() {
						@Override
						public void onResponse(RESTResponse response) {
							if (response.responseCode == HttpStatus.SC_OK) {
								Log.i(LOG_TAG, "Click thru succesfully sent");
							}
							if (SwrveHelper.doNotResendResponseCode(response.responseCode)) {
								Log.e(LOG_TAG, "Click thru error. Scheduled resend");
								notifySentClickThruError();
							} else {
								// The data was successfully sent or there was not a network error
								// do not send it again
								notifySentClickThru();
								storage.removeClickThrusById(clickThruId);
								Map<String, String> payload = new HashMap<String, String>();
								payload.put("destination", clickThru.getKey().toString());
								payload.put("source", clickThru.getValue());
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("name", "Swrve.Messages.click_thru");
								queueEvent("event", parameters, payload);
							}
						}

						@Override
						public void onException(Exception exp) {
							notifySentClickThruError();
						}
					});
					clickThruPendingState.setSending(false);
				} catch (Exception exp) {
				}
			}
		}
	}
	
	protected boolean restClientExecutorExecute(Runnable runnable) {
		try {
			if (restClientExecutor.isShutdown()) {
				Log.i(LOG_TAG, "Trying to schedule a rest execution while shutdown");
			} else {
				restClientExecutor.execute(SwrveRunnables.withoutExceptions(runnable));
				return true;
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error while scheduling a rest execution", e);
		}
		return false;
	}

	protected boolean storageExecutorExecute(Runnable runnable) {
		try {
			if (storageExecutor.isShutdown()) {
				Log.i(LOG_TAG, "Trying to schedule a storage execution while shutdown");
			} else {
				storageExecutor.execute(SwrveRunnables.withoutExceptions(runnable));
				return true;
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error while scheduling a storage execution", e);
		}
		return false;
	}

	protected long getSessionTime() {
		return getNow().getTime();
	}

	protected void generateNewSessionInterval() {
		lastSessionTick = getSessionTime() + newSessionInterval;
	}

	protected void taskCompleted() {
		// Do nothing
	}

	protected void getDeviceInfo(Context context) {
		try {
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			int width = display.getWidth();
			int height = display.getHeight();
			display.getMetrics(metrics);
			float xdpi = metrics.xdpi;
			float ydpi = metrics.ydpi;
			// Always use portrait dimensions (i.e. width < height)
			if (width > height) {
				int tmp = width;
				width = height;
				height = tmp;
				float tmpdpi = xdpi;
				xdpi = ydpi;
				ydpi = tmpdpi;
			}

			// Set device info
			this.device_width = width;
			this.device_height = height;
			this.device_dpi = metrics.densityDpi;
			this.android_device_xdpi = xdpi;
			this.android_device_ydpi = ydpi;
		} catch (Exception exp) {
			Log.e(LOG_TAG, "Get device screen info failed", exp);
		}
	}

	protected void findCacheFolder(Context context) {
		cacheDir = config.getCacheDir();
		if (cacheDir == null) {
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public Date getNow() {
		return new Date();
	}

	protected boolean hasShowTooManyMessagesAlready() {
		return (messagesLeftToShow <= 0);
	}

	protected boolean isTooSoonToShowMessageAfterLaunch(Date now) {
		return now.before(showMessagesAfterLaunch);
	}

	protected boolean isTooSoonToShowMessageAfterDelay(Date now) {
		if (showMessagesAfterDelay == null) {
			return false;
		}
		return now.before(showMessagesAfterDelay);
	}

	protected void saveCampaignsInCache(final String campaignContent) {
		storageExecutorExecute(new Runnable() {
			@Override
			public void run() {
				MemoryCachedLocalStorage cachedStorage = cachedLocalStorage;
				cachedStorage.setAndFlushSecureSharedEntryForUser(userId, CAMPAIGN_CATEGORY, campaignContent, getUniqueKey());
				Log.i(LOG_TAG, "Saved campaigns in cache.");
				taskCompleted();
			}
		});
	}

	protected void autoShowMessages() {		
		// Don't do anything if we've already shown a message or if its too long after session start
		if (!autoShowMessagesEnabled) {
			return;
		}
		
		// Only execute if at least 1 call to the /user_resources_and_campaigns api endpoint has been completed
		// And ensure all assets have been downloaded
		if (!campaignsAndResourcesInitialized || campaigns == null) {
			return;
		}

		for (final SwrveCampaign campaign : campaigns) {
			final SwrveBase<T, C> swrve = (SwrveBase<T, C>) this;
			if (campaign.hasMessageForEvent(SWRVE_AUTOSHOW_AT_SESSION_START_TRIGGER)) {
				synchronized(this) {
					if (autoShowMessagesEnabled && activityContext != null) {
						Activity activity = activityContext.get();
						if (activity != null) {
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									try {
										if(messageListener != null) {
											SwrveMessage message = swrve.getMessageForEvent(SWRVE_AUTOSHOW_AT_SESSION_START_TRIGGER);
											if(message != null && message.supportsOrientation(getDeviceOrientation())) {
												messageListener.onMessage(message, true);
												autoShowMessagesEnabled = false;
											}
										}
									} catch(Exception exp) {
										Log.e(LOG_TAG, "Could not launch campaign automatically");
									}
								}
							});
						}
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Ensure that after SWRVE_DEFAULT_AUTOSHOW_MESSAGES_MAX_DELAY autoshow is disabled
	 */
	protected void disableAutoShowAfterDelay() {	    
		ScheduledExecutorService timedService = Executors.newSingleThreadScheduledExecutor();
		timedService.schedule(new Runnable() {
			@Override
			public void run() {
				autoShowMessagesEnabled = false;
			}
		}, config.getAutoShowMessagesMaxDelay() , TimeUnit.MILLISECONDS);
	}

	@SuppressLint("UseSparseArrays")
	protected void loadCampaignsFromJSON(JSONObject json) {
		if (json == null) {
			Log.i(LOG_TAG, "NULL JSON for campaigns, aborting load.");
			return;
		}

	    if (json.length() == 0) {
	        Log.i(LOG_TAG, "Campaign JSON empty, no campaigns downloaded");
	        campaigns.clear();
	        return;
	    }		
		
		Log.i(LOG_TAG, "Campaign JSON data: " + json);

		try {
			// Check if schema has a version number
			if (!json.has("version")) {
				return;
			}
			// Version check
			String version = json.getString("version");
			if (!version.equals(TEMPLATE_VERSION)) {
				Log.i(LOG_TAG, "Campaign JSON has the wrong version. No campaigns loaded.");
				return;
			}

			// CDN
			this.cdnRoot = json.getString("cdn_root");
			Log.i(LOG_TAG, "CDN URL " + this.cdnRoot);

			// Game Data
			JSONObject gamesData = json.getJSONObject("game_data");
			if (gamesData != null) {
				@SuppressWarnings("unchecked")
				Iterator<String> gamesDataIt = gamesData.keys();
				while (gamesDataIt.hasNext()) {
					String appId = (String) gamesDataIt.next();
					JSONObject gameData = gamesData.getJSONObject(appId);
					if (gameData.has("app_store_url")) {
						String url = gameData.getString("app_store_url");
						this.appStoreURLs.put(Integer.parseInt(appId), url);
						if (SwrveHelper.IsNullOrEmpty(url)) {
							Log.e(LOG_TAG, "App store link " + appId + " is empty!");
						} else {
							Log.i(LOG_TAG, "App store Link " + appId + ": " + url);
						}
					}
				}
			}

			JSONObject rules = json.getJSONObject("rules");
			int delay = (rules.has("delay_first_message")) ? rules.getInt("delay_first_message") : DEFAULT_DELAY_FIRST_MESSAGE;
			long maxShows = (rules.has("max_messages_per_session")) ? rules.getLong("max_messages_per_session") : DEFAULT_MAX_SHOWS;
			int minDelay = (rules.has("min_delay_between_messages")) ? rules.getInt("min_delay_between_messages") : DEFAULT_MIN_DELAY;

			Date now = getNow();
			this.showMessagesAfterLaunch = SwrveHelper.addTimeInterval(initialisedTime, delay, Calendar.SECOND);
			this.minDelayBetweenMessage = minDelay;
			this.messagesLeftToShow = maxShows;

			Log.i(LOG_TAG, "Game rules OK: Delay Seconds: " + delay + " Max shows: " + maxShows);
			Log.i(LOG_TAG, "Time is " + now.toString() + " show messages after " + this.showMessagesAfterLaunch.toString());

			Map<Integer, String> campaignsDownloaded = null;

			// QA
			if (json.has("qa")) {
				JSONObject jsonQa = json.getJSONObject("qa");
				campaignsDownloaded = new HashMap<Integer, String>();
				Log.i(LOG_TAG, "You are a QA user!");
				// Load QA user settings
				qaUser = new SwrveQAUser((SwrveBase<T, C>) this, jsonQa);
				qaUser.bindToServices();

				if (jsonQa.has("campaigns")) {
					JSONArray jsonQaCampaigns = jsonQa.getJSONArray("campaigns");
					for (int i = 0; i < jsonQaCampaigns.length(); i++) {
						JSONObject jsonQaCampaign = jsonQaCampaigns.getJSONObject(i);
						int campaignId = jsonQaCampaign.getInt("id");
						String campaignReason = jsonQaCampaign.getString("reason");

						Log.i(LOG_TAG, "Campaign " + campaignId + " not downloaded because: " + campaignReason);

						// Add campaign for QA purposes
						campaignsDownloaded.put(campaignId, campaignReason);
					}
				}
			}

			JSONArray jsonCampaigns = json.getJSONArray("campaigns");			
			List<Integer> newCampaignIds = new ArrayList<Integer>();
			
			// Remove any campaigns that aren't in the new list
			// We do this before updating campaigns and adding new campaigns to ensure
			// there isn't a gap where no campaigns are available while reloading
			for (int i = 0, j = jsonCampaigns.length(); i < j; i++) {
				JSONObject campaignData = jsonCampaigns.getJSONObject(i);				
				newCampaignIds.add(campaignData.getInt("id"));
			}
			
			for (int i = campaigns.size() - 1; i >= 0; i--) {
				if (!newCampaignIds.contains(campaigns.get(i).getId())) {
					campaigns.remove(i);
				}
			}
			
			// Load existing campaign settings
			JSONObject jsonSettings = new JSONObject();
			if (qaUser == null || !qaUser.isResetDevice()) {
				// Load campaign data
				String serializedSettings = cachedLocalStorage.getCacheEntryForUser(userId, CAMPAIGN_SETTINGS_CATEGORY);
				if (!SwrveHelper.IsNullOrEmpty(serializedSettings)) {
					jsonSettings = new JSONObject(serializedSettings);
				}
			}
			
			List<SwrveCampaign> newCampaigns = new ArrayList<SwrveCampaign>();
			Set<String> assetsQueue = new HashSet<String>();
			for (int i = 0, j = jsonCampaigns.length(); i < j; i++) {
				JSONObject campaignData = jsonCampaigns.getJSONObject(i);
				// Load campaign and get assets to be loaded
				Set<String> campaignAssetsQueue = new HashSet<String>();
				SwrveCampaign campaign = loadCampaignFromJSON(campaignData, campaignAssetsQueue);
				assetsQueue.addAll(campaignAssetsQueue);

				// Check if we need to reset the device for QA, otherwise load campaign settings into downloaded campaign
				if (qaUser == null || !qaUser.isResetDevice()) {
					String campaignIdStr = Integer.toString(campaign.getId());
					if (jsonSettings.has(campaignIdStr)) {
						JSONObject campaignSettings = jsonSettings.getJSONObject(campaignIdStr);
						if (campaignSettings != null) {
							campaign.loadSettings(campaignSettings);
						}
					}
				}
				
				newCampaigns.add(campaign);
				Log.i(LOG_TAG, "Got campaign with id " + campaign.getId());
				
				if (qaUser != null) {
					// Add campaign for QA purposes
					campaignsDownloaded.put(campaign.getId(), null);
				}
			}

			// QA logging
			if (qaUser != null) {
				qaUser.talkSession(campaignsDownloaded);
			}
			
			// Launch load assets, then add to active campaigns
			// Note that campaign is also added to campaigns list in this function
			downloadAssets(assetsQueue);
	
			// Update current list of campaigns with new ones
			campaigns = new ArrayList<SwrveCampaign>(newCampaigns);
		} catch (JSONException exp) {
			Log.e(LOG_TAG, "Error parsing campaign JSON", exp);
		}
	}

	protected void downloadAssets(final Set<String> assetsQueue) {
		assetsCurrentlyDownloading = true;
		ExecutorService resourceDownloadExecutor = Executors.newSingleThreadExecutor();		
		resourceDownloadExecutor.execute(SwrveRunnables.withoutExceptions(new Runnable() {
			@Override
			public void run() {
				Set<String> assetsToDownload = filterExistingFiles(assetsQueue);
				for (String asset : assetsToDownload) {
					boolean success = downloadAssetSynchronously(asset);
					if (success) {
						synchronized(assetsOnDisk) {
							assetsOnDisk.add(asset);
						}
					}
				}
				assetsCurrentlyDownloading = false;
				autoShowMessages();
				taskCompleted();
			}
		}));
	}

	protected SwrveCampaign loadCampaignFromJSON(JSONObject campaignData, Set<String> assetsQueue) throws JSONException {
		return new SwrveCampaign((SwrveBase<?,?>)this, campaignData, assetsQueue);
	}

	protected boolean downloadAssetSynchronously(final String assetPath) {
		String url = cdnRoot + assetPath;
		InputStream inputStream = null;
		try {
			URLConnection openConnection = new URL(url).openConnection();
			inputStream = new DoneHandlerInputStream(openConnection.getInputStream());
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				stream.write(buffer, 0, bytesRead);
			}
			byte[] fileContents = stream.toByteArray();
			String sha1File = SwrveHelper.sha1(stream.toByteArray());
			if (assetPath.equals(sha1File)) {
				// Save to file
				FileOutputStream fileStream = new FileOutputStream(new File(cacheDir, assetPath));
				fileStream.write(fileContents);
				fileStream.close();

				return true;
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error downloading campaigns", e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error downloading campaigns", e);
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	protected Set<String> filterExistingFiles(Set<String> assetsQueue) {
		Iterator<String> itDownloadQueue = assetsQueue.iterator();
		while (itDownloadQueue.hasNext()) {
			String assetPath = itDownloadQueue.next();
			File file = new File(cacheDir, assetPath);
			if (file.exists()) {
				itDownloadQueue.remove();
				synchronized(assetsOnDisk) {
					assetsOnDisk.add(assetPath);
				}
			}
		}
		return assetsQueue;
	}

	protected void saveCampaignSettings() {
		try {
			// Save campaign state
			JSONObject jsonSettings = new JSONObject();
			Iterator<SwrveCampaign> itCampaign = campaigns.iterator();
			while (itCampaign.hasNext()) {
				SwrveCampaign campaign = itCampaign.next();
				jsonSettings.put(Integer.toString(campaign.getId()), campaign.createSettings());
			}

			final String serializedSettings = jsonSettings.toString();
			// Write to cache
			storageExecutorExecute(new Runnable() {
				@Override
				public void run() {
					MemoryCachedLocalStorage cachedStorage = cachedLocalStorage;
					cachedStorage.setCacheEntryForUser(userId, CAMPAIGN_SETTINGS_CATEGORY, serializedSettings);
					if (cachedStorage.getSecondaryStorage() != null) {
						cachedStorage.getSecondaryStorage().setCacheEntryForUser(userId, CAMPAIGN_SETTINGS_CATEGORY, serializedSettings);
					}
					Log.i(LOG_TAG, "Saved campaigns in cache");
					taskCompleted();
				}
			});
		} catch (JSONException exp) {
			Log.e(LOG_TAG, "Error saving campaigns settings", exp);
		}
	}

	protected void noMessagesWereShown(String event, String reason) {
		Log.i(LOG_TAG, "Not showing message for " + event + ": " + reason);
		if (qaUser != null) {
			qaUser.triggerFailure(event, reason);
		}
	}

	protected void unbindAndShutdown() {
		// Reduce the references to the SDK
		int counter = bindCounter.decrementAndGet();
		// Remove the binding to the current activity, if any
		this.activityContext = null;
		
		removeCurrentDialog();
		
		// Check if there are no more references to this object
		if (counter == 0) {
			if (mustCleanInstance) {
				((SwrveBase<?, ?>)this).shutdown();
			}
		}
	}
	
	protected void removeCurrentDialog() {
		if (currentDialog != null) {
			final SwrveDialog dialog = currentDialog.get();
			if (dialog != null && dialog.isShowing()) {
				messageDisplayed = dialog.getMessage();
				// Remove reference to the SDK from the message
				messageDisplayed.setMessageController(null);
				lastMessageDestroyed = (new Date()).getTime();
				Activity activity = dialog.getOwnerActivity();
				if (activity == null) {
					activity = getActivityContext();
				}
				
				if (activity != null) {
					// Call from activity UI thread
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
						}
					});
				} else {
					// Call from this thread
					dialog.dismiss();
				}
			}
			currentDialog = null;
		}
	}
	
	protected SwrveOrientation getDeviceOrientation() {
		Context ctx = context.get();
		if (ctx != null) {
			return SwrveOrientation.parse(ctx.getResources().getConfiguration().orientation);
		}
		return SwrveOrientation.Both;
	}

	protected class PendingState {
		public boolean pending;
		public boolean sending;

		public PendingState(boolean pending, boolean sending) {
			this.pending = pending;
			this.sending = sending;
		}

		public void setPending(boolean pending) {
			this.pending = pending;
		}

		public void setSending(boolean sending) {
			this.sending = sending;
		}

		public boolean isPending() {
			return pending;
		}

		public boolean isSending() {
			return sending;
		}
	}

	protected Activity getActivityContext() {
		if (activityContext != null) {
			Activity ctx = activityContext.get();
			if (ctx != null) {
				return ctx;
			}
		}

		return null;
	}
	
	protected boolean isActivityFinishing(Activity activity) {
		return activity.isFinishing();
	}
	
	protected class DisplayMessageRunnable implements Runnable {
		private SwrveBase<?, ?> sdk;
		private Activity activity;
		private SwrveMessage message;
		private boolean firstTime;
		
		public DisplayMessageRunnable(SwrveBase<?, ?> sdk, Activity activity, SwrveMessage message, boolean firstTime) {
			this.sdk = sdk;
			this.activity = activity;
			this.message = message;
			this.firstTime = firstTime;
		}

		@Override
		public void run() {
			try {
				Log.d(LOG_TAG, "Called show dialog");
				SwrveDialog dialog = (currentDialog == null)? null : currentDialog.get();
				if (dialog == null || !dialog.isShowing()) {
					SwrveOrientation deviceOrientation = getDeviceOrientation();
					Log.d(LOG_TAG, "Trying to show dialog with orientation " + deviceOrientation);
					SwrveMessageView swrveMessageView = SwrveMessageViewFactory.getInstance().buildLayout(activity, message, deviceOrientation, installButtonListener, customButtonListener, firstTime, config.getMinSampleSize());
					SwrveDialog newDialog = new SwrveDialog(activity, message, swrveMessageView, R.style.SwrveDialogTheme); 
					newDialog.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							// Remove reference to the dialog on our side
							if (currentDialog != null) {
								SwrveDialog refDialog = currentDialog.get();
								if (refDialog == dialog) {
									currentDialog = null;
								}
							}
						}
					});
					
					// Check if the customer wants to manage the dialog themselves
					if (dialogListener != null) {
						dialogListener.onDialog(newDialog);
					} else {
						// Save a reference to the dialog and display it now
						sdk.currentDialog = new WeakReference<SwrveDialog>(newDialog);
						newDialog.show();
					}
				}
				
				activity = null;
				sdk = null;
				message = null;
			} catch (Exception e) {
				Log.w(LOG_TAG, "Couldn't create a SwrveMessageView", e);
			}
		}
	}
	
	/**
	 * Get device info and send it to Swrve
	 */
	protected void queueDeviceInfoNow(final boolean sendNow) {
		final SwrveBase<T, C> swrve = (SwrveBase<T, C>) this;
		storageExecutorExecute(new Runnable() {
			@Override
			public void run() {
				Log.i(LOG_TAG, "Queueing device info");
				// Automatic user update

				userUpdate(swrve.getDeviceInfo());

				// Send event after it has been queued
				if (sendNow) {
					restClientExecutorExecute(new Runnable() {
						@Override
						public void run() {
							Log.i(LOG_TAG, "Sending device info");
							swrve.sendQueuedEvents();
							taskCompleted();
						}
					});
				}
				taskCompleted();
			}
		});
	}
	
	/**
	 * Create a unique key for this user
	 */
	public String getUniqueKey() {
		Context contextRef = context.get();
		String androidId = "";
		if (contextRef != null) {
			androidId = Secure.getString(contextRef.getContentResolver(), Secure.ANDROID_ID);
		}

		return this.userId + this.apiKey + androidId;
	}

	/**
	 * Invalidates the currently stored ETag
	 * Should be called when a refresh of campaigns and resources needs to be forced (eg. when cached data cannot be read)
	 */
	protected void invalidateETag() {
		campaignsAndResourcesLastETag = null;
		SharedPreferences settings = context.get().getSharedPreferences(SDK_PREFS_NAME, 0);
		SharedPreferences.Editor settingsEditor = settings.edit();
		settingsEditor.remove("campaigns_and_resources_etag");
		settingsEditor.commit();
	}
	
	/**
	 * Initialize Resource Manager with cache content
	 */
	protected void initResources() {
		this.resourceManager = new SwrveResourceManager();
		String cachedResources = null;

		// Read cached resources
		try {
			cachedResources = cachedLocalStorage.getSecureCacheEntryForUser(userId, RESOURCES_CACHE_CATEGORY, getUniqueKey());
		} catch (SecurityException e) {
			invalidateETag();
			Log.i(LOG_TAG, "Signature for " + RESOURCES_CACHE_CATEGORY + " invalid; could not retrieve data from cache");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", "Swrve.signature_invalid");
			queueEvent("event", parameters, null);
		}

		if (cachedResources != null) {
			try {
				JSONArray resourceJson = new JSONArray(cachedResources);
				this.resourceManager.setResourcesFromJSON(resourceJson);
			} catch (JSONException e) {
				Log.e(LOG_TAG, "Could not parse cached json content for resources", e);
			}
		} else {
			invalidateETag();
		}
	}
	
	/**
	 * Initialize campaigns with cache content
	 */
	protected void initCampaigns() {
		campaigns = new ArrayList<SwrveCampaign>();

		try {
			String campaignsFromCache = cachedLocalStorage.getSecureCacheEntryForUser(userId, CAMPAIGN_CATEGORY, getUniqueKey());
			if (campaignsFromCache != null) {
				JSONObject campaignsJson = new JSONObject(campaignsFromCache);
				updateCampaigns(campaignsJson);
				Log.i(LOG_TAG, "Loaded campaigns from cache.");
			} else {
				invalidateETag();
			}
		} catch (JSONException e) {
			invalidateETag();
			Log.e(LOG_TAG, "Invalid json in cache, cannot load campaigns", e);
		} catch (SecurityException e) {
			invalidateETag();
			Log.e(LOG_TAG, "Signature validation failed when trying to load campaigns from cache.", e);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", "Swrve.signature_invalid");
			queueEvent("event", parameters, null);
		}
	}

	/**
	 * Update campaigns with given JSON
	 */
	protected void updateCampaigns(JSONObject campaignJSON) {
		loadCampaignsFromJSON(campaignJSON);
	}

	/**
	 * Call resource listener if one is set up, and ensure it is called on the UI thread if possible
	 */
	protected void invokeResourceListener() {
		if (resourcesListener != null) {
			Activity ctx = getActivityContext();
			if (ctx != null) {
				ctx.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						resourcesListener.onResourcesUpdated();
					}
				
				});
			} else {
				// If we do not have access to the activity context run on current thread
				resourcesListener.onResourcesUpdated();
			}
		}
	}
	
	/**
	 * Check if any events need sending, then after flush delay reload campaigns and resources
	 * This function should be called periodically, and additionally immediately after an IAP event
	 */
	protected void checkForCampaignAndResourcesUpdates(boolean calledFromTimer) {
		final SwrveBase<T, C> swrve = (SwrveBase<T, C>) this;
		
		// If this function got called outside the timer schedule, restart the timer
		if (!calledFromTimer) {
			startCampaignsAndResourcesTimer();
		}

		// If there are any events to be sent, or if any events were sent since last refresh
		// send events queued, wait campaignsAndResourcesFlushRefreshDelay for events to reach servers and refresh
		final LinkedHashMap<ILocalStorage, LinkedHashMap<Long, String>> combinedEvents = cachedLocalStorage.getCombinedFirstNEvents(config.getMaxEventsPerFlush());
		if (!combinedEvents.isEmpty() || eventsWereSent) {
			swrve.sendQueuedEvents();
			eventsWereSent = false;
			ScheduledExecutorService timedService = Executors.newSingleThreadScheduledExecutor();
			timedService.schedule(new Runnable() {
				@Override
				public void run() {
					swrve.refreshCampaignsAndResources();
				}				
			}, campaignsAndResourcesFlushRefreshDelay.longValue(), TimeUnit.MILLISECONDS);
		} else {
			swrve.refreshCampaignsAndResources();
		}
		
	}
	
	/**
	 * Set up timer for checking for campaign and resources updates
	 */
	protected void startCampaignsAndResourcesTimer() {		
		if (!config.isAutoDownloadCampaingsAndResources()) {
			return;
		}
		
		final SwrveBase<T, C> swrve = (SwrveBase<T, C>) this;
		swrve.refreshCampaignsAndResources();

		// If there is an existing executor, shut it down 
		// This will finish any tasks in progress, but not execute any tasks currently scheduled or accept new tasks
		if (campaignsAndResourcesExecutor != null) {
			campaignsAndResourcesExecutor.shutdown();
		}

		// Start repeating timer
		campaignsAndResourcesExecutor = new ScheduledThreadPoolExecutor(1);		
		campaignsAndResourcesExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		campaignsAndResourcesExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				checkForCampaignAndResourcesUpdates(true);
			}
		}, 0, campaignsAndResourcesFlushFrequency.longValue(), TimeUnit.MILLISECONDS);

		// Call refresh once after refresh delay to ensure campaigns are reloaded after initial events have been sent
		ScheduledExecutorService timedService = Executors.newSingleThreadScheduledExecutor();
		timedService.schedule(new Runnable() {
			@Override
			public void run() {
				swrve.refreshCampaignsAndResources();
			}
		}, campaignsAndResourcesFlushRefreshDelay.longValue(), TimeUnit.MILLISECONDS);
	}
	
	public Set<String> getAssetsOnDisk() {
		synchronized(assetsOnDisk) {
			return this.assetsOnDisk;
		}
	}
	
	public String getAutoShowEventTrigger() {
		return SWRVE_AUTOSHOW_AT_SESSION_START_TRIGGER;
	}
}
