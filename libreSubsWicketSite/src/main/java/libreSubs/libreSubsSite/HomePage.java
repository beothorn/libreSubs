package libreSubs.libreSubsSite;

import java.io.IOException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SHA1;
import org.libreSubsEngine.subtitleRepository.repository.SubtitleKey;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	public String sha1;
	public String localeSelect;
	public String fileName;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {

		final SubtitleRepositoryLocation subtitleDefaultRepository = new SubtitleDefaultRepository();
		final SubtitlesRepository subtitlesRepository = new SubtitlesRepository(
				subtitleDefaultRepository);
		try {
			new SubtitleRepositoryLoader(subtitlesRepository);
		} catch (final IOException e) {
			setResponsePage(new ErrorPage("Could not load subtitle repository."));
		}

		addSubtitleFinderApplet();
		addSubtitlesListPrintForDebug(subtitlesRepository);
		addSubtitleSearchForm(subtitlesRepository);

	}

	private void addSubtitleFinderApplet() {

		final DeployJava div = new DeployJava("appletDiv");
		div.setWidth(800);
		div.setHeight(500);
		div.setCode("org.libreSubsApplet.MainApplet.class");
		div.setCodebase(WicketApplication.getBasePath() + "applets");
		div.setArchive("subFinder.jar");
		div.addParameter("srtProviderURL", WicketApplication.getBasePath()
				+ "?id=%id&lang=%lang&file=%file");
		div.setMinimalVersion("1.6");
		add(div);
	}

	private void addSubtitlesListPrintForDebug(
			final SubtitlesRepository subtitlesRepository) {
		add(new Label("message", "SubList: "
				+ subtitlesRepository.listSubtitles()));
	}

	@SuppressWarnings("serial")
	private void addSubtitleSearchForm(
			final SubtitlesRepository subtitlesRepository) {
		final Form<String> form = new Form<String>("inputForm",
				new CompoundPropertyModel<String>(this)) {
			@Override
			protected void onSubmit() {
				try {
					final SubtitleKey subtitleKey = new SubtitleKey(Language
							.valueOf(localeSelect), new SHA1(sha1));

					new SubtitleResource(subtitlesRepository, subtitleKey,
							fileName);

				} catch (final IOException e) {
					setResponsePage(new ErrorPage("Could not load subtitle."));
					return;
				}

			}
		};
		add(form);
		form.add(new TextField<String>("sha1"));
		form.add(new TextField<String>("fileName"));
		form.add(new DropDownChoice<String>("localeSelect", Language
				.getLanguagesAsStringList()));
	}

}
