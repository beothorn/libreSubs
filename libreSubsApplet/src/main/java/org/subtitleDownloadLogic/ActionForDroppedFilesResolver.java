package org.subtitleDownloadLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.OutputListener;
import org.subtitleDownloadLogic.utils.Downloader;
import org.subtitleDownloadLogic.utils.IOUtils;
import org.subtitleDownloadLogic.utils.Uploader;

public class ActionForDroppedFilesResolver {

	public static final int MAX_UP_SIZE_IN_BYTES = 1000000;
	private static final String SUBTITLE_EXTENSION = "srt";
	private final static String[] VIDEO_EXTENSIONS = "mpeg,mpg,avi,mov,wmv,rm,rmvb,mp4,3gp,ogm,ogg,mkv,asf".split(",");
	private final Downloader downloader;
	private final Uploader uploader;
	
	public ActionForDroppedFilesResolver(final List<File> droppedList,final Downloader downloader, final Uploader uploader,final OutputListener outputListener, final boolean onSeparateThread) {
		this.downloader = downloader;
		this.uploader = uploader;
		final List<File> videoFiles = new ArrayList<File>();
		
		final Runnable processFile = new Runnable() {
			@Override
			public void run() {
				outputListener.info("Processando "+droppedList);
				for (final File fileOrDir : droppedList) {
					sortFiles(outputListener, videoFiles, fileOrDir);
				}
				for (final File videoFile : videoFiles) {
					downloadAndUploadSubtitles(outputListener, videoFile);
				}
				outputListener.info("Terminado");
			};
		};
		
		if(onSeparateThread){
			final Thread processFileThread = new Thread(processFile);
			processFileThread.start();
		}else{
			processFile.run();
		}
	}
	
	public ActionForDroppedFilesResolver(final List<File> droppedList,final Downloader downloader, final Uploader uploader,final OutputListener outputListener) {
		this(droppedList,downloader,uploader,outputListener,true);
	}

	private void downloadAndUploadSubtitles(final OutputListener outputListener, 
			final File videoFile) {
		final File sub = getSubtitleForVideoOnSubtitleListOrNull(videoFile);
		if(sub == null){
			downloader.download(outputListener, videoFile);
		}else{
			if(sub.length()>MAX_UP_SIZE_IN_BYTES){
				outputListener.error("O arquivo de legenda é muito grande.");
			}
			uploader.upload(outputListener,new VideoWithSubtitle(videoFile, sub));
		}
	}

	private void sortFiles(final OutputListener outputListener,
			final List<File> videoFiles,
			final File fileOrDir) {
		if(fileOrDir.isDirectory()){
			final File[] filesInDir = fileOrDir.listFiles();
			for (final File file : filesInDir) {
				sortFiles(outputListener,videoFiles,file);
			}
			return;
		}
		final File file = fileOrDir;
		final String extension = IOUtils.getExtension(file.getName()).toLowerCase();
		for (final String allowedVideoExtension : VIDEO_EXTENSIONS) {
			if(extension.equals(allowedVideoExtension)){					
				videoFiles.add(file);
			}
		}
	}

	private File getSubtitleForVideoOnSubtitleListOrNull(final File videoFile) {
		final String videoFileWithoutExtension = IOUtils.removeExtension(videoFile.getAbsolutePath());
		final File subFile = new File(videoFileWithoutExtension+"."+SUBTITLE_EXTENSION);
		if(!subFile.exists()){
			return null;
		}
		return subFile;
	}
}
