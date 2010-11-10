package org.libreSubsEngine.subtitleRepository.repository;

import org.libreSubsCommons.Language;

public class SubtitleKey {
	
	private final Language language;
	private final PartialSHA1 videoID;	

	public SubtitleKey(final Language language, final PartialSHA1 videoID) {
		this.language = language;
		this.videoID = videoID;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(!(other instanceof SubtitleKey))
			return false;
		final SubtitleKey otherSubKey = (SubtitleKey) other;
		
		return otherSubKey.language.equals(language) && otherSubKey.videoID.equals(videoID);
	}
	
	@Override
	public int hashCode() {
		return (videoID.toString()+language.name()).hashCode();
	}
	
	@Override
	public String toString() {
		return "videoID: "+videoID +" language: "+ language.toString();
	}

}
