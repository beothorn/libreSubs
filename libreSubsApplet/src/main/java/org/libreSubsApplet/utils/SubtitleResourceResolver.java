package org.libreSubsApplet.utils;



public class SubtitleResourceResolver {

	private final String downloadUrl;
	private final String uploadUrl;
	public static String idParameter = "id";
	public static String langParameter = "lang";
	public static String fileParameter = "file";
	public static String commandLineParameter = "commandLine";

	public SubtitleResourceResolver(final String downloadUrl, final String uploadUrl) {
		this.downloadUrl = downloadUrl;
		this.uploadUrl = uploadUrl;
	}

	public String resolve(final String id, final String lang, final String file) {
		String resolved = downloadUrl.replace("%"+idParameter, id);
		resolved = resolved.replace("%"+langParameter, lang);
		resolved = resolved.replace("%"+fileParameter, file);		
		return resolved+"&"+commandLineParameter+"=true";
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

}
