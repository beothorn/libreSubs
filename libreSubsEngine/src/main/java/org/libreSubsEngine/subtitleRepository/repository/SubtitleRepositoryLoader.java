package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;



public class SubtitleRepositoryLoader {

	private final SubtitlesRepository subtitlesBase;

	public SubtitleRepositoryLoader(final SubtitlesRepository subtitlesRepository) throws IOException {
		this.subtitlesBase = subtitlesRepository;
		loadAllFiles(subtitlesRepository.getBaseDir());
		subtitlesBase.commit();
	}
	
	private void loadAllFiles(final File dir) throws IOException{
		final String[] list = dir.list();
		if(list == null)
			return;
		for (final String fileName : list) {
			final File child = new File(dir,fileName);
			if(child.isDirectory()){
				if(!child.getName().startsWith("."))
					loadAllFiles(child);
			}else{
				addStrFile(child);
			}
		}
	}

	private void addStrFile(final File child) throws IOException {
		subtitlesBase.addSubtitleFromFileWithBaseName(child);
	}

}
