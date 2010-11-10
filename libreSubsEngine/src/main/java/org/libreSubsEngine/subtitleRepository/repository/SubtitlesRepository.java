package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;

public class SubtitlesRepository {

	private final Map<SubtitleKey, Subtitle> subtitles;
	private final SubtitleRepositoryLocation repositoryLocation;

	public SubtitlesRepository(final SubtitleRepositoryLocation baseDir) {		
		this.repositoryLocation = baseDir;
		subtitles = new LinkedHashMap<SubtitleKey, Subtitle>();
	}

	void addSubtitle(final PartialSHA1 videoID, final Language language,final String content) throws IOException {
		final String videoIDAsString = videoID.toString();
		final String strDirName = videoIDAsString.substring(0, 2);
		final File strDir = new File(repositoryLocation.getBaseDir(), strDirName);
		if(!strDir.exists()){
			strDir.mkdir();
		}
		final String fileName = videoIDAsString + "." + language;
		final File subtitleFile = new File(strDir, fileName);
		if(subtitleFile.exists())
			throw new RuntimeException("File already exists");
		else
			subtitleFile.createNewFile();
		
		FileUtils.writeStringToFile(subtitleFile, content);
		addSubtitleFromFileWithBaseName(subtitleFile);
	}
	
	void addSubtitleFromFileWithBaseName(final File strFile) throws IOException {
		final String videoID = StringUtils.substringBeforeLast(strFile.getName(), ".");
		final String language = StringUtils.substringAfterLast(strFile.getName(), ".");
		final PartialSHA1 videoSHA1 = new PartialSHA1(videoID);
		addSubtitleToBaseOnMemory(videoSHA1, language, strFile);
	}

	private void addSubtitleToBaseOnMemory(final PartialSHA1 videoSHA1,final String language,final File strFile
			) throws IOException {
		final String strFileContent = FileUtils.readFileToString(strFile);
		
		final SubtitleKey subtitleKey = new SubtitleKey(Language.valueOf(language), videoSHA1);
		final Subtitle subtitle = new Subtitle(strFileContent, strFile);
		subtitles.put(subtitleKey, subtitle);
	}

	public String getSubtitleContentsFromVideoIDAndLanguageOrNull(final Language language,
			final PartialSHA1 videoID) throws IOException {
		final SubtitleKey subtitleKey = new SubtitleKey(language, videoID);
		return getSubtitleContentsForKeyOrNull(subtitleKey);
	}

	public String getSubtitleContentsForKeyOrNull(final SubtitleKey subtitleKey){
		if(subtitleKey == null)
			return null;
		final Subtitle subtitle = subtitles.get(subtitleKey);
		if (subtitle == null)
			return null;
		final String content = subtitle.getContent();
		return content;
	}

	public void changeContentsForSubtitle(final String newContent, final Language language,
			final PartialSHA1 videoID) throws IOException {
		changeContentsForSubtitle(newContent, new SubtitleKey(language, videoID) );
	}
	
	public void changeContentsForSubtitle(final String newContent, final SubtitleKey subtitleKey) throws IOException {
		final Subtitle subtitle = subtitles.get(subtitleKey);
		subtitle.setContent(newContent);
	}

	public String listSubtitles() {
		final StringBuilder subtitlesList = new StringBuilder();
		final Set<SubtitleKey> keySet = subtitles.keySet();
		for (final SubtitleKey subtitleKey : keySet) {
			subtitlesList.append(subtitleKey.toString()+"\n");
		}
		return subtitlesList.toString();
	}

	public File getBaseDir() {
		return repositoryLocation.getBaseDir();
	}

	public boolean subtitleExists(final SubtitleKey key){
		if(key == null)
			return false;
		return subtitles.containsKey(key);
	}

}
