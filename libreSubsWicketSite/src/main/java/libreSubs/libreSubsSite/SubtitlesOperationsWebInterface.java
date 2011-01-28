package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;
import libreSubs.libreSubsSite.download.DownloadSubtitle;
import libreSubs.libreSubsSite.editPage.SubtitleEditorPage;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

public class SubtitlesOperationsWebInterface extends BasePage {
	
	private final SubParameters downloadParameters;
	
	public SubtitlesOperationsWebInterface() {
		setStatelessHint(true);
		downloadParameters = new SubParameters();
		addSubtitleDownloadForm();
		add(new LanguageChooserDropDown("lang"));
		addSubtitleEditForm();
	}

	private void addSubtitleEditForm() {
		@SuppressWarnings("serial")
		final StatelessForm<String> form = new StatelessForm<String>(
				"editorCallForm",
				new CompoundPropertyModel<String>(downloadParameters)) {
			@Override
			protected void onSubmit() {

				final String idParam = SubtitleResourceResolver.idParameter;
				final String langParam = SubtitleResourceResolver.langParameter;

				final PageParameters pageParameters = new PageParameters(idParam+"="+downloadParameters.id+","+langParam+"="+downloadParameters.lang);
				
				setResponsePage(new SubtitleEditorPage(pageParameters));
			}
		};
		form.add(new TextField<String>("id"));
		form.add(new LanguageChooserDropDown("lang"));
		
		add(form);
	}

	@SuppressWarnings("serial")
	private void addSubtitleDownloadForm() {
		final StatelessForm<String> form = new StatelessForm<String>(
				"inputForm", new CompoundPropertyModel<String>(
						downloadParameters)) {
			@Override
			protected void onSubmit() {
				getRequestCycle().setRequestTarget(new ResourceStreamRequestTarget(new DownloadSubtitle().getResourceStream()));
			}

		};
		add(form);
		form.add(new TextField<String>("id"));
		form.add(new TextField<String>("fileName"));
		form.add(new LanguageChooserDropDown("lang"));
	}
}
