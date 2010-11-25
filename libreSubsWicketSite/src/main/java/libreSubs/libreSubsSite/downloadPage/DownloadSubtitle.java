package libreSubs.libreSubsSite.downloadPage;

import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.apache.wicket.protocol.http.WebResponse;
import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;

@SuppressWarnings("serial")
public class DownloadSubtitle extends DynamicWebResource {

	public static final String RESOURCE_NAME = "downloadSrt";
	
	public DownloadSubtitle() {
		setCacheable(false);
	}

	public static String getDownloadURLPath() {
		return WicketApplication.getBasePath() + RESOURCE_NAME;
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

		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();

		final String subtitle = subtitlesRepositoryHandler.getSubtitleOrNull(
				parameters
				.getId(), language);

		if (subtitle == null) {
			return new ErrorResourceState("Legenda não encontrada.");
		}

		return new ResourceState() {

			@Override
			public byte[] getData() {
				return subtitle.getBytes();
			}

			@Override
			public String getContentType() {
				return "text/srt";
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
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		final boolean subtitleDoesnExist = !subtitlesRepositoryHandler
				.subtitleExists(
				parameters.getId(), language);

		return isLackingParameters || languageUnknown || subtitleDoesnExist;
	}
}