package libreSubs.libreSubsSite;

import java.io.IOException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {

		SubtitleDefaultRepository subtitleDefaultRepository = new SubtitleDefaultRepository();
		SubtitlesRepository subtitlesRepository = new SubtitlesRepository(
				subtitleDefaultRepository.getBaseDir());
		try {
			new SubtitleRepositoryLoader(subtitlesRepository);
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Add the simplest type of label
		add(new Label("message", "SubList: "
				+ subtitlesRepository.listSubtitles()));

        // TODO Add your page's components here
    }
}
