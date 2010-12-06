package libreSubs.libreSubsSite.editPage;



import libreSubs.libreSubsSite.ErrorPage;
import libreSubs.libreSubsSite.SubParameters;
import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.libreSubsApplet.utils.SubtitleResourceResolver;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "editSrtPage")
public class SubtitleEditorPage extends WebPage {
	
	String subtitle;
	SubParameters subParameters;

	public SubtitleEditorPage(final PageParameters parameters) {
		setStatelessHint(true);

		final CharSequence idParam = parameters.getCharSequence(SubtitleResourceResolver.idParameter);
		if(idParam == null){
			ErrorPage.redirectToError("Parametro "
					+ SubtitleResourceResolver.idParameter
					+ " não foi passado.");
		}
		
		final CharSequence langParam = parameters.getCharSequence(SubtitleResourceResolver.langParameter); 
		if(langParam == null){
			ErrorPage.redirectToError("Parametro "
					+ SubtitleResourceResolver.langParameter
					+ " não foi passado.");
		}
		
		final String id = idParam.toString();
		final String lang = langParam.toString();
		final String message = "";
		
		buildPage(id, lang, message);
	}

	@SuppressWarnings("serial")
	private void buildPage(final String id, final String lang,
			final String message) {
		add(new Label("message", message));
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();

		if (!subtitlesRepositoryHandler.subtitleExists(id, lang)) {
			ErrorPage.redirectToError("Legenda não existe.");
		}
		
		add(new MenuPanel("menu"));
		add(new Label("id", id));
		add(new Label("lang", lang));
		
		subtitle = subtitlesRepositoryHandler.getSubtitleOrNull(id, lang);
		
		subParameters = new SubParameters();
		final StatelessForm<String> editForm = new StatelessForm<String>(
				"editForm",
				new CompoundPropertyModel<String>(
				subParameters)){
			@Override
			protected void onSubmit() {
				setResponsePage(new CommitPage(id, lang, subParameters.content));
			}
		};
		
		add(editForm);
		subParameters.content = subtitle;
		final TextArea<String> editSubtitleTextArea = new TextArea<String>("content");
		editForm.add(editSubtitleTextArea);
	}

	public SubtitleEditorPage(final String id, final String lang,
			final String message) {
		buildPage(id, lang, message);
	}	

}
