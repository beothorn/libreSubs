package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.libreSubsEngine.testUtils.MockVideoFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryRepo;

public class SubtitleVideoIDMatcherTest {

	private static TempRepositoryRepo tempRepositoryCreator;

	@Before
	public void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryRepo();
	}
	
	@Test
	public void addSubAndRetrieve() throws IOException {
		final SubtitlesRepository subtitlesBase = new SubtitlesRepository(tempRepositoryCreator);
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = new SubtitlesRepositoryHandler(subtitlesBase);
		final File tmpFile = File.createTempFile("libresubTest", ".tmp");
		FileUtils.writeStringToFile(tmpFile, MockVideoFileInfo.content);
		subtitlesRepositoryHandler.addSubtitle(MockVideoFileInfo.videoID.toString(), MockVideoFileInfo.language.toString(), tmpFile);
		final String subtitleFromVideoIDOrNull = subtitlesBase.getSubtitleContentsFromVideoIDAndLanguageOrNull(MockVideoFileInfo.language,
				MockVideoFileInfo.videoID);
		Assert.assertEquals(MockVideoFileInfo.content, subtitleFromVideoIDOrNull);
	}

}
