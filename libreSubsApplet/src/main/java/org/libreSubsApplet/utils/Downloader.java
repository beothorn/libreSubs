package org.libreSubsApplet.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.libreSubsApplet.DownloadedContent;
import org.libreSubsApplet.OutputListener;

public class Downloader {

	private final SubtitleResourceResolver subtitleSource;
	private String subtitleLanguage;

	public Downloader(final SubtitleResourceResolver subtitleSource, final String subtitleLanguage) {
		this.subtitleSource = subtitleSource;
		this.subtitleLanguage = subtitleLanguage;
	}
	
	private DownloadedContent downloadFromURLToString(final String address)throws IOException {
		final URL url = new URL(address);
		final URLConnection conn = url.openConnection();
		final String contentType = conn.getContentType();
		final String content = IOUtils.convertStreamToString(conn.getInputStream());
		return new DownloadedContent(content, contentType);
	}
	
	public void download(final OutputListener outputListener, final File video) {
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
		final String subtitleUrl = subtitleSource.resolve(shaHex, getSubtitleLanguage(), newStrFileName);

		final DownloadedContent downloadedContent;

		try {
			downloadedContent = downloadFromURLToString(subtitleUrl);
			if (downloadedContent.isError()) {
				final String niceErrorMsg = "Ocorreu um erro ao tentar baixar a legenda de "+fileName+" : "+ downloadedContent.getContent();
				outputListener.error(niceErrorMsg);
				return;
			}
		} catch (final Exception e) {
			final String exceptionErrorMsg = "Ocorreu um erro ao tentar baixar a legenda de "+fileName+" : "
					+ e.getMessage();
			outputListener.error(exceptionErrorMsg);
			return;
		}

		final File srtFile = new File(parent, newStrFileName);
		try {
			IOUtils.writeStringToFile(srtFile,
					downloadedContent.getContent());
			outputListener.info("Legenda de "+fileName+" salva com sucesso");
		} catch (final IOException e) {
			srtFile.delete();
			outputListener
					.error("Ocorreu um erro ao tentar escreve arquivo de legenda: "
							+ e.getMessage());
		}
	}

	public void setSubtitleLanguage(final String subtitleLanguage) {
		this.subtitleLanguage = subtitleLanguage;
	}

	public String getSubtitleLanguage() {
		return subtitleLanguage;
	}

}
