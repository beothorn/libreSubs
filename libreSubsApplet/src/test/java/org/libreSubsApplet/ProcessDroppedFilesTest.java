package org.libreSubsApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ProcessDroppedFilesTest {

	
	@Test
	public void testDropOneVideo(){
		final File videoFile = new File("/video.mkv");
		final List<File> droppedList = new ArrayList<File>();
		droppedList.add(videoFile);
		
		final ActionForDroppedFilesResolver actionForDroppedFilesResolver = new ActionForDroppedFilesResolver(droppedList, new OutputListener(){
			@Override
			public void error(final String error) {
				//do nothing
			}

			@Override
			public void info(final String info) {
				//do nothing
			}
			
		});
		final List<File> filesToDownload = actionForDroppedFilesResolver.getVideosToDownloadSubtitles();
		Assert.assertEquals(droppedList.toString(), filesToDownload.toString());
	}
	
	@Test
	public void testDropOneVideoAndOneSubtitle(){
		final File videoFile = new File("/video.mkv");
		final File subtitleFile = new File("/video.srt");
		final List<File> droppedList = new ArrayList<File>();
		droppedList.add(videoFile);
		droppedList.add(subtitleFile);
		
		final ActionForDroppedFilesResolver actionForDroppedFilesResolver = new ActionForDroppedFilesResolver(droppedList,new OutputListener(){
			@Override
			public void error(final String error) {
				//do nothing
			}

			@Override
			public void info(final String info) {
				//do nothing
			}
			
		});
		final List<VideoWithSubtitle> filesToUpload = actionForDroppedFilesResolver.getFilesToUpload();
		Assert.assertEquals(1, filesToUpload.size());
		final VideoWithSubtitle videoWithSubtitle = filesToUpload.get(0);
		Assert.assertEquals(videoFile.getAbsolutePath(), videoWithSubtitle.getVideo().getAbsolutePath());
		Assert.assertEquals(subtitleFile.getAbsolutePath(), videoWithSubtitle.getSubtitle().getAbsolutePath());
	}
	
}
