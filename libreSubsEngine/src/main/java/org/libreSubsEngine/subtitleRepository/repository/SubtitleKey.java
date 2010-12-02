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
		
		return otherSubKey.language.equals(language) && otherSubKey.videoID.equals(videoID);
	}
	
	@Override
	public int hashCode() {
		return (videoID.toString()+language).hashCode();
	}
	
	@Override
	public String toString() {
		return "videoID: "+videoID +" language: "+ language.toString();
	}

}
