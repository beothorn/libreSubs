package org.libreSubsCommons;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	
	public static File createFileOrCry(final String parentName,final String newFileName) {
		final File newFile = new File(parentName, newFileName);
		try {
			newFile.createNewFile();
			return newFile;
		} catch (final IOException e) {
			throw new RuntimeException("Error trying to create file: "+newFile.getAbsolutePath(),e);
		}
	}
	
}
