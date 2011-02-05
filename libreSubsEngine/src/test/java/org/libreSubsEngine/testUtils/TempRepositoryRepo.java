package org.libreSubsEngine.testUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

public class TempRepositoryRepo implements SubtitleRepositoryLocation {

	private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	public static final String TEST_REPO_PATH = "/subtitlesRepository";
	private final File tempDir;
	private SubtitlesRepository subtitlesBase;
	
	public TempRepositoryRepo() throws IOException {
		tempDir = new File(getTmpDir(), "testLibreSubsRepo");
		createTempRepository();
	}
	
	public File getFileOnTemp(final String fileRelativePath){
		return new File(tempDir,fileRelativePath);
	}
	
	public static String getTmpDir() {
		return TMP_DIR;
	}

	@Override
	public File getBaseDir() {
		return tempDir;
	}

	private void createTempRepository() throws IOException {
		final File tempDir = getBaseDir();
		if(tempDir.exists())
			FileUtils.deleteDirectory(tempDir);
		tempDir.mkdir();
		final URL tempRepoURL = TempRepositoryRepo.class.getResource(TEST_REPO_PATH);
		final File testRepo = new File(tempRepoURL.getFile());
		FileUtils.copyDirectory(testRepo, tempDir);
	}

	public void loadSubtitleBase() throws IOException {
		subtitlesBase = new SubtitlesRepository(this);
	}

	public SubtitlesRepository getSubRepo() {
		return subtitlesBase;
	}
}
