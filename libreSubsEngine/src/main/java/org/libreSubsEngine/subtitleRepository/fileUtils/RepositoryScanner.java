package org.libreSubsEngine.subtitleRepository.fileUtils;

import java.io.File;
import java.io.IOException;



public class RepositoryScanner {

	
	public static void scan(final RepositoryScannerListener repositoryScannerListener, final File baseDir) throws IOException{
		final String[] list = baseDir.list();
		if(list == null)
			return;
		for (final String fileName : list) {
			final File child = new File(baseDir,fileName);
			if(child.isDirectory()){
				if(!child.getName().startsWith("."))
					scan(repositoryScannerListener,child);
			}else{
				repositoryScannerListener.addFile(child);
			}
		}
	}
}
