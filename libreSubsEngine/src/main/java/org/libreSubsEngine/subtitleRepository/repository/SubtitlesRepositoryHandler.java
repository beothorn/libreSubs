package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.libreSubsCommons.Language;

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

	public String listSubtitles() {
		return subtitlesBase.listSubtitles();
	}

	public boolean subtitleExists(final String id, final String lang) {
		return subtitlesBase.subtitleExists(createKeyOrNull(id, lang));
	}

	private SubtitleKey createKeyOrNull(final String id, final String lang) {
		if(!Language.isValidLanguage(lang)){
			return null;
		}
		final SubtitleKey subtitleKey = new SubtitleKey(Language.valueOf(lang), new PartialSHA1(id));
		return subtitleKey;
	}

	public void addSubtitleFromFileAndDeleteIt(final String id, final String language, final File srtFile) throws IOException {
		final String content = FileUtils.readFileToString(srtFile);
		srtFile.delete();
		addSubtitle(id, language, content);		
	}
	
	public void addSubtitle(final String id, final String language, final String content) throws IOException {		
		final PartialSHA1 partialSHA1 = new PartialSHA1(id);
		final Language lang = Language.valueOf(language);
		subtitlesBase.addSubtitle(partialSHA1, lang, content);
	}
	
	public void changeContentsForSubitle(final String id, final String lang, final String content) throws IOException{
		if(!subtitleExists(id, lang)){
			throw new RuntimeException("Subtitle doesn't exist: id"+id+" lang"+lang);
		}
		final SubtitleKey subtitleKey = createKeyOrNull(id, lang);
		subtitlesBase.changeContentsForSubtitle(content, subtitleKey);
	}

	public String getLastNCommits(final int i) {
		return subtitlesBase.getLastNCommits(i);
	}

}
