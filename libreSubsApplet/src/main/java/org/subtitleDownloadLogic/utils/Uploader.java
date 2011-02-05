package org.subtitleDownloadLogic.utils;

import org.OutputListener;
import org.subtitleDownloadLogic.VideoWithSubtitle;

public interface Uploader {

	public abstract void upload(final OutputListener outputListener,
			final VideoWithSubtitle videoWithSubtitle);

}