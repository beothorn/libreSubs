package org.libreSubsEngine;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.libreSubsEngine.SubtitlesBase.Language;

public class SubtitleMD5MatcherTest {

	@Test
	public void addSubAndRetrieve() throws IOException {
		SubtitlesBase subtitlesBase = new SubtitlesBase();
		final String strContent = "pioneer str content";
		final long partialMD5 = 12345L;
		final InputStream subStream = SubtitleMD5MatcherTest.class
				.getResourceAsStream("/subtitlesRepository/pt_BR/p/pioneer.srt");
		final String subtitleAsString = IOUtils.toString(subStream);
		Assert.assertEquals(strContent, subtitleAsString);
		subtitlesBase.addSubtitle(partialMD5, Language.PT_BR, subtitleAsString);
		final String subtitleFromPartialMD5OrNull = subtitlesBase.getSubtitleFromPartialMD5OrNull(Language.PT_BR,
				partialMD5);
		Assert.assertEquals(strContent, subtitleFromPartialMD5OrNull);
	}

}
