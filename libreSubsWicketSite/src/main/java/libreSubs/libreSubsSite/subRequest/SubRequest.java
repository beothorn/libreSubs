package libreSubs.libreSubsSite.subRequest;

import java.io.UnsupportedEncodingException;

import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.apache.wicket.protocol.http.WebResponse;
import org.libreSubsCommons.Language;

@SuppressWarnings("serial")
public class SubRequest extends DynamicWebResource {

	public SubRequest() {
		setCacheable(false);
	}

	@Override
	protected DynamicWebResource.ResourceState getResourceState() {

		final SubRequestParameters parameters = new SubRequestParameters(
				getParameters());
		if (!parameters.hasAllObrigatoryParameters()) {
			return new ErrorResourceState(
					"Os seguintes parâmetros devem ser informados: "
							+ parameters.getLackingParametersNames());
		}

		final String language = parameters.getLanguage();
		if (!Language.isValidLanguage(language)) {
			return new ErrorResourceState("O idioma " + language
					+ " não é suportado.");
		}

		final String subtitle = WicketApplication.getSubtitleOrNull(parameters
				.getId(), language);

		if (subtitle == null) {
			return new ErrorResourceState("Legenda não encontrada.");
		}

		return new ResourceState() {

			@Override
			public byte[] getData() {
				try {
					return subtitle.getBytes("UTF-8");
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
		final SubRequestParameters parameters = new SubRequestParameters(
				getParameters());

		if (subtitleRequestWillFail(parameters)) {
			return;
		}

		final String filename = parameters.getFile();
		if (filename == null)
			response.setAttachmentHeader("legenda.srt");
		else
			response.setAttachmentHeader(filename);
	}

	private boolean subtitleRequestWillFail(
			final SubRequestParameters parameters) {
		final String language = parameters.getLanguage();
		final boolean isLackingParameters = !parameters
				.hasAllObrigatoryParameters();
		final boolean languageUnknown = !Language.isValidLanguage(language);
		final boolean subtitleDoesnExist = !WicketApplication.subtitleExists(
				parameters.getId(), language);

		return isLackingParameters || languageUnknown || subtitleDoesnExist;
	}
}