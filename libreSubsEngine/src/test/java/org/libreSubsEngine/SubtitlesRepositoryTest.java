package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.libreSubsEngine.SubtitlesBase.Language;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitlesRepositoryTest {
	
	private static TempRepositoryCreator tempRepositoryCreator;

	@Before
	public void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryCreator();
	}
	
	@Test
	public void loadRepositoryTest(){
		final SubtitlesBase subtitlesBase = loadSubtitleBase();
		final String subtitleFromPartialMD5OrNull = subtitlesBase.getSubtitleFromPartialMD5OrNull(Language.pt_BR,
				PioneerFileInfo.partialmd5);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromPartialMD5OrNull);
	}

	private SubtitlesBase loadSubtitleBase() {
		final File tempDir = tempRepositoryCreator.getTempDir();
		final SubtitlesBase subtitlesBase = new SubtitlesBase();
		new SubtitleBaseLoader(tempDir,subtitlesBase);
		return subtitlesBase;
	}
	
	@Test
	public void changeSubtitleContentInternally() throws IOException{
		final SubtitlesBase subtitlesBase = loadSubtitleBase();
		final String newContent = "NewContent";
		//TODO: Language + partial md5 should be a class and str contents and str file should be another class
		subtitlesBase.changeContentsForSubtitle(newContent,PioneerFileInfo.language,PioneerFileInfo.partialmd5);
		final File fileOnTemp = tempRepositoryCreator.getFileOnTemp(PioneerFileInfo.path);
		final String pioneerFileContents = FileUtils.readFileToString(fileOnTemp);
		Assert.assertEquals(newContent, pioneerFileContents);
		final String subtitleFromPartialMD5OrNull = subtitlesBase.getSubtitleFromPartialMD5OrNull(Language.pt_BR,
				PioneerFileInfo.partialmd5);
		Assert.assertEquals(newContent, subtitleFromPartialMD5OrNull);
	}
	
	@Test
	public void changeSubtitleContentExternallyAndWarnBase(){
		
	}

}
