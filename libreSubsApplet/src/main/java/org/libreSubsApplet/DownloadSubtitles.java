package org.libreSubsApplet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class DownloadSubtitles implements DropFileListener {

	private static final int PARTIAL_SHA1_SIZE = 100;

	@Override
	public void droppedFiles(final List<File> files) {
		for (final File file : files) {
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
			} catch (final FileNotFoundException fileNotFound) {
				fileNotFound.printStackTrace();
				return;
			}
			try {
				final byte[] test = new byte[PARTIAL_SHA1_SIZE];
				fileInputStream.read(test);
				final String fileName = file.getName();
				System.out.println(fileName+" "+DigestUtils.shaHex(test));
				final File srtFile = new File(file.getParent(),fileName+".srt");
				srtFile.createNewFile();
				
//				URL u=new URL("http://localhost:7001/enter.html");
//				app.getAppletContext().showDocument(u);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
