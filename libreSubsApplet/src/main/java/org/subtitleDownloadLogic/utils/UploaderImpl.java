package org.subtitleDownloadLogic.utils;

import java.io.File;
import java.io.IOException;

import org.OutputListener;
import org.subtitleDownloadLogic.VideoWithSubtitle;

public class UploaderImpl implements Uploader {

	private final String uploadUrl;
	private String subtitleLanguage;

	public UploaderImpl(final String uploadUrl, final String subtitleLanguage) {
		this.uploadUrl = uploadUrl;
		this.setSubtitleLanguage(subtitleLanguage);
	}

	private String postSubtitle(final String id, final String lang, final File subtitle) throws IOException {
		final ClientHttpRequest clientHttpRequest = new ClientHttpRequest(uploadUrl);
		clientHttpRequest.setParameter(SubtitleResourceResolver.idParameter, id);
		clientHttpRequest.setParameter(SubtitleResourceResolver.langParameter, lang);
		clientHttpRequest.setParameter(SubtitleResourceResolver.fileParameter, subtitle);
		clientHttpRequest.setParameter(SubtitleResourceResolver.commandLineParameter, "true");
		return IOUtils.convertStreamToString(clientHttpRequest.post());
	}
	
	/* (non-Javadoc)
	 * @see org.subtitleDownloadLogic.utils.Uploader#upload(org.OutputListener, org.subtitleDownloadLogic.VideoWithSubtitle)
	 */
	@Override
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
			final String postSubtitleAnswer = postSubtitle(shaHex,getSubtitleLanguage(),videoWithSubtitle.getSubtitle());
			outputListener.info("Legenda de " + video.getName() + " Id: "+shaHex+" "+postSubtitleAnswer);
		} catch (final IOException e) {
			outputListener.error(e.toString());
		}
	}

	public void setSubtitleLanguage(final String subtitleLanguage) {
		this.subtitleLanguage = subtitleLanguage;
	}

	public String getSubtitleLanguage() {
		return subtitleLanguage;
	}

}
