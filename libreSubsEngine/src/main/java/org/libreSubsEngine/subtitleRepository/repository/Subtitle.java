package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Subtitle {
	
	private String content;
	private File srtFile;

	public Subtitle(final File srtFile) throws IOException {
		this.srtFile = srtFile;
		this.content = FileUtils.readFileToString(srtFile);//TODO: here uses the default plataform encoding :( BUG
	}
	
	public Subtitle(final String content, final File srtFile) throws IOException {
		this.setStrFile(srtFile);
		this.setContent(content);
	}

	public void setContent(final String content) throws IOException {
		this.content = content;
		writeContentToFile();
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

	private void writeContentToFile() throws IOException {
		FileUtils.writeStringToFile(srtFile, content);
	}
}
