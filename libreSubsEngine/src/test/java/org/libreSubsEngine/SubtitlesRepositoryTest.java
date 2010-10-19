package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.libreSubsEngine.SubtitlesBase.Language;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitlesRepositoryTest {
	
	private static TempRepositoryCreator tempRepositoryCreator;

	@BeforeClass
	public static void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryCreator();
	}
	
	@Test
	public void loadRepositoryTest(){
		final File tempDir = tempRepositoryCreator.getTempDir();
		final SubtitlesBase subtitlesBase = new SubtitlesBase();
		new SubtitleBaseLoader(tempDir,subtitlesBase);
		final String subtitleFromPartialMD5OrNull = subtitlesBase.getSubtitleFromPartialMD5OrNull(Language.pt_BR,
				PioneerFileInfo.partialmd5);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromPartialMD5OrNull);
	}

}
