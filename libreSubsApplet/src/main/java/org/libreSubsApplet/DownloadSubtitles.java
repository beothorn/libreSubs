package org.libreSubsApplet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.libreSubsCommons.FileUtils;
import org.libreSubsCommons.SHA1Utils;

public class DownloadSubtitles implements DropFileListener {

	private final URL subtitleSource;
	private final OutputListener outputListener;

	public DownloadSubtitles(final URL subtitleSource, final OutputListener outputListener) {
		this.subtitleSource = subtitleSource;
		this.outputListener = outputListener;
	}
	

	@Override
	public void droppedFiles(final List<File> files){
		for (final File file : files) {

			final String shaHex;
			try {
				shaHex = SHA1Utils.getPartialSHA1ForFile(file);
			} catch (final IOException e1) {
				throw new RuntimeException("Error while calculating partial SHA1 for file.",e1);
			}

			final String fileName = file.getName();
			final String parent = file.getParent();
			final String newStrFileName = FilenameUtils.removeExtension(fileName)
					+ ".srt";
			outputListener.info(shaHex);
			final File srtFile = FileUtils.createFileOrCry(parent, newStrFileName);
			
//			 final URL u=new URL(subtitleSource;
//			 app.getAppletContext().showDocument(u);
		}
	}

	

	

}
