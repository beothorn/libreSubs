package org;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.subtitleDownloadLogic.DroppedFilesProcessor;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

public class MainCommandLine {

	public static void main(final String[] args) {
		final String downloadUrl = "http://www.libresubs.com:8080/latestLibresubs/download?id=%id&lang=%lang";
		final String uploadUrl = "http://www.libresubs.com:8080/latestLibresubs/upload";
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(downloadUrl, uploadUrl);
		final CommandLineOutput outputListener = new CommandLineOutput();
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
