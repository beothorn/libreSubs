package org.libreSubsApplet.utils;

import java.io.File;
import java.io.IOException;

import org.libreSubsApplet.OutputListener;
import org.libreSubsApplet.VideoWithSubtitle;

public class Uploader {

	private final String uploadUrl;
	private String subtitleLanguage;

	public Uploader(final String uploadUrl, final String subtitleLanguage) {
		this.uploadUrl = uploadUrl;
		this.setSubtitleLanguage(subtitleLanguage);
	}

	private void postSubtitle(final String id, final String lang, final File subtitle) throws IOException {
		final ClientHttpRequest clientHttpRequest = new ClientHttpRequest(uploadUrl);
		clientHttpRequest.setParameter(SubtitleResourceResolver.idParameter, id);
		clientHttpRequest.setParameter(SubtitleResourceResolver.langParameter, lang);
		clientHttpRequest.setParameter(SubtitleResourceResolver.fileParameter, subtitle);
		clientHttpRequest.post();
	}
	
	public void upload(final OutputListener outputListener,final VideoWithSubtitle videoWithSubtitle) {
		final String shaHex; 
		final File video = videoWithSubtitle.getVideo();
		try {
			shaHex = IOUtils.getPartialSHA1ForFile(video);
		} catch (final SHA1CalculationException e) {
			outputListener.error("Erro calculando SHA1; " + e.getMessage());
			return;
		}
		try {
			postSubtitle(shaHex,getSubtitleLanguage(),videoWithSubtitle.getSubtitle());
		} catch (final IOException e) {
			outputListener.error(e.getMessage());
		}
		outputListener.info("Legenda de " + video.getName() + " enviada. Id: "+shaHex);
	}

	public void setSubtitleLanguage(final String subtitleLanguage) {
		this.subtitleLanguage = subtitleLanguage;
	}

	public String getSubtitleLanguage() {
		return subtitleLanguage;
	}

}
