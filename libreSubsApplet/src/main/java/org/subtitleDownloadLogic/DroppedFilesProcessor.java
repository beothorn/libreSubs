package org.subtitleDownloadLogic;

import java.io.File;
import java.util.List;

import org.OutputListener;
import org.libreSubsApplet.dropFile.DropFileListener;
import org.subtitleDownloadLogic.utils.DownloaderImpl;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;
import org.subtitleDownloadLogic.utils.UploaderImpl;

public class DroppedFilesProcessor implements DropFileListener {

	private final OutputListener outputListener;
	private final UploaderImpl uploader;
	private final DownloaderImpl downloader;

	public DroppedFilesProcessor(final SubtitleResourceResolver subtitleSource, final OutputListener outputListener, final String subtitleLanguage) {
		this.outputListener = outputListener;
		uploader = new UploaderImpl(subtitleSource.getUploadUrl(), subtitleLanguage);
		downloader = new DownloaderImpl(subtitleSource, subtitleLanguage);
	}

	@Override
	public void droppedFiles(final List<File> files) {
		new ActionForDroppedFilesResolver(
				files,downloader,uploader, outputListener);
	}
	

	public void setLanguage(final String language) {
		downloader.setSubtitleLanguage(language);
		uploader.setSubtitleLanguage(language);
	}

	public String getLanguage() {
		return downloader.getSubtitleLanguage();
	}

}
