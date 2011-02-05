package org.subtitleDownloadLogic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.OutputListener;
import org.junit.Assert;
import org.junit.Test;
import org.subtitleDownloadLogic.mock.MockDownloaderUploader;
import org.subtitleDownloadLogic.utils.IOUtils;

public class ActionForDroppedFilesResolverTest implements OutputListener {
	
	@Test
	public void testActionForDroppedFilesResolver() {
		final ArrayList<File> filesDropped = new ArrayList<File>();
		filesDropped.add(new File("/foo/bar.avi"));
		
		final MockDownloaderUploader mockDownloaderUploader = new MockDownloaderUploader();
		final boolean onSeparateThread = false;
		new ActionForDroppedFilesResolver(filesDropped, mockDownloaderUploader, mockDownloaderUploader, this,onSeparateThread);
		
		final String downloadList = mockDownloaderUploader.getDownloadList();
		final String uploadList = mockDownloaderUploader.getUploadList();
		Assert.assertEquals("[/foo/bar.avi]", downloadList);
		Assert.assertEquals("[]", uploadList);
	}
	
	@Test
	public void testActionForDroppedFilesOverwritePolicy() throws IOException {
		final ArrayList<File> filesDropped = new ArrayList<File>();
		final File tempFileAvi = File.createTempFile("DELNOW", ".avi");
		tempFileAvi.deleteOnExit();
		filesDropped.add(tempFileAvi);
		final String tempFileWithoutExtension = IOUtils.removeExtension(tempFileAvi.getAbsolutePath());
		final File subFile = new File(tempFileWithoutExtension+".srt");
		Assert.assertTrue("Error creating temp file", subFile.createNewFile());
		subFile.deleteOnExit();
		
		final MockDownloaderUploader mockDownloaderUploader = new MockDownloaderUploader();
		final boolean onSeparateThread = false;
		new ActionForDroppedFilesResolver(filesDropped, mockDownloaderUploader, mockDownloaderUploader, this,onSeparateThread);
		
		final String downloadList = mockDownloaderUploader.getDownloadList();
		final String uploadList = mockDownloaderUploader.getUploadList();
		Assert.assertEquals("[]", downloadList);
		Assert.assertEquals("["+subFile.toString()+"]", uploadList);
	}

	@Override
	public void info(final String info) {
	}

	@Override
	public void error(final String error) {
	}

}
