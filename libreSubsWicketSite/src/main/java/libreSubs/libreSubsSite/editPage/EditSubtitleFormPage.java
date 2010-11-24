package libreSubs.libreSubsSite.editPage;

import libreSubs.libreSubsSite.SubParameters;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.libreSubsCommons.Language;
import org.libreSubsCommons.SubtitleResourceResolver;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "edit")
public class EditSubtitleFormPage extends WebPage {
	
	private final SubParameters downloadParameters;
	
	@SuppressWarnings("serial")
	public EditSubtitleFormPage() {
		setStatelessHint(true);
		downloadParameters = new SubParameters();
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
		add(new MenuPanel("menu"));
		form.add(new TextField<String>("id"));
		form.add(new DropDownChoice<String>("lang", Language
				.getLanguagesAsStringList()));
		
		add(form);
	}

}
