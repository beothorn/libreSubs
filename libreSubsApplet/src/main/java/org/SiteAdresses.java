package org;

public class SiteAdresses {
	
	private static final String downloadUrl = "http://www.libresubs.com:8080/latestLibresubs/download?id=%id&lang=%lang";
	private static final String uploadUrl = "http://www.libresubs.com:8080/latestLibresubs/upload";
	
	public static String getDownloadurl() {
		return downloadUrl;
	}
	public static String getUploadurl() {
		return uploadUrl;
	}

}
