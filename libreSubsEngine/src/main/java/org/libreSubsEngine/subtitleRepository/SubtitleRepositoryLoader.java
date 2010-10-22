package org.libreSubsEngine.subtitleRepository;

import java.io.File;
import java.io.IOException;

import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;


public class SubtitleRepositoryLoader {

	private final SubtitlesRepository subtitlesBase;

	public SubtitleRepositoryLoader(final SubtitlesRepository subtitlesRepository) throws IOException {
		this.subtitlesBase = subtitlesRepository;
		loadAllFiles(subtitlesRepository.getBaseDir());
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
