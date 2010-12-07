package org.libreSubsApplet.utils;



public class SubtitleResourceResolver {

	private final String urlParameter;
	
	public static String idParameter = "id";
	public static String langParameter = "lang";
	public static String fileParameter = "file";

	private final String uploadUrl;

	public SubtitleResourceResolver(final String downloadUrl, final String uploadUrl) {
		this.urlParameter = downloadUrl;
		this.uploadUrl = uploadUrl;
	}

	public String resolve(final String id, final String lang, final String file) {
		String resolved = urlParameter.replace("%"+idParameter, id);;
		resolved = resolved.replace("%"+langParameter, lang);
		resolved = resolved.replace("%"+fileParameter, file);		
		return resolved;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

}
