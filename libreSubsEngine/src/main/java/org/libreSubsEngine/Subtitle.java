package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Subtitle {
	
	private String content;
	private File strFile;

	public Subtitle(final File strFile) throws IOException {
		this.setContent(FileUtils.readFileToString(strFile));
		this.strFile = strFile;
	}
	
	public Subtitle(final String content, final File strFile) {
		this.setContent(content);
		this.setStrFile(strFile);
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setStrFile(final File strFile) {
		this.strFile = strFile;
	}

	public File getStrFileOrNull() {
		return strFile;
	}

	public void write() throws IOException {
		FileUtils.writeStringToFile(strFile, content);
	}
	
	

}
