package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitlesRepositoryTest {
	
	private static TempRepositoryCreator tempRepositoryCreator;
	private SubtitlesRepository subtitlesBase;

	@Before
	public void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryCreator();
		subtitlesBase = loadSubtitleBase();
	}
	
	@Test
	public void loadRepositoryTest() throws IOException{
		final SubtitlesRepository subtitlesBase = loadSubtitleBase();
		final String subtitleFromVideoIDOrNull = subtitlesBase.getSubtitleContentsFromVideoIDOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromVideoIDOrNull);
	}

	private SubtitlesRepository loadSubtitleBase() throws IOException {
		final File tempDir = tempRepositoryCreator.getTempDir();
		final SubtitlesRepository subtitlesBase = new SubtitlesRepository(tempDir);
		new SubtitleRepositoryLoader(subtitlesBase);
		return subtitlesBase;
	}
	
	@Test
	public void changeSubtitleContentInternally() throws IOException{
		final String newContent = "New Content";
		subtitlesBase.changeContentsForSubtitle(newContent,PioneerFileInfo.language,PioneerFileInfo.videoID);
		final File fileOnTemp = tempRepositoryCreator.getFileOnTemp(PioneerFileInfo.path);
		final String pioneerFileContents = FileUtils.readFileToString(fileOnTemp);
		Assert.assertEquals(newContent, pioneerFileContents);
		final String subtitleFromVideoIDOrNull = subtitlesBase.getSubtitleContentsFromVideoIDOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(newContent, subtitleFromVideoIDOrNull);
	}
}
