package org.libreSubsApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ProcessDroppedFilesTest {

	
	@Test
	public void testDropOneVideo(){
		final File videoFile = new File("/video.mkv");
		final List<File> droppedList = new ArrayList<File>();
		droppedList.add(videoFile);
	}
	
	@Test
	public void testDropOneVideoAndOneSubtitle(){
		final File videoFile = new File("/video.mkv");
		final File subtitleFile = new File("/video.srt");
		final List<File> droppedList = new ArrayList<File>();
		droppedList.add(videoFile);
		droppedList.add(subtitleFile);
	}
	
}
