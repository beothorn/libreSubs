package org.subtitleDownloadLogic;

public class DownloadedContent {
	
	private final String content;
	private final String contentType;

	public DownloadedContent(final String content, final String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public boolean isError() {
		return ! contentType.contains("text/srt");
	}

}
