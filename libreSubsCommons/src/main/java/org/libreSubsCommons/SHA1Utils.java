package org.libreSubsCommons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA1Utils {
	
	private static int PARTIAL_SHA1_SIZE = 100;
	
	public static String getPartialSHA1SizeAsHumanReadable(){
		return PARTIAL_SHA1_SIZE+"k";
	}
	
	public static String getPartialSHA1ForFile(final File file) throws IOException {
		FileInputStream fileInputStream;
		fileInputStream = new FileInputStream(file);
		final byte[] test = new byte[PARTIAL_SHA1_SIZE];
		fileInputStream.read(test);
		return DigestUtils.shaHex(test);
	}
}
