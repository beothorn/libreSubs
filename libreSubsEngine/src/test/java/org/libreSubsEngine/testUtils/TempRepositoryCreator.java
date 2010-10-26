package org.libreSubsEngine.testUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;

public class TempRepositoryCreator implements SubtitleRepositoryLocation {

	private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	public static final String TEST_REPO_PATH = "/subtitlesRepository";
	private final File tempDir;
	
	public TempRepositoryCreator() throws IOException {
		tempDir = new File(TMP_DIR, "testLibreSubsRepo");
		createTempRepository();
	}
	
	public File getFileOnTemp(String fileRelativePath){
		return new File(tempDir,fileRelativePath);
	}
	
	private void createTempRepository() throws IOException {
		final File tempDir = getBaseDir();
		if(tempDir.exists())
			FileUtils.deleteDirectory(tempDir);
		tempDir.mkdir();
		final URL tempRepoURL = TempRepositoryCreator.class.getResource(TEST_REPO_PATH);
		final File testRepo = new File(tempRepoURL.getFile());
		FileUtils.copyDirectory(testRepo, tempDir);
	}

	@Override
	public File getBaseDir() {
		return tempDir;
	}
}
