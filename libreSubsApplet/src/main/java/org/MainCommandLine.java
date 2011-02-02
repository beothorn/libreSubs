package org;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.subtitleDownloadLogic.DroppedFilesProcessor;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

public class MainCommandLine {

	public static void main(final String[] args) {
		final OutputListener outputListener = new WindowOutputListener();
		final String downloadUrl = SiteAdresses.getDownloadurl();
		final String uploadUrl = SiteAdresses.getUploadurl();
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(downloadUrl, uploadUrl);
		final String defaulLocale = Locale.getDefault().toString();
		final DroppedFilesProcessor dropFileListener = new DroppedFilesProcessor(srtSource, outputListener, defaulLocale);
		final ArrayList<File> fileAndDirList = new ArrayList<File>();
		for (final String fileOrDirPath : args) {
			final File fileOrDir = new File(fileOrDirPath);
			if(fileOrDir.exists()){
				fileAndDirList.add(fileOrDir);
			}else{
				outputListener.error("Caminho inválido ou inexistente: "+fileOrDir);
			}
		}
		dropFileListener.droppedFiles(fileAndDirList);
	}

}
