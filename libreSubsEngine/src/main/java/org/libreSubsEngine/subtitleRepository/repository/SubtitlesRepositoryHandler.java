package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;

import org.libreSubsApplet.utils.LocaleUtil;

public class SubtitlesRepositoryHandler {

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
		addSubtitle(id, language, srtFile);		
		srtFile.delete();
	}
	
	public void addSubtitle(final String id, final String language, final File content) throws IOException {		
		final PartialSHA1 partialSHA1 = new PartialSHA1(id);
		subtitlesBase.addSubtitle(partialSHA1, language, content);
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

}
