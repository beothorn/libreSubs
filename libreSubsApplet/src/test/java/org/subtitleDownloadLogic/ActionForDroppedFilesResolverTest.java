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
		filesDropped.add(new File("/foo/bar.srt"));
		
		final MockDownloaderUploader mockDownloaderUploader = new MockDownloaderUploader();
		final boolean onSeparateThread = false;
		new ActionForDroppedFilesResolver(filesDropped, mockDownloaderUploader, mockDownloaderUploader, this,onSeparateThread);
		
		final String downloadList = mockDownloaderUploader.getDownloadList();
		final String uploadList = mockDownloaderUploader.getUploadList();
		Assert.assertEquals("[]", downloadList);
		Assert.assertEquals("[/foo/bar.srt]", uploadList);
	}
	
	@Test
	public void testActionForDroppedFilesOverwritePolicy() throws IOException {
		final ArrayList<File> filesDropped = new ArrayList<File>();
		final File tempFileAvi = File.createTempFile("DELNOW", ".avi");
		filesDropped.add(tempFileAvi);
		final String tempFileWithoutExtension = IOUtils.removeExtension(tempFileAvi.getAbsolutePath());
		final File subFile = new File(tempFileWithoutExtension+".srt");
		Assert.assertTrue("Error creating temp file", subFile.createNewFile());
		
		final MockDownloaderUploader mockDownloaderUploader = new MockDownloaderUploader();
		final boolean onSeparateThread = false;
		new ActionForDroppedFilesResolver(filesDropped, mockDownloaderUploader, mockDownloaderUploader, this,onSeparateThread);
		
		final String downloadList = mockDownloaderUploader.getDownloadList();
		final String uploadList = mockDownloaderUploader.getUploadList();
		Assert.assertEquals("["+tempFileAvi.toString()+"]", downloadList);
		Assert.assertEquals("[]", uploadList);
	}

	@Override
	public void info(final String info) {
	}

	@Override
	public void error(final String error) {
	}

}
