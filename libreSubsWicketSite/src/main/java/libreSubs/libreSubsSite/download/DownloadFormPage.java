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
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
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
				getRequestCycle().setRequestTarget( new ResourceStreamRequestTarget(new DownloadSubtitle().getResourceStream()) );
			}

		};
		add(form);
		form.add(new TextField<String>("id"));
		form.add(new TextField<String>("fileName"));
		form.add(new LanguageChooserDropDown("lang"));
	}

}
