package org.libreSubsApplet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.libreSubsApplet.dropFile.DropFileListener;
import org.libreSubsApplet.utils.IOUtils;
import org.libreSubsApplet.utils.SHA1CalculationException;
import org.libreSubsApplet.utils.SubtitleResourceResolver;

public class DroppedFilesProcessor implements DropFileListener {

	private final SubtitleResourceResolver subtitleSource;
	private final OutputListener outputListener;
	private String subtitleLanguage;

	public DroppedFilesProcessor(final SubtitleResourceResolver subtitleSource,
			final OutputListener outputListener, final String subtitleLanguage) {
		this.subtitleSource = subtitleSource;
		this.outputListener = outputListener;
		this.subtitleLanguage = subtitleLanguage;
	}

	@Override
	public void droppedFiles(final List<File> files) {

		final ActionForDroppedFilesResolver actionForDroppedFiles = new ActionForDroppedFilesResolver(
				files, outputListener);

		tryToDownloadSubtitlesForVideos(actionForDroppedFiles
				.getVideosToDownloadSubtitles());
		uploadFiles(actionForDroppedFiles.getFilesToUpload());
	}

	private void uploadFiles(final List<VideoWithSubtitle> filesToUpload) {
		for (final VideoWithSubtitle videoWithSubtitle : filesToUpload) {
			showUploadNotImplementedMessage(videoWithSubtitle);
		}
	}

	private void showUploadNotImplementedMessage(
			final VideoWithSubtitle videoWithSubtitle) {
		final String shaHex; 
		try {
			shaHex = IOUtils.getPartialSHA1ForFile(videoWithSubtitle.getVideo());
		} catch (final SHA1CalculationException e) {
			outputListener.error("Erro calculando SHA1; " + e.getMessage());
			return;
		}
		outputListener.error("Upload ainda não foi implementado pelo applet.");
		outputListener.error("Use a página de upload com este id: "
				+ shaHex);
	}

	private void tryToDownloadSubtitlesForVideos(
			final List<File> videosAskingSubtitles) {
		for (final File video : videosAskingSubtitles) {
			tryToDownloadSubtitleForVideo(video);
		}
	}

	private void tryToDownloadSubtitleForVideo(final File video) {
		outputListener.info("Baixando legenda para " + video.getName());
		final String shaHex;
		
		try {
			shaHex = IOUtils.getPartialSHA1ForFile(video);
		} catch (final SHA1CalculationException e1) {
			outputListener.error("Erro calculando SHA1; " + e1.getMessage());
			return;
		}

		final String fileName = video.getName();
		final File parent = video.getParentFile();
		final String newStrFileName = IOUtils.removeExtension(fileName)+ ".srt";
		final String subtitleUrl = subtitleSource.resolve(shaHex, getLanguage(), newStrFileName);
		
		outputListener.info("Arquivo: " + fileName + " - SHA1: " + shaHex);
		outputListener.info("Url: "+subtitleUrl);

		final DownloadedContent downloadedContent;

		try {
			downloadedContent = downloadAdressForString(subtitleUrl);
			if (downloadedContent.isError()) {
				final String niceErrorMsg = "Ocorreu um erro ao tentar baixar a legenda: "+ downloadedContent.getContent();
				outputListener.error(niceErrorMsg);
				return;
			}
		} catch (final Exception e) {
			final String exceptionErrorMsg = "Ocorreu um erro ao tentar baixar a legenda: "
					+ e.getMessage();
			outputListener.error(exceptionErrorMsg);
			return;
		}

		final File srtFile = new File(parent, newStrFileName);
		try {
			IOUtils.writeStringToFile(srtFile,
					downloadedContent.getContent());
			outputListener.info("Legenda salva com sucesso");
		} catch (final IOException e) {
			srtFile.delete();
			outputListener
					.error("Ocorreu um erro ao tentar escreve arquivo de legenda: "
							+ e.getMessage());
		}
	}

	private DownloadedContent downloadAdressForString(final String address)
			throws IOException {
		final URL url = new URL(address);
		final URLConnection conn = url.openConnection();
		final String contentType = conn.getContentType();
		final String content = IOUtils.convertStreamToString(conn.getInputStream());
		return new DownloadedContent(content, contentType);
	}

	public void setLanguage(final String language) {
		this.subtitleLanguage = language;
	}

	public String getLanguage() {
		return subtitleLanguage;
	}

}
