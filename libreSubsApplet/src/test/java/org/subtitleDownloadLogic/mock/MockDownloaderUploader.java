package org.subtitleDownloadLogic.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.OutputListener;
import org.subtitleDownloadLogic.VideoWithSubtitle;
import org.subtitleDownloadLogic.utils.Downloader;
import org.subtitleDownloadLogic.utils.Uploader;

public class MockDownloaderUploader implements Downloader,Uploader {

	private final List<File> toDownload;
	private final List<File> toUpload;
	
	public MockDownloaderUploader() {
		toDownload = new ArrayList<File>();
		toUpload = new ArrayList<File>();
	}
	
	@Override
	public void download(final OutputListener outputListener, final File video) {
		toDownload.add(video);
	}

	@Override
	public void upload(final OutputListener outputListener,
			final VideoWithSubtitle videoWithSubtitle) {
		final File subtitle = videoWithSubtitle.getSubtitle();
		toUpload.add(subtitle);
	}

	public String getDownloadList() {
		return toDownload.toString();
	}

	public String getUploadList() {
		return toUpload.toString();
	}

}
