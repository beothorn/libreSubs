package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.downloadPage.DownloadSubtitle;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;
import libreSubs.libreSubsSite.wicketComponents.DeployJava;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.libreSubsApplet.utils.SubtitleResourceResolver;
import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;

public class HomePage extends WebPage {

	private SubParameters downloadParameters;

	public HomePage() {
		setStatelessHint(true);
		add(new MenuPanel("menu"));

		addSubtitleFinderApplet();
		addSubtitlesListPrintForDebug();
		addSubtitleSearchForm();

		add(new Label("siteBaseURL", WicketApplication.getBasePath()));
	}

	private void addSubtitleFinderApplet() {
		if (WicketApplication.get().getConfigurationType().equals(
				WicketApplication.DEVELOPMENT)) {
			add(new Label("appletDiv", "APPLET GOES HERE"));
			return;
		}

		final DeployJava div = new DeployJava("appletDiv");
		div.setWidth(800);
		div.setHeight(500);
		div.setCode("org.libreSubsApplet.MainApplet.class");
		div.setCodebase(WicketApplication.getBasePath() + "applets");
		div.setArchive("subFinder.jar");
		final String idParam = SubtitleResourceResolver.idParameter;
		final String langParam = SubtitleResourceResolver.langParameter;
		div.addParameter("srtProviderURL", DownloadSubtitle
				.getDownloadURLPath()
				+ "?" + idParam + "=%id&" + langParam + "=%lang");
		div.setMinimalVersion("1.6");
		add(div);
	}

	private void addSubtitlesListPrintForDebug() {
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		add(new MultiLineLabel("message", "SubList: "
				+ subtitlesRepositoryHandler.listSubtitles()));
	}

	@SuppressWarnings("serial")
	private void addSubtitleSearchForm() {
		downloadParameters = new SubParameters();
		final StatelessForm<String> form = new StatelessForm<String>(
				"inputForm",
				new CompoundPropertyModel<String>(downloadParameters)) {
			@Override
			protected void onSubmit() {

				final String idParam = SubtitleResourceResolver.idParameter;
				final String langParam = SubtitleResourceResolver.langParameter;
				final String fileParam = SubtitleResourceResolver.fileParameter;

				final String subRequestURL = DownloadSubtitle
						.getDownloadURLPath()
						+ "?"
						+ idParam
						+ "="
						+ downloadParameters.id
						+ "&"
						+ langParam
						+ "="
						+ downloadParameters.lang
						+ "&" + fileParam + "=" + downloadParameters.fileName;
				getRequestCycle().setRequestTarget(
						new RedirectRequestTarget(subRequestURL));
			}
		};
		add(form);
		form.add(new TextField<String>("id"));
		form.add(new TextField<String>("fileName"));
		form.add(new DropDownChoice<String>("lang", Language
				.getLanguagesAsStringList()));
	}

}
