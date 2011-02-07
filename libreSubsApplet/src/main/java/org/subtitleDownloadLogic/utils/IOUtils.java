package org.subtitleDownloadLogic.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IOUtils {
	
	private static final int SHA1_KEY_LENGTH = 40;
	private static int PARTIAL_SHA1_SIZE = 100;

//    public static String convertStreamToString(final InputStream is, final String language)
//            throws IOException {
//    	final String encodingForLanguage = LocaleUtil.getEncodingForLanguage(language);
//        final char[] buffer = new char[1024];
//        final StringBuilder out = new StringBuilder();
//        try {
//	        final Reader reader = new InputStreamReader(is, encodingForLanguage);
//	        int read;
//	        do {
//	        	read = reader.read(buffer, 0, buffer.length);
//	        	if (read>0) {
//	        		out.append(buffer, 0, read);
//	        	}
//	        } while (read>=0);
//        } finally {
//            is.close();
//        }
//        final String resultingString = out.toString();
//		return resultingString;
//    }
    
    public static String convertStreamToString(final InputStream is, final String language) throws IOException{
    	final String encodingForLanguage = LocaleUtil.getEncodingForLanguage(language);
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int bufferUsed;
        while ( (bufferUsed = is.read(buffer)) > 0 ) {
            bout.write(buffer, 0, bufferUsed);
        }
        final byte[] byteArray = bout.toByteArray();
		final String string = new String(byteArray, encodingForLanguage);
		return string;
    }

	public static void writeStringToFile(final File srtFile, final String content) throws IOException{
		final BufferedWriter out = new BufferedWriter(new FileWriter(srtFile));
		out.write(content);
		out.close();
	}

	public static String removeExtension(final String fileName) {
		if(!fileName.contains(".")){
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	public static String getExtension(final String fileName) {
		return fileName.substring(fileName.lastIndexOf('.')+1, fileName.length());
	}

	public static String getBaseName(final String absolutePath) {
		final String withoutExtension = removeExtension(absolutePath);
		final String pathSeparator = System.getProperty("path.separator");
		return withoutExtension.substring(withoutExtension.lastIndexOf(pathSeparator)+1, withoutExtension.length());
	}

	public static String getPartialSHA1ForFile(final File file) throws SHA1CalculationException{
		
		byte[] partialFile;
		try {
			partialFile = readPartialFile(file);
		} catch (final FileNotFoundException e) {
			throw new SHA1CalculationException(e);
		} catch (final IOException e) {
			throw new SHA1CalculationException(e);
		}
		
		try {
			final String algorithm = "SHA-1";
			final MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] sha1hash = new byte[SHA1_KEY_LENGTH];
			md.update(partialFile, 0, PARTIAL_SHA1_SIZE);
			sha1hash = md.digest();
			return convertToHex(sha1hash);
		} catch (final NoSuchAlgorithmException e) {
			throw new SHA1CalculationException(e);
		}
	}

	private static byte[] readPartialFile(final File file)
			throws FileNotFoundException, IOException {
		final byte[] partialFile = new byte[PARTIAL_SHA1_SIZE];
		final FileInputStream fileInputStream = new FileInputStream(file);
		try{
			fileInputStream.read(partialFile);
		}finally{
			fileInputStream.close();
		}
		return partialFile;
	}
	
	 private static String convertToHex(final byte[] data) { 
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
	
	public static String getPartialSHA1SizeAsHumanReadable(){
		return PARTIAL_SHA1_SIZE+"k";
	}
	
	public static void inputStreamToFile(final InputStream inputStream,
			final File outputFile) throws IOException, FileNotFoundException {
		outputFile.createNewFile();
		outputFile.setExecutable(true);
		final OutputStream out = new FileOutputStream(outputFile);
		int read=0;
		final byte[] bytes = new byte[1024];
		while((read = inputStream.read(bytes))!= -1){
			out.write(bytes, 0, read);
		}
		inputStream.close();
		out.flush();
		out.close();
	}
	
}