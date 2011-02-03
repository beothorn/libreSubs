package org.subtitleDownloadLogic.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class IOUtils {
	
	private static final int SHA1_KEY_LENGTH = 40;
	private static int PARTIAL_SHA1_SIZE = 100;

    public static String convertStreamToString(final InputStream is)
            throws IOException {
        final Writer writer = new StringWriter();

        final char[] buffer = new char[1024];
        try {
            final Reader reader = new BufferedReader(
                    new InputStreamReader(is, LocaleUtil.getEncodingForLanguage(Locale.getDefault().toString())));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
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