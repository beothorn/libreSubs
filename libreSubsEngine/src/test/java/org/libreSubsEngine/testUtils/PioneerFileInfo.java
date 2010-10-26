package org.libreSubsEngine.testUtils;

import org.libreSubsEngine.Language;
import org.libreSubsEngine.subtitleRepository.repository.SHA1;

public class PioneerFileInfo {
	
	public static final SHA1 videoID = new SHA1("12345");
	public static final String content = "pioneer str content";
	public static final Language language = Language.pt_BR;
	public static final String path = "/12/"+videoID+"."+language;

}
