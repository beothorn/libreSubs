package org;

public class SiteAdresses {
	
	private static final String downloadUrl = "http://www.libresubs.com:8080/latestLibresubs/download?id=%id&lang=%lang";
	private static final String uploadUrl = "http://www.libresubs.com:8080/latestLibresubs/upload";
	private static final String appletUrl = "http://www.libresubs.com:8080/latestLibresubs/applets/subFinder.jar";
	
	public static String getDownloadurl() {
		return downloadUrl;
	}
	public static String getUploadurl() {
		return uploadUrl;
	}
	public static String getAppleturl() {
		return appletUrl;
	}

}
