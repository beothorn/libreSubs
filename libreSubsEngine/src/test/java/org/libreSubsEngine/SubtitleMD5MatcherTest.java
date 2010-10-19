package org.libreSubsEngine;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.libreSubsEngine.SubtitlesBase.Language;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitleMD5MatcherTest {

	@Test
	public void addSubAndRetrieve() throws IOException {
		SubtitlesBase subtitlesBase = new SubtitlesBase();
		final String pioneerPath = TempRepositoryCreator.TEST_REPO_PATH+PioneerFileInfo.path;
		final InputStream subStream = SubtitleMD5MatcherTest.class
				.getResourceAsStream( pioneerPath);
		final String subtitleAsString = IOUtils.toString(subStream);
		Assert.assertEquals(PioneerFileInfo.content, subtitleAsString);
		subtitlesBase.addSubtitle(PioneerFileInfo.partialmd5, PioneerFileInfo.language, subtitleAsString);
		final String subtitleFromPartialMD5OrNull = subtitlesBase.getSubtitleFromPartialMD5OrNull(Language.pt_BR,
				PioneerFileInfo.partialmd5);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromPartialMD5OrNull);
	}

}
