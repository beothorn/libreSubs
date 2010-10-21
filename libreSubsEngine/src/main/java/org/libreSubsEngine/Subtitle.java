package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Subtitle {
	
	private String content;
	private File strFile;

	public Subtitle(File strFile) throws IOException {
		this.setContent(FileUtils.readFileToString(strFile));
		this.strFile = strFile;
	}
	
	public Subtitle(String content, File strFile) {
		this.setContent(content);
		this.setStrFile(strFile);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setStrFile(File strFile) {
		this.strFile = strFile;
	}

	public File getStrFileOrNull() {
		return strFile;
	}
	
	

}
