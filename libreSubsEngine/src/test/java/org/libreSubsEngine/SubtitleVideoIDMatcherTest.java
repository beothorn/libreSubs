package org.libreSubsEngine;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitleVideoIDMatcherTest {

	private static TempRepositoryCreator tempRepositoryCreator;

	@Before
	public void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryCreator();
	}
	
	@Test
	public void addSubAndRetrieve() throws IOException {
		final SubtitlesBase subtitlesBase = new SubtitlesBase(tempRepositoryCreator.getTempDir());
		final String pioneerPath = TempRepositoryCreator.TEST_REPO_PATH+PioneerFileInfo.path;
		final InputStream subStream = SubtitleVideoIDMatcherTest.class
				.getResourceAsStream(pioneerPath);
		final String subtitleAsString = IOUtils.toString(subStream);
		Assert.assertEquals(PioneerFileInfo.content, subtitleAsString);
		subtitlesBase.addSubtitle(PioneerFileInfo.videoID, PioneerFileInfo.language, subtitleAsString);
		final String subtitleFromVideoIDOrNull = subtitlesBase.getSubtitleContentsFromVideoIDOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromVideoIDOrNull);
	}

}
