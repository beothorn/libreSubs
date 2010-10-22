package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.libreSubsEngine.Language;

public class SubtitlesRepository {

	private final Map<SubtitleKey, Subtitle> subtitles;
	private final File baseDir;

	public SubtitlesRepository(final File baseDir) {
		if(!baseDir.isDirectory()){
			throw new RuntimeException("BaseDir is not a directory");
		}
		this.baseDir = baseDir;
		subtitles = new LinkedHashMap<SubtitleKey, Subtitle>();
	}

	public void addSubtitle(final long videoID, final Language language,final String content) throws IOException {
		final String videoIDAsString = Long.toString(videoID);
		final String strDirName = videoIDAsString.substring(0, 2);
		final File strDir = new File(baseDir, strDirName);
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
		addSubtitle(subtitleFile);
	}
	
	public void addSubtitle(final File strFile) throws IOException {
		final String videoID = StringUtils.substringBeforeLast(strFile.getName(), ".");
		final String language = StringUtils.substringAfterLast(strFile.getName(), ".");
		final SubtitleKey subtitleKey = new SubtitleKey(Language.valueOf(language), Long.parseLong(videoID));
		
		final String strFileContent = FileUtils.readFileToString(strFile);
		final Subtitle subtitle = new Subtitle(strFileContent, strFile);
		
		subtitles.put(subtitleKey, subtitle);
	}

	public String getSubtitleContentsFromVideoIDOrNull(final Language language,
			final long videoID) throws IOException {
		final SubtitleKey subtitleKey = new SubtitleKey(language, videoID);
		final Subtitle subtitle = subtitles.get(subtitleKey);
		if (subtitle == null)
			return null;
		final String content = subtitle.getContent();
		return content;
	}

	public void changeContentsForSubtitle(final String newContent, final Language language,
			final long videoID) throws IOException {
		changeContentsForSubtitle(newContent, new SubtitleKey(language, videoID) );
	}
	
	public void changeContentsForSubtitle(final String newContent, final SubtitleKey subtitleKey) throws IOException {
		final Subtitle subtitle = subtitles.get(subtitleKey);
		subtitle.setContent(newContent);
	}

	public File getBaseDir() {
		return baseDir;
	}

	public String listSubtitles() {
		final StringBuilder subtitlesList = new StringBuilder();
		final Set<SubtitleKey> keySet = subtitles.keySet();
		for (final SubtitleKey subtitleKey : keySet) {
			subtitlesList.append(subtitleKey.toString()+"\n");
		}
		return subtitlesList.toString();
	}

}
