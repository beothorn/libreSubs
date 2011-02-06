package org.libreSubsEngine.subtitleRepository;

public class SubtitleRepositoryLocationFactory {
	
	private static SubtitleRepositoryLocation subtitleRepositoryLocation;
	
	public static SubtitleRepositoryLocation getRepository(){
		if(subtitleRepositoryLocation == null)
			return new SubtitleDefaultRepository();
		return subtitleRepositoryLocation;
	}

	public static void setSubtitleRepositoryLocation(
			final SubtitleRepositoryLocation subtitleRepositoryLocation) {
		SubtitleRepositoryLocationFactory.subtitleRepositoryLocation = subtitleRepositoryLocation;
	}

}
