package libreSubs.libreSubsSite.editPage;



import libreSubs.libreSubsSite.ErrorPage;
import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.libreSubsCommons.SubtitleResourceResolver;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "editSrtPage")
public class EditSubtitlePage extends WebPage {
	
	
	String subtitle;

	@SuppressWarnings("serial")
	public EditSubtitlePage(final PageParameters parameters) {
		final CharSequence idParam = parameters.getCharSequence(SubtitleResourceResolver.idParameter);
		if(idParam == null){
			setResponsePage(new ErrorPage() {				
				@Override
				protected String errorMessage() {
					return "Parametro "+SubtitleResourceResolver.idParameter+" não foi passado.";
				}
			});
			return;
		}
		final CharSequence langParam = parameters.getCharSequence(SubtitleResourceResolver.langParameter);
		if(langParam == null){
			setResponsePage(new ErrorPage() {				
				@Override
				protected String errorMessage() {
					return "Parametro "+SubtitleResourceResolver.langParameter+" não foi passado.";
				}
			});
			return;
		}
		
		final String id = idParam.toString();
		final String lang = langParam.toString();
		
		if(!WicketApplication.subtitleExists(id, lang)){
			setResponsePage(new ErrorPage() {				
				@Override
				protected String errorMessage() {
					return "Legenda não existe.";
				}
			});
			return;
		}
		
		add(new Label("id", id));
		add(new Label("lang", lang));
		add(new FeedbackPanel("saveFeedback"));
		
		subtitle = WicketApplication.getSubtitleOrNull(id, lang);
		
		final Form<String> editForm = new Form<String>("editForm"){
			@Override
			protected void onSubmit() {
				info("Saved");
			}
		};
		
		add(editForm);
		final TextArea<String> editSubtitleTextArea = new TextArea<String>("subtitle",new CompoundPropertyModel<String>(
				subtitle));
		editForm.add(editSubtitleTextArea);
	}	

}
