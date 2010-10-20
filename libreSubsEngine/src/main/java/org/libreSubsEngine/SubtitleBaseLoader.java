package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.libreSubsEngine.SubtitlesBase.Language;

public class SubtitleBaseLoader {

	private final SubtitlesBase subtitlesBase;

	public SubtitleBaseLoader(File baseDir, SubtitlesBase subtitlesBase) {
		if(!baseDir.isDirectory()){
			throw new RuntimeException("BaseDir is not a directory");
		}
		this.subtitlesBase = subtitlesBase;
		
		loadAllFiles(baseDir);
	}
	
	private void loadAllFiles(File dir){
		final String[] list = dir.list();
		if(list == null)
			return;
		for (String fileName : list) {
			final File child = new File(dir,fileName);
			if(child.isDirectory()){
				loadAllFiles(child);
			}else{
				try {
					addStrFile(child);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void addStrFile(File child) throws IOException {
		final String md5 = StringUtils.substringAfterLast(child.getName(), ".");
		final String nameWithoutmd5 = StringUtils.substringBeforeLast(child.getName(), ".");
		final String languageName = StringUtils.substringAfterLast(nameWithoutmd5, ".");
		final String subtitles = FileUtils.readFileToString(child);
		final Language language = Language.valueOf(languageName);
		subtitlesBase.addSubtitle(Long.parseLong(md5), language, subtitles);
	}

}
