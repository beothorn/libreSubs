package org.libreSubsApplet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.libreSubsApplet.dropFile.DropFileListener;
import org.libreSubsCommons.FileUtils;
import org.libreSubsCommons.Language;
import org.libreSubsCommons.SHA1Utils;
import org.libreSubsCommons.SubtitleResourceResolver;

public class DownloadSubtitles implements DropFileListener {

	private final SubtitleResourceResolver subtitleSource;
	private final OutputListener outputListener;

	public DownloadSubtitles(final SubtitleResourceResolver subtitleSource, final OutputListener outputListener) {
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
			
			
			download(subtitleSource.resolve("1111", Language.pt_BR, newStrFileName), srtFile);
		}
	}

	public void download(final String address, final File localFileName) {
	    OutputStream out = null;
	    URLConnection conn = null;
	    InputStream in = null;
	    try {
	        // Get the URL
	        final URL url = new URL(address);
	        // Open an output stream to the destination file on our local filesystem
	        out = new BufferedOutputStream(new FileOutputStream(localFileName));
	        conn = url.openConnection();
	        in = conn.getInputStream();
	 
	        // Get the data
	        final byte[] buffer = new byte[1024];
	        int numRead;
	        while ((numRead = in.read(buffer)) != -1) {
	            out.write(buffer, 0, numRead);
	        }            
	        // Done! Just clean up and get out
	    } catch (final Exception exception) {
	        exception.printStackTrace();
	    } finally {
	        try {
	            if (in != null) {
	                in.close();
	            }
	            if (out != null) {
	                out.close();
	            }
	        } catch (final IOException ioe) {
	            // Shouldn't happen, maybe add some logging here if you are not 
	            // fooling around ;)
	        }
	    }
	}

}
