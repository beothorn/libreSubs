package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

public class SubtitleBaseLoader {

	private final SubtitlesBase subtitlesBase;

	public SubtitleBaseLoader(final File baseDir, final SubtitlesBase subtitlesBase) throws IOException {
		if(!baseDir.isDirectory()){
			throw new RuntimeException("BaseDir is not a directory");
		}
		this.subtitlesBase = subtitlesBase;
		
		loadAllFiles(baseDir);
	}
	
	private void loadAllFiles(final File dir) throws IOException{
		final String[] list = dir.list();
		if(list == null)
			return;
		for (final String fileName : list) {
			final File child = new File(dir,fileName);
			if(child.isDirectory()){
				loadAllFiles(child);
			}else{
				addStrFile(child);
			}
		}
	}

	private void addStrFile(final File child) throws IOException {
		subtitlesBase.addSubtitle(child);
	}

}
