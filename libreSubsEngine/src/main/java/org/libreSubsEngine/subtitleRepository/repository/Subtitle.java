package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.subtitleDownloadLogic.utils.LocaleUtil;

public class Subtitle {
	
	private String content;
	private File srtFile;
	
	public Subtitle(final String content, final File srtFile) throws IOException {
		this.setStrFile(srtFile);
		this.content = content;
	}

	public void setContent(final String content, final String language) throws IOException {
		this.content = content;
		writeContentToFile(language);
	}

	public String getContent(){
		return content;
	}

	public void setStrFile(final File srtFile) {
		this.srtFile = srtFile;
	}

	public File getStrFileOrNull() {
		return srtFile;
	}

	private void writeContentToFile(final String language) throws IOException {
		final String encodingForLanguage = LocaleUtil.getEncodingForLanguage(language);
		FileUtils.writeStringToFile(srtFile, content,encodingForLanguage);
	}
}
