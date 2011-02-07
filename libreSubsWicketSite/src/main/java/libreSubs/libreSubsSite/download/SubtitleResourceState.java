package libreSubs.libreSubsSite.download;

import java.nio.charset.Charset;

import org.apache.wicket.markup.html.DynamicWebResource.ResourceState;
import org.apache.wicket.protocol.http.WebResponse;
import org.subtitleDownloadLogic.utils.LocaleUtil;

public class SubtitleResourceState extends ResourceState {

	private final String subtitle;
	private final String encodingForLanguage;
	
	public SubtitleResourceState(final String subtitle, final String language, final WebResponse response) {
		this.subtitle = subtitle;
		encodingForLanguage = LocaleUtil.getEncodingForLanguage(language);
		response.setCharacterEncoding(encodingForLanguage);
	}
	
	@Override
	public byte[] getData() {
		final Charset charset = Charset.forName(encodingForLanguage);
		final byte[] subAsBytes = subtitle.getBytes(charset);
		return subAsBytes;
	}

	@Override
	public String getContentType() {
		return "text/srt";
	}	

}
