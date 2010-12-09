package libreSubs.libreSubsSite.download;

import libreSubs.libreSubsSite.CommonsParameters;
import libreSubs.libreSubsSite.TextResource;
import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.DynamicWebResource;
import org.apache.wicket.protocol.http.WebResponse;
import org.libreSubsApplet.utils.LocaleUtil;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;

@SuppressWarnings("serial")
public class DownloadSubtitle extends DynamicWebResource {

	public static final String RESOURCE_NAME = "download";
	
	public DownloadSubtitle() {
		setCacheable(false);
	}

	public static String getDownloadURLPath() {
		return WicketApplication.getBasePath() + RESOURCE_NAME;
	}

	@Override
	protected DynamicWebResource.ResourceState getResourceState() {
		final RequestCycle cycle = RequestCycle.get();
		final WebResponse response = (WebResponse)cycle.getResponse();
		setHeaderForSubtitle(response);
		
		final CommonsParameters parameters = new CommonsParameters(getParameters());
		if (!parameters.hasAllObrigatoryParameters()) {
			return new TextResource(
					"Os seguintes parâmetros devem ser informados: "
							+ parameters.getLackingParametersNames());
		}

		final String language = parameters.getLanguage();
		if (!LocaleUtil.isValidLanguage(language)) {
			return new TextResource("O idioma " + language
					+ " não é suportado.");
		}

		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();

		final String subtitle = subtitlesRepositoryHandler.getSubtitleOrNull(
				parameters
				.getId(), language);

		if (subtitle == null) {
			return new TextResource("Legenda não encontrada.");
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

	private void setHeaderForSubtitle(final WebResponse response) {
		final CommonsParameters parameters = new CommonsParameters(
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
			final CommonsParameters parameters) {
		final String language = parameters.getLanguage();
		final boolean isLackingParameters = !parameters
				.hasAllObrigatoryParameters();
		final boolean languageUnknown = !LocaleUtil.isValidLanguage(language);
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		final boolean subtitleDoesnExist = !subtitlesRepositoryHandler
				.subtitleExists(
				parameters.getId(), language);

		return isLackingParameters || languageUnknown || subtitleDoesnExist;
	}
}