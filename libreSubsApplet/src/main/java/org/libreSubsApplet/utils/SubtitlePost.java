package org.libreSubsApplet.utils;

import java.io.File;
import java.io.IOException;

public class SubtitlePost {

	private final String uploadUrl;

	public SubtitlePost(final String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public void postSubtitle(final String id, final String lang, final File subtitle) throws IOException {
		final ClientHttpRequest clientHttpRequest = new ClientHttpRequest(uploadUrl);
		clientHttpRequest.setParameter(SubtitleResourceResolver.idParameter, id);
		clientHttpRequest.setParameter(SubtitleResourceResolver.langParameter, lang);
		clientHttpRequest.setParameter(SubtitleResourceResolver.fileParameter, subtitle);
		clientHttpRequest.post();
	}

}
