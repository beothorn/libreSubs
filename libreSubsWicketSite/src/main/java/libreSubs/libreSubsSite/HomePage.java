package libreSubs.libreSubsSite;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.libreSubsCommons.Language;
import org.libreSubsCommons.SubtitleResourceResolver;

public class HomePage extends WebPage {

	public String sha1;
	public String localeSelect;
	public String fileName;

	public HomePage() {

		addSubtitleFinderApplet();

		addSubtitlesListPrintForDebug();
		addSubtitleSearchForm();
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

	private void addSubtitlesListPrintForDebug() {
		add(new Label("message", "SubList: "
				+ WicketApplication.listSubtitles()));
	}

	@SuppressWarnings("serial")
	private void addSubtitleSearchForm() {
		final Form<String> form = new Form<String>("inputForm",
				new CompoundPropertyModel<String>(this)) {
			@Override
			protected void onSubmit() {

				final String idParam = SubtitleResourceResolver.idParameter;
				final String langParam = SubtitleResourceResolver.langParameter;
				final String fileParam = SubtitleResourceResolver.fileParameter;

				final String subRequestURL = "/sub?" + idParam + "=" + sha1
						+ "&" + langParam + "=" + localeSelect + "&"
						+ fileParam + "=" + fileName;
				getRequestCycle().setRequestTarget(
						new RedirectRequestTarget(subRequestURL));
			}
		};
		add(form);
		form.add(new TextField<String>("sha1"));
		form.add(new TextField<String>("fileName"));
		form.add(new DropDownChoice<String>("localeSelect", Language
				.getLanguagesAsStringList()));
	}

}
