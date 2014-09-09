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
package com.swrve.sdk.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;

import com.swrve.sdk.SwrveHelper;
import com.swrve.sdk.messaging.SwrveOrientation;

/**
 * 
 * Configuration for the Swrve SDK
 * 
 */
public abstract class SwrveConfigBase {
	/**
	 * Custom unique user id
	 */
	private String userId;
	
	/**
	 * Is talk enabled
	 */
	private boolean talkEnabled = true;
	
	/**
	 * Maximum size to allow SQLite database to grow to
	 */
	private long maxSqliteDbSize = 1 * 1024 * 1024;

	/**
	 * Maximum number of events to send per flush
	 */
	private int maxEventsPerFlush = 50;
	
	/**
	 * Maximum number of click-thrus to send per flush
	 */
	private int maxClickThrusPerFlush = 10;
	
	/**
	 * Maximum number of concurrent downloads
	 */
	private int maxConcurrentDownloads = 10;

	/**
	 * Name of SQLite database to use for storage
	 */
	private String dbName = "swrve.db";

	/**
	 * Events end-point
	 */
	private URL eventsUrl = null;
	private URL defaultEventsUrl = null;

	/**
	 * Link end-point
	 */
	private URL linkUrl = null;
	private URL defaultLinkUrl = null;
	
	/**
	 * Content end-point
	 */
	private URL contentUrl = null;
	private URL defaultContentUrl = null;

	/**
	 * Link token (must be UUID) 
	 */
	private UUID linkToken;
	
	/**
	 * Session timeout time
	 */
	private long newSessionInterval = 30000;
	
	/**
	 * App version
	 */
	private String appVersion;
	
	/**
	 * Target app store.
	*/
	private String appStore = "google";
	
	/**
	 * App language. If null it defaults to the value returned by Locale.getDefault()
	 */
	private String language;
	
	/**
	 * Orientation supported by the application
	 */
	private SwrveOrientation orientation = SwrveOrientation.Both;
	
	/**
	 * Automatically download campaigns and resources
	 */
	private boolean autoDownloadCampaignsAndResources = true;
	
	/**
	 * Sample size to use when loading in-app message images. This should be a power
	 * of two. The default is 1.
	 */
	private int minSampleSize = 1;
	
	/**
	 * Cache folder used to save the in-app message images.
	 */
	private File cacheDir;
	
	/**
	 * Automatically send events onResume
	 */
	private boolean sendQueuedEventsOnResume = true;
	
	/**
	 * Maximum delay for in-app messages to appear after initialization
	 */
	private long autoShowMessagesMaxDelay = 5000; 
	
	/**
	 * Create an instance of SDK advance preferences.
	 */
	public SwrveConfigBase() {
	}
	
	/**
	 * @return the autoDownloadCampaignsAndResources
	 */
	public boolean isAutoDownloadCampaingsAndResources() {
		return this.autoDownloadCampaignsAndResources;
	}
	
	/**
	 * @param boolean autoDownload
	 */
	public SwrveConfigBase setAutoDownloadCampaignsAndResources(boolean autoDownload) {
		this.autoDownloadCampaignsAndResources = autoDownload;
		return this;
	}
	
	/**
	 * @return the orientation
	 */
	public SwrveOrientation getOrientation() {
		return orientation;
	}
	
	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public SwrveConfigBase setOrientation(SwrveOrientation orientation) {
		this.orientation = orientation;
		return this;
	}
	
	/**
	 * @param locale
	 * 			the locale to set
	 *
	 */
	public SwrveConfigBase setLanguage(Locale locale) {
		this.language = SwrveHelper.toLanguageTag(locale);
		return this;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * @param language
	 *            the language to set
	 *
	 * @deprecated Use {@link #setLanguage(Locale)} instead.
	 */
	@Deprecated
	public SwrveConfigBase setLanguage(String language) {
		this.language = language;
		return this;
	}
	
	/**
	 * @return the maxSqliteDbSize
	 */
	public long getMaxSqliteDbSize() {
		return maxSqliteDbSize;
	}

	/**
	 * @param maxSqliteDbSize
	 *            the maxSqliteDbSize to set
	 */
	public SwrveConfigBase setMaxSqliteDbSize(long maxSqliteDbSize) {
		this.maxSqliteDbSize = maxSqliteDbSize;
		return this;
	}

	/**
	 * @return the maxEventsPerFlush
	 */
	public int getMaxEventsPerFlush() {
		return maxEventsPerFlush;
	}

	/**
	 * @param maxEventsPerFlush
	 *            the maxEventsPerFlush to set
	 */
	public SwrveConfigBase setMaxEventsPerFlush(int maxEventsPerFlush) {
		this.maxEventsPerFlush = maxEventsPerFlush;
		return this;
	}
	
	/**
	 * @return the maxConcurrentDownloads
	 */
	public int getMaxConcurrentDownloads() {
		return maxConcurrentDownloads;
	}

	/**
	 * @param maxConcurrentDownloads
	 *            the maxConcurrentDownloads to set
	 */
	public SwrveConfigBase setMaxConcurrentDownloads(int maxConcurrentDownloads) {
		this.maxConcurrentDownloads = maxConcurrentDownloads;
		return this;
	}
	
	/**
	 * @return the maxClickThrusPerFlush
	 */
	public int getMaxClickThrusPerFlush() {
		return maxClickThrusPerFlush;
	}

	/**
	 * @param maxClickThrusPerFlush
	 *            the maxClickThrusPerFlush to set
	 */
	public SwrveConfigBase setMaxClickThrusPerFlush(int maxClickThrusPerFlush) {
		this.maxClickThrusPerFlush = maxClickThrusPerFlush;
		return this;
	}

	/**
	 * @return the db_name
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param db_name
	 *            the db_name to set
	 */
	public SwrveConfigBase setDbName(String db_name) {
		this.dbName = db_name;
		return this;
	}

	/**
	 * @return the eventsUrl
	 */
	public URL getEventsUrl() {
		if(eventsUrl == null)
			return defaultEventsUrl;
		return eventsUrl;
	}

	/**
	 * @param eventsUrl
	 *            the eventsUrl to set
	 */
	public SwrveConfigBase setEventsUrl(URL eventsUrl) {
		this.eventsUrl = eventsUrl;
		return this;
	}

	/**
	 * @return the linkUrl
	 */
	public URL getLinkUrl() {
		if(linkUrl == null)
			return defaultLinkUrl;
		return linkUrl;
	}
	
	/**
	 * @param linkUrl
	 *            the linkUrl to set
	 */
	public SwrveConfigBase setLinkUrl(URL linkUrl) {
		this.linkUrl = linkUrl;
		return this;
	}
	
	/**
	 * @return the contentUrl
	 */
	public URL getContentUrl() {
		if(contentUrl == null)
			return defaultContentUrl;
		return contentUrl;
	}
	
	/**
	 * @param contentUrl
	 *            the contentUrl to set
	 */
	public SwrveConfigBase setContentUrl(URL contentUrl) {
		this.contentUrl = contentUrl;
		return this;
	}
	
	/**
	 * @return the linkToken
	 */
	public UUID getLinkToken() {
		return linkToken;
	}
	
	/**
	 * @param linkToken
	 *            the linkToken to set. Must be a valid UUID (e.g. 2fbe0704-1984-3b39-8b79-13075d01c120)
	 */
	public SwrveConfigBase setLinkToken(UUID linkToken) {
		this.linkToken = linkToken;
		return this;
	}
	
	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}
	
	/**
	 * @param appVersion
	 *            the appVersion to set
	 */
	public SwrveConfigBase setAppVersion(String appVersion) {
		this.appVersion = appVersion;
		return this;
	}

	/**
	 * @return the newSessionInterval
	 */
	public long getNewSessionInterval() {
		return newSessionInterval;
	}
	
	/**
	 * @param newSessionInterval
	 *            the newSessionInterval to set
	 */
	public SwrveConfigBase setNewSessionInterval(long newSessionInterval) {
		this.newSessionInterval = newSessionInterval;
		return this;
	}
	
	/**
	 * @return the appStore
	 */
	public String getAppStore() {
		return appStore;
	}
	
	/**
	 * @param appStore
	 *            the appStore to set
	 */
	public SwrveConfigBase setAppStore(String appStore) {
		this.appStore = appStore;
		return this;
	}
	
	/**
	 * Generate default endpoints with the given app id. User internally.
	 * @throws MalformedURLException 
	 */
	public void generateUrls(int appId) throws MalformedURLException {
		defaultEventsUrl = new URL("http://" + appId + ".api.swrve.com");
		defaultLinkUrl = new URL("https://" + appId + ".link.swrve.com");
		defaultContentUrl = new URL("https://" + appId + ".content.swrve.com");
	}
	
	/**
	 * @param appStore
	 *            the user id to set
	 */
	public SwrveConfigBase setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * @return the user id
	 */
	public String getUserId() {
		return this.userId;
	}
	
	/**
	 * @param enabled
	 *            activate/deactivate talk
	 */
	public SwrveConfigBase setTalkEnabled(boolean enabled) {
		this.talkEnabled = enabled;
		return this;
	}

	/**
	 * @return if talk is enabled
	 */
	public boolean isTalkEnabled() {
		return this.talkEnabled;
	}
	
	/**
	 * Minimum sample size to use when loading in-app message images. This should be a power
	 * of two. The default is 1.
	 * @param sampleSize sample size
	 */
	public void setMinSampleSize(int sampleSize) {
		this.minSampleSize = sampleSize;
	}
	
	/**
	 * The sample size used when loading in-app message images.
	 * @return sample size
	 */
	public int getMinSampleSize() {
		return minSampleSize;
	}
	
	/**
	 * Cache folder used to save the in-app message images.
	 * The default is Context.getCacheDir()
	 * @param cacheDir cache folder
	 */
	public void setCacheDir(File cacheDir) {
		this.cacheDir = cacheDir;
	}
	
	/**
	 * Cache folder used to save the in-app message images.
	 * @return cache folder
	 */
	public File getCacheDir() {
		return cacheDir;
	}
	
	/**
	 * Automatically send events onResume
	 * @param sendQueuedEventsOnResume
	 */
	public void setSendQueuedEventsOnResume(boolean sendQueuedEventsOnResume) {
		this.sendQueuedEventsOnResume = sendQueuedEventsOnResume;
	}
	
	/**
	 * 
	 * @return if the sdk will send events onResume
	 */
	public boolean isSendQueuedEventsOnResume() {
		return sendQueuedEventsOnResume;
	}
	
	/**
	 * Maximum delay for in-app messages to appear after initialization
	 * @param autoShowMessagesMaxDelay
	 */
	public void setAutoShowMessagesMaxDelay(long autoShowMessagesMaxDelay) {
		this.autoShowMessagesMaxDelay = autoShowMessagesMaxDelay;
	}
	
	/**
	 * Maximum delay for in-app messages to appear after initialization
	 * @return maximim delay in milliseconds
	 */
	public long getAutoShowMessagesMaxDelay() {
		return autoShowMessagesMaxDelay;
	}
}
