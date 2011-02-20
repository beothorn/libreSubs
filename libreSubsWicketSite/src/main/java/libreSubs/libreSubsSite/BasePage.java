package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;
import libreSubs.libreSubsSite.download.DownloadSubtitle;
import libreSubs.libreSubsSite.editPage.SubtitleEditorPage;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.IResourceStream;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

public abstract class BasePage extends WebPage {
	
	private final SubParameters subParameters;
	
	public BasePage() {
		subParameters = new SubParameters();
		setStatelessHint(true);
		addDownloadElements();
		addUploadElements();
		addEditElements();
	}

	private void addUploadElements() {
		add(new LanguageChooserDropDown("lang"));
	}

	private void addEditElements() {
		final CompoundPropertyModel<String> paramsCompoundPropertyModel = new CompoundPropertyModel<String>(subParameters);
		@SuppressWarnings("serial")
		final StatelessForm<String> form = new StatelessForm<String>(
				"editorCallForm",
				paramsCompoundPropertyModel) {
			@Override
			protected void onSubmit() {
				final String idParam = SubtitleResourceResolver.idParameter;
				final String langParam = SubtitleResourceResolver.langParameter;
				final PageParameters pageParameters = new PageParameters(idParam+"="+subParameters.id+","+langParam+"="+subParameters.lang);
				final SubtitleEditorPage editorPage = new SubtitleEditorPage(pageParameters);
				setResponsePage(editorPage);
			}
		};
		form.add(new TextField<String>("id"));
		form.add(new LanguageChooserDropDown("lang"));
		
		add(form);
	}

	@SuppressWarnings("serial")
	private void addDownloadElements() {
		final CompoundPropertyModel<String> paramsCompoundPropertyModel = new CompoundPropertyModel<String>(subParameters);
		final StatelessForm<String> form = new StatelessForm<String>("inputForm", paramsCompoundPropertyModel) {
			@Override
			protected void onSubmit() {
				final DownloadSubtitle downloadSubtitle = new DownloadSubtitle();
				final IResourceStream resourceStream = downloadSubtitle.getResourceStream();
				final ResourceStreamRequestTarget downloadRequestTarget = new ResourceStreamRequestTarget(resourceStream);
				final RequestCycle requestCycle = getRequestCycle();
				requestCycle.setRequestTarget(downloadRequestTarget);
			}

		};
		add(form);
		form.add(new TextField<String>("id"));
		form.add(new TextField<String>("fileName"));
		form.add(new LanguageChooserDropDown("lang"));
	}
}
