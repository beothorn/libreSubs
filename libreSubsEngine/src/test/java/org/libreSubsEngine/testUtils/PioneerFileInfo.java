package org.libreSubsEngine.testUtils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.libreSubsEngine.subtitleRepository.repository.PartialSHA1;

public class PioneerFileInfo {
	
	private static final PartialSHA1 videoID = new PartialSHA1("12345");
	private static String content;
	private static final String language = "pt_BR";
	private static final String path = "/12/"+getVideoid()+"."+getLanguage();
	
	public static PartialSHA1 getVideoid() {
		return videoID;
	}

	public static String getContent() {
		if(content == null){
			final Class<PioneerFileInfo> clazz = PioneerFileInfo.class;
			final InputStream resourceAsStream = clazz.getResourceAsStream("/subtitlesRepository/12/12345.pt_BR");
			try {
				content = IOUtils.toString(resourceAsStream,"cp1252");
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
		return content;
	}

	public static String getLanguage() {
		return language;
	}

	public static String getPath() {
		return path;
	}

}
