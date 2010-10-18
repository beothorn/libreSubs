package org.libreSubsEngine;

import java.util.LinkedHashMap;
import java.util.Map;

public class SubtitlesBase {

	public enum Language {
		PT_BR
	};

	private final Map<Language, Map<Long, String>> languageForSubtitles;

	public SubtitlesBase() {
		languageForSubtitles = new LinkedHashMap<Language, Map<Long, String>>();
	}

	public void addSubtitle(final long partialMD5, final Language language,
			final String sub) {
		Map<Long, String> md5ForLanguage = languageForSubtitles.get(language);
		if (md5ForLanguage == null) {
			md5ForLanguage = new LinkedHashMap<Long, String>();
			languageForSubtitles.put(language, md5ForLanguage);
		}
		md5ForLanguage.put(partialMD5, sub);
	}

	public String getSubtitleFromPartialMD5OrNull(final Language language,
			long partialMD5) {
		final Map<Long, String> md5ForLanguage = languageForSubtitles
				.get(language);
		if (md5ForLanguage == null)
			return null;
		final String subtitles = md5ForLanguage.get(partialMD5);
		return subtitles;
	}

}
