package org.libreSubsApplet;

import java.io.File;
import java.util.List;

import org.libreSubsApplet.dropFile.DropFileListener;
import org.libreSubsApplet.utils.Downloader;
import org.libreSubsApplet.utils.SubtitleResourceResolver;
import org.libreSubsApplet.utils.Uploader;

public class DroppedFilesProcessor implements DropFileListener {

	private final OutputListener outputListener;
	private final Uploader uploader;
	private final Downloader downloader;

	public DroppedFilesProcessor(final SubtitleResourceResolver subtitleSource, final OutputListener outputListener, final String subtitleLanguage) {
		this.outputListener = outputListener;
		uploader = new Uploader(subtitleSource.getUploadUrl(), subtitleLanguage);
		downloader = new Downloader(subtitleSource, subtitleLanguage);
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
