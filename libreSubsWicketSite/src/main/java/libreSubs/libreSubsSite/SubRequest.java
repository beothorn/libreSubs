package libreSubs.libreSubsSite;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.util.value.ValueMap;
import org.libreSubsCommons.Language;
import org.libreSubsCommons.SubtitleResourceResolver;
import org.libreSubsEngine.subtitleRepository.repository.SHA1;
import org.libreSubsEngine.subtitleRepository.repository.SubtitleKey;

@SuppressWarnings("serial")
public class SubRequest extends DynamicWebResource {

	@Override
	protected DynamicWebResource.ResourceState getResourceState() {
		return new ResourceState() {
			@Override
			public byte[] getData() {
				try {
					final ValueMap parameters = getParameters();
					final String idParam = parameters.getCharSequence(
							SubtitleResourceResolver.idParameter).toString();
					final String langParam = parameters.getCharSequence(
							SubtitleResourceResolver.langParameter).toString();

					final SubtitleKey subtitleKey = new SubtitleKey(Language
							.valueOf(langParam), new SHA1(idParam));
					String subtitleContentsForKey;
					try {
						subtitleContentsForKey = WicketApplication.subtitles
								.getSubtitleContentsForKey(subtitleKey);
					} catch (final IOException e) {
						throw new RuntimeException(e);
					}
					return subtitleContentsForKey.getBytes("UTF-8");
				} catch (final UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}

			}

			@Override
			public String getContentType() {
				return "text/str";
			}
		};
	}

	@Override
	protected void setHeaders(final WebResponse response) {
		final ValueMap parameters = getParameters();
		final String filename = parameters.getCharSequence(
				SubtitleResourceResolver.fileParameter).toString();
		response.setAttachmentHeader(filename);
	}
}