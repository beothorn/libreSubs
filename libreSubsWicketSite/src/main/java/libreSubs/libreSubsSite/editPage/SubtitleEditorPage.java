package libreSubs.libreSubsSite.editPage;



import java.io.IOException;

import libreSubs.libreSubsSite.ErrorPage;
import libreSubs.libreSubsSite.SubParameters;
import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;
import libreSubs.libreSubsSite.uploadPage.SubtitleUploadForm;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.libreSubsApplet.utils.SubtitleResourceResolver;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "editSrtPage")
public class SubtitleEditorPage extends WebPage {
	
	
	String subtitle;
	SubParameters subParameters;

	@SuppressWarnings("serial")
	public SubtitleEditorPage(final PageParameters parameters) {
		setStatelessHint(true);


		final CharSequence idParam = parameters.getCharSequence(SubtitleResourceResolver.idParameter);
		if(idParam == null){
			throw new RestartResponseException(new ErrorPage() {				
				@Override
				protected String errorMessage() {
					return "Parametro " + SubtitleResourceResolver.idParameter
							+ " não foi passado.";
				}
			}); 
		}
		
		final CharSequence langParam = parameters.getCharSequence(SubtitleResourceResolver.langParameter); 
		if(langParam == null){
			throw new RestartResponseException(new ErrorPage() {				
				@Override
				protected String errorMessage() {
					return "Parametro "
							+ SubtitleResourceResolver.langParameter
							+ " não foi passado.";
				}
			});
		}
		
		final String id = idParam.toString();
		final String lang = langParam.toString();
		
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();

		if (!subtitlesRepositoryHandler.subtitleExists(id, lang)) {
			throw new RestartResponseException(new ErrorPage() {				
				@Override
				protected String errorMessage() {
					return "Legenda não existe.";
				}
			});
		}
		
		add(new MenuPanel("menu"));
		add(new Label("id", id));
		add(new Label("lang", lang));
		add(new FeedbackPanel("saveFeedback"));
		
		subtitle = subtitlesRepositoryHandler.getSubtitleOrNull(id, lang);
		
		subParameters = new SubParameters();
		final Form<String> editForm = new Form<String>("editForm",new CompoundPropertyModel<String>(
				subParameters)){
			@Override
			protected void onSubmit() {
				try {
					subtitlesRepositoryHandler.changeContentsForSubitle(id,
							lang, subParameters.content);
				} catch (final IOException e) {
					info("Erro ao enviar legenda");
					Logger.getLogger(SubtitleUploadForm.class).error(
							"Erro ao enviar legenda", e);
					return;
				}
				info("Legenda alterada");
			}
		};
		
		add(editForm);
		subParameters.content = subtitle;
		final TextArea<String> editSubtitleTextArea = new TextArea<String>("content");
		editForm.add(editSubtitleTextArea);
	}	

}
