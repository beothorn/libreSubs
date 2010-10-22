package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Subtitle {
	
	private String content;
	private File strFile;

	public Subtitle(final File strFile) throws IOException {
		this.strFile = strFile;
		this.content = FileUtils.readFileToString(strFile);
	}
	
	public Subtitle(final String content, final File strFile) throws IOException {
		this.setStrFile(strFile);
		this.setContent(content);
	}

	public void setContent(final String content) throws IOException {
		this.content = content;
		writeContentToFile();
	}

	public String getContent() throws IOException {
		return content;
	}

	public void setStrFile(final File strFile) {
		this.strFile = strFile;
	}

	public File getStrFileOrNull() {
		return strFile;
	}

	private void writeContentToFile() throws IOException {
		
		FileUtils.writeStringToFile(strFile, content);
	}
}
