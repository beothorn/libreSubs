package org.libreSubsApplet;

import java.io.File;

public class VideoWithSubtitle {
	
	private final File video;
	private final File subtitle;

	public VideoWithSubtitle(final File video, final File subtitle) {
		this.video = video;
		this.subtitle = subtitle;
	}

	public File getVideo() {
		return video;
	}

	public File getSubtitle() {
		return subtitle;
	}
	
	@Override
	public String toString() {
		return video.getName() +" - "+ subtitle.getName();
	}
}
