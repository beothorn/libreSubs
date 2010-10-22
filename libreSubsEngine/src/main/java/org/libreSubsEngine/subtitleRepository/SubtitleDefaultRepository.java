package org.libreSubsEngine.subtitleRepository;

import java.io.File;

public class SubtitleDefaultRepository {
	
	private final File baseDir;
	
	public SubtitleDefaultRepository() {
		final File userHome = new File(System.getProperty("user.home"));
		baseDir = new File(userHome,".libreSubs");
		if(!baseDir.exists()){
			baseDir.mkdir();
		}
	}

	public File getBaseDir() {
		return baseDir;
	}

}
