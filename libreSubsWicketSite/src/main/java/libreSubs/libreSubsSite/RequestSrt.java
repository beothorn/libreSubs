package libreSubs.libreSubsSite;

import java.io.IOException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.libreSubsEngine.Language;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SHA1;
import org.libreSubsEngine.subtitleRepository.repository.SubtitleKey;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

public class RequestSrt extends WebPage {

	public RequestSrt() {
		this(null);
	}

	public RequestSrt(final PageParameters parameters) {
		final SubtitleRepositoryLocation subtitleDefaultRepository = new SubtitleDefaultRepository();
		final SubtitlesRepository subtitlesRepository = new SubtitlesRepository(
				subtitleDefaultRepository);
		try {
			new SubtitleRepositoryLoader(subtitlesRepository);
		} catch (final IOException e) {
			setResponsePage(new ErrorPage("Could not load subtitle repository."));
		}

		// final Object lang = parameters.get("lang");
		// final Object sha1 = parameters.get("sha1");
		// final Object srt = parameters.get("srt");
		// System.out.println("" + lang + sha1 + srt + "");

		try {
			final SubtitleKey subtitleKey = new SubtitleKey(Language
					.valueOf("pt_BR"), new SHA1("1111"));

			new SubtitleResource(subtitlesRepository, subtitleKey, "rg");

		} catch (final IOException e) {
			setResponsePage(new ErrorPage("Could not load subtitle."));
			return;
		}
	}

}
