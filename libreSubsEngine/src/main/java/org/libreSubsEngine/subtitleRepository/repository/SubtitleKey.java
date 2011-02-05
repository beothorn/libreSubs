package org.libreSubsEngine.subtitleRepository.repository;


public class SubtitleKey {
	
	private final String language;
	private final PartialSHA1 videoID;	

	public SubtitleKey(final String language, final PartialSHA1 videoID) {
		this.language = language;
		this.videoID = videoID;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(!(other instanceof SubtitleKey))
			return false;
		final SubtitleKey otherSubKey = (SubtitleKey) other;
		
		return otherSubKey.getLanguage().equals(getLanguage()) && otherSubKey.videoID.equals(videoID);
	}
	
	@Override
	public int hashCode() {
		return (videoID.toString()+getLanguage()).hashCode();
	}
	
	@Override
	public String toString() {
		return "videoID: "+videoID +" language: "+ getLanguage().toString();
	}

	public String getLanguage() {
		return language;
	}

}
