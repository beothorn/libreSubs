package org.libreSubsApplet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class DownloadSubtitles implements DropFileListener {

	@Override
	public void dropedFiles(final List<File> files) {
		for (final File file : files) {
			FileInputStream fileImageInputStream;
			try {
				fileImageInputStream = new FileInputStream(file);
			} catch (final FileNotFoundException e1) {
				e1.printStackTrace();
				return;
			}
			try {
				System.out.println(DigestUtils.shaHex(fileImageInputStream));
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
