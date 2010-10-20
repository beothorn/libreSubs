package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class SubtitlesBase {

	private final Map<SubtitleKey, Subtitle> subtitles;
	private final File baseDir;

	public SubtitlesBase(final File baseDir) {
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
		
		FileUtils.writeStringToFile(subtitleFile, content);
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
			final long videoID) {
		final SubtitleKey subtitleKey = new SubtitleKey(language, videoID);
		final Subtitle subtitle = subtitles.get(subtitleKey);
		if (subtitle == null)
			return null;
		final String content = subtitle.getContent();
		return content;
	}

	public void changeContentsForSubtitle(final String newContent, final Language ptBr,
			final long videoID) {
		
	}

}
