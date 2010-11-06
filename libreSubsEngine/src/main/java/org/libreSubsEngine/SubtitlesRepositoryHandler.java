package org.libreSubsEngine;

import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.repository.SHA1;
import org.libreSubsEngine.subtitleRepository.repository.SubtitleKey;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

public class SubtitlesRepositoryHandler {

	private final SubtitlesRepository subtitlesBase;

	public SubtitlesRepositoryHandler(final SubtitlesRepository subtitlesBase) {
		this.subtitlesBase = subtitlesBase;
	}

	public boolean isValidLanguage(final String language) {
		return Language.isValidLanguage(language);
	}

	public String getSubtitleOrNull(final String id, final String lang) {
		final SubtitleKey subtitleKey = createKeyOrNull(id, lang);
		return subtitlesBase.getSubtitleContentsForKeyOrNull(subtitleKey);
	}

	private SubtitleKey createKeyOrNull(final String id, final String lang) {
		if(!Language.isValidLanguage(lang)){
			return null;
		}
		final SubtitleKey subtitleKey = new SubtitleKey(Language.valueOf(lang), new SHA1(id));
		return subtitleKey;
	}

	public String listSubtitles() {
		return subtitlesBase.listSubtitles();
	}

	public boolean subtitleExists(final String id, final String lang) {
		return subtitlesBase.subtitleExists(createKeyOrNull(id, lang));
	}

}
