package org.libreSubsApplet;

import org.libreSubsCommons.Language;

public class SubtitleResourceResolver {

	private final String urlParameter;

	public SubtitleResourceResolver(final String urlParameter) {
		this.urlParameter = urlParameter;
	}

	public String resolve(final String id, final Language lang, final String file) {
		String resolved = urlParameter.replace("%id", id);;
		resolved = resolved.replace("%lang", lang.toString());
		resolved = resolved.replace("%file", file);		
		return resolved;
	}

}
