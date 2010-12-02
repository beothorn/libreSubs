package org.libreSubsEngine.testUtils;

import org.libreSubsEngine.subtitleRepository.repository.PartialSHA1;

public class MockVideoFileInfo {
	
	public static final PartialSHA1 videoID = new PartialSHA1("23456");
	public static final String content = "mock srt content";
	public static final String language = "en_US";
	public static final String path = "/23/"+videoID+"."+language;
}
