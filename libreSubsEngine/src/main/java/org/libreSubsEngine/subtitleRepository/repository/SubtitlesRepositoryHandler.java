package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;

import org.subtitleDownloadLogic.utils.LocaleUtil;

public class SubtitlesRepositoryHandler {

	//TODO: get rid of this class
	
	private final SubtitlesRepository subtitlesBase;

	public SubtitlesRepositoryHandler(final SubtitlesRepository subtitlesBase) {
		this.subtitlesBase = subtitlesBase;
	}

	public boolean isValidLanguage(final String language) {
		return LocaleUtil.isValidLanguage(language);
	}

	public String getSubtitleOrNull(final String id, final String lang) {
		final SubtitleKey subtitleKey = createKeyOrNull(id, lang);
		return subtitlesBase.getSubtitleContentsForKeyOrNull(subtitleKey);
	}

	public String listSubtitles() {
		return subtitlesBase.listSubtitles();
	}

	public boolean subtitleExists(final String id, final String lang) {
		return subtitlesBase.subtitleExists(createKeyOrNull(id, lang));
	}

	private SubtitleKey createKeyOrNull(final String id, final String lang) {
		if(!LocaleUtil.isValidLanguage(lang)){
			return null;
		}
		final SubtitleKey subtitleKey = new SubtitleKey(lang, new PartialSHA1(id));
		return subtitleKey;
	}

	public void addSubtitleFromFileAndDeleteIt(final String id, final String language, final File srtFile) throws IOException {
		subtitlesBase.addSubtitle(id, language, srtFile);
	}
	
	public void changeContentsForSubitle(final String commiter,final String email,final String message,final String id, final String lang, final String content) throws IOException{
		if(!subtitleExists(id, lang)){
			throw new RuntimeException("Subtitle doesn't exist: id"+id+" lang"+lang);
		}
		final SubtitleKey subtitleKey = createKeyOrNull(id, lang);
		subtitlesBase.changeContentsForSubtitle(commiter,email, message, content, subtitleKey);
	}

	public String getLastNCommits(final int i) {
		return subtitlesBase.getLastNCommits(i);
	}

	public int subtitlesQuantity() {
		return subtitlesBase.subtitlesQuantity();
	}

	public long subtitlesRepoSize() {
		return subtitlesBase.subtitlesRepoSize();
	}

}
