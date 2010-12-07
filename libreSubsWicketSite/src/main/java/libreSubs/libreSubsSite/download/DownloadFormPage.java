package libreSubs.libreSubsSite.download;

import libreSubs.libreSubsSite.SubParameters;
import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.libreSubsApplet.utils.SubtitleResourceResolver;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "downloadForm")
public class DownloadFormPage extends WebPage {

	private SubParameters downloadParameters;

	public DownloadFormPage() {
		setStatelessHint(true);
		add(new MenuPanel("menu"));
		addSubtitleDownloadForm();
		add(new Label("siteBaseURL", WicketApplication.getBasePath()));
	}

	@SuppressWarnings("serial")
	private void addSubtitleDownloadForm() {
		downloadParameters = new SubParameters();
		final StatelessForm<String> form = new StatelessForm<String>(
				"inputForm", new CompoundPropertyModel<String>(
						downloadParameters)) {
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
						+ "&"
						+ fileParam
						+ "="
						+ downloadParameters.fileName;
				getRequestCycle().setRequestTarget(
						new RedirectRequestTarget(subRequestURL));
			}
		};
		add(form);
		form.add(new TextField<String>("id"));
		form.add(new TextField<String>("fileName"));
		form.add(new LanguageChooserDropDown("lang"));
	}

}
