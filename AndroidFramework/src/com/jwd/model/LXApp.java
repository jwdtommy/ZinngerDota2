package com.jwd.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;
import android.content.Context;

/**
 * app模型
 * 
 * @author yanyi
 * 
 */
public class LXApp implements Serializable {
	public static final int LOCAL = 0;
	public static final int WEB = 1;
	public static final int APP = 2;
	public static final int INSTUALLED = 0;
	public static final int UNINSTALL = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = -743972858299450598L;

	public LXApp(JSONObject json) {
		this.type = Integer.parseInt(json.optString("type"));
		this.id = Integer.parseInt(json.optString("id"));
		this.logo = json.optString("id");
		this.title = json.optString("id");
		this.des = json.optString("des");
		this.version = json.optString("version");
		this.needLogin = Integer.parseInt(json.optString("id")) > 0 ? true
				: false;
		this.canDel = true;
		this.contentUrl = json.optString("id");
		this.downloadUrl = json.optString("id");
	}

	public LXApp() {
	};

	// 对应上面的type
	private int type;
	private long id;
	private String logo;
	private int icon;
	private String title;
	private String des;
	private String version;
	private boolean needLogin;
	private boolean canDel;
	private String contentUrl;
	private String downloadUrl;
	private int status;
	private String className;
	private int newNotiCount;
	private boolean hasNewFeed;
	private String latestVersionTime;
	private String latestVersionSize;
	private boolean isExpiry;


	public boolean isExpiry() {
		return isExpiry;
	}

	public void setExpiry(boolean isExpiry) {
		this.isExpiry = isExpiry;
	}

	public String getLatestVersionTime() {
		return latestVersionTime;
	}

	public void setLatestVersionTime(String latestVersionTime) {
		this.latestVersionTime = latestVersionTime;
	}

	public String getLatestVersionSize() {
		return latestVersionSize;
	}

	public void setLatestVersionSize(String latestVersionSize) {
		this.latestVersionSize = latestVersionSize;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}

	public boolean isCanDel() {
		return canDel;
	}

	public void setCanDel(boolean canDel) {
		this.canDel = canDel;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public static int getLocal() {
		return LOCAL;
	}

	public static int getWeb() {
		return WEB;
	}

	public static int getApp() {
		return APP;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getNewNotiCount() {
		return newNotiCount;
	}

	public void setNewNotiCount(int newNotiCount) {
		this.newNotiCount = newNotiCount;
	}

	public boolean isHasNewFeed() {
		return hasNewFeed;
	}

	public void setHasNewFeed(boolean hasNewFeed) {
		this.hasNewFeed = hasNewFeed;
	}



}
