package org.libreSubsEngine.subtitleRepository.repository;

import org.libreSubsEngine.Language;

public class SubtitleKey {
	
	private final Language language;
	private final long videoID;

	public SubtitleKey(final Language language, final long videoID) {
		this.language = language;
		this.videoID = videoID;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(!(other instanceof SubtitleKey))
			return false;
		final SubtitleKey otherSubKey = (SubtitleKey) other;
		
		return otherSubKey.language.equals(language) && otherSubKey.videoID == videoID;
	}
	
	@Override
	public int hashCode() {
		return (int) (videoID + language.ordinal());
	}
	
	@Override
	public String toString() {
		return "videoID: "+videoID +" language: "+ language.toString();
	}

}
