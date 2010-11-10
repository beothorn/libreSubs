package org.libreSubsEngine.testUtils;

import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.repository.PartialSHA1;

public class MockVideoFileInfo {
	
	public static final PartialSHA1 videoID = new PartialSHA1("23456");
	public static final String content = "mock str content";
	public static final Language language = Language.en_US;
	public static final String path = "/23/"+videoID+"."+language;
}
