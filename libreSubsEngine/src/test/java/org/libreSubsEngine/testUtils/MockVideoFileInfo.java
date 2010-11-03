package org.libreSubsEngine.testUtils;

import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.repository.SHA1;

public class MockVideoFileInfo {
	
	public static final SHA1 videoID = new SHA1("23456");
	public static final String content = "mock str content";
	public static final Language language = Language.en_US;
	public static final String path = "/23/"+videoID+"."+language;
}
