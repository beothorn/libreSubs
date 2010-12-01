package org.libreSubsApplet.utils;



public class SubtitleResourceResolver {

	private final String urlParameter;
	
	public static String idParameter = "id";
	public static String langParameter = "lang";
	public static String fileParameter = "file";

	public SubtitleResourceResolver(final String urlParameter) {
		this.urlParameter = urlParameter;
	}

	public String resolve(final String id, final String lang, final String file) {
		String resolved = urlParameter.replace("%"+idParameter, id);;
		resolved = resolved.replace("%"+langParameter, lang);
		resolved = resolved.replace("%"+fileParameter, file);		
		return resolved;
	}

}
