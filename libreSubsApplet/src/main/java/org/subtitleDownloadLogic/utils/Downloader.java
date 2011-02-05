package org.subtitleDownloadLogic.utils;

import java.io.File;

import org.OutputListener;

public interface Downloader {

	public abstract void download(final OutputListener outputListener,
			final File video);

}