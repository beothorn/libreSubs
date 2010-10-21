package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitlesRepositoryTest {
	
	private static TempRepositoryCreator tempRepositoryCreator;

	@Before
	public void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryCreator();
	}
	
	@Test
	public void loadRepositoryTest() throws IOException{
		final SubtitlesBase subtitlesBase = loadSubtitleBase();
		final String subtitleFromVideoIDOrNull = subtitlesBase.getSubtitleContentsFromVideoIDOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromVideoIDOrNull);
	}

	private SubtitlesBase loadSubtitleBase() throws IOException {
		final File tempDir = tempRepositoryCreator.getTempDir();
		final SubtitlesBase subtitlesBase = new SubtitlesBase(tempDir);
		new SubtitleBaseLoader(tempDir,subtitlesBase);
		return subtitlesBase;
	}
	
	@Test
	public void changeSubtitleContentInternally() throws IOException{
		final SubtitlesBase subtitlesBase = loadSubtitleBase();
		final String newContent = "New Content";
		subtitlesBase.changeContentsForSubtitle(newContent,PioneerFileInfo.language,PioneerFileInfo.videoID);
		final File fileOnTemp = tempRepositoryCreator.getFileOnTemp(PioneerFileInfo.path);
		final String pioneerFileContents = FileUtils.readFileToString(fileOnTemp);
		Assert.assertEquals(newContent, pioneerFileContents);
		final String subtitleFromVideoIDOrNull = subtitlesBase.getSubtitleContentsFromVideoIDOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(newContent, subtitleFromVideoIDOrNull);
	}
	
	@Test
	public void changeSubtitleContentExternallyAndWarnBase(){
		
	}
}
