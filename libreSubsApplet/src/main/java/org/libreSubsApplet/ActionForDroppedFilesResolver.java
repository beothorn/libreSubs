package org.libreSubsApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.libreSubsApplet.utils.IOUtils;

public class ActionForDroppedFilesResolver {

	private static final String SUBTITLE_EXTENSION = "srt";
	private final List<File> videosWithoutSubtitles;
	private final List<VideoWithSubtitle> filesToUpload;
	
	public ActionForDroppedFilesResolver(final List<File> droppedList, final OutputListener outputListener) {
		videosWithoutSubtitles = new ArrayList<File>();
		filesToUpload = new ArrayList<VideoWithSubtitle>();
		final List<File> videoFiles = new ArrayList<File>();
		final List<File> subtitlesFiles = new ArrayList<File>();
		for (final File fileOrDir : droppedList) {
			sortFiles(outputListener, videoFiles, subtitlesFiles, fileOrDir);
		}
		
		for (final File videoFile : videoFiles) {
			matchVideosWithSubtitles(subtitlesFiles, videoFile);
		}
	}

	private void matchVideosWithSubtitles(final List<File> subtitlesFiles,
			final File videoFile) {
		final File sub = getSubtitleForVideoOnSubtitleListOrNull(videoFile, subtitlesFiles);
		if(sub == null){
			videosWithoutSubtitles.add(videoFile);
		}else{
			filesToUpload.add(new VideoWithSubtitle(videoFile, sub));
		}
	}

	private void sortFiles(final OutputListener outputListener,
			final List<File> videoFiles, final List<File> subtitlesFiles,
			final File fileOrDir) {
		if(fileOrDir.isDirectory()){
			final File[] filesInDir = fileOrDir.listFiles();
			for (final File file : filesInDir) {
				sortFiles(outputListener,videoFiles,subtitlesFiles,file);
			}
			return;
		}
		final String extension = IOUtils.getExtension(fileOrDir.getName()).toLowerCase();
		if(extension.equals(SUBTITLE_EXTENSION)){
			subtitlesFiles.add(fileOrDir);
		}else {
			videoFiles.add(fileOrDir);
		}
	}

	private File getSubtitleForVideoOnSubtitleListOrNull(final File videoFile,
			final List<File> subtitlesFiles) {
		final String videoName = IOUtils.getBaseName(videoFile.getAbsolutePath()).toLowerCase();
		for (final File sub : subtitlesFiles) {
			final String subName = IOUtils.getBaseName(sub.getAbsolutePath()).toLowerCase();
			if(videoName.equals(subName)){
				return sub;
			}
		}
		return null;
	}

	public List<File> getVideosToDownloadSubtitles() {
		return videosWithoutSubtitles;
	}

	public List<VideoWithSubtitle> getFilesToUpload() {
		return filesToUpload;
	}

}
