package org.libreSubsApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class ActionForDroppedFilesResolver {

	private static final String SUBTITLE_EXTENSION = "srt";
	private final List<File> filesToDownload;
	private final List<VideoWithSubtitle> filesToUpload;
	private final static String[] videoFilesExtensions = new String[]{"mpeg", "mpg", "avi", "mov","wmv", "rm", "rmvb", "mp4", "3gp","ogm" , "ogg", "mkv"};
	
	public ActionForDroppedFilesResolver(final List<File> droppedList) {
		filesToDownload = new ArrayList<File>();
		filesToUpload = new ArrayList<VideoWithSubtitle>();
		final List<File> videoFiles = new ArrayList<File>();
		final List<File> subtitlesFiles = new ArrayList<File>();
		for (final File file : droppedList) {
			final String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();
			if(Arrays.asList(videoFilesExtensions).contains(extension)){
				videoFiles.add(file);
			}else if(extension.equals(SUBTITLE_EXTENSION)){
				subtitlesFiles.add(file);
			}
		}
		
		for (final File videoFile : videoFiles) {
			final File sub = getSubtitleForVideoOnSubtitleListOrNull(videoFile, subtitlesFiles);
			if(sub == null){
				filesToDownload.add(videoFile);
			}else{
				filesToUpload.add(new VideoWithSubtitle(videoFile, sub));
			}
		}
	}

	private File getSubtitleForVideoOnSubtitleListOrNull(final File videoFile,
			final List<File> subtitlesFiles) {
		final String videoName = FilenameUtils.getBaseName(videoFile.getAbsolutePath()).toLowerCase();
		for (final File sub : subtitlesFiles) {
			final String subName = FilenameUtils.getBaseName(sub.getAbsolutePath()).toLowerCase();
			if(videoName.equals(subName)){
				return sub;
			}
		}
		return null;
	}

	public List<File> getFilesToDownload() {
		return filesToDownload;
	}

	public List<VideoWithSubtitle> getFilesToUpload() {
		return filesToUpload;
	}

}
