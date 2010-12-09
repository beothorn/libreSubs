package libreSubs.libreSubsSite.editPage;

import java.io.IOException;

import libreSubs.libreSubsSite.TextPage;
import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;

public class CommitPage extends WebPage {

	private final String id;
	private final String lang;
	private final String content;

	public CommitPage(final String id, final String lang, final String content) {
		this.id = id;
		this.lang = lang;
		this.content = content;
		add(new MenuPanel("menu"));
		addCommitForm();
	}

	private void commit(final String commiter, final String email,
			final String message,
			final String id,
			final String lang, final String content) {
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();

		try {
			subtitlesRepositoryHandler.changeContentsForSubitle(commiter,
					email,
					message, id,
					lang,
					content);
		} catch (final IOException e) {
			Logger.getLogger(CommitPage.class).error(
					"Erro ao enviar legenda", e);
			TextPage.redirectToPageWithText("Erro ao enviar legenda");
		}
	}
	
	@SuppressWarnings("serial")
	private void addCommitForm() {
		final CommitInformation commitInformation = new CommitInformation();
		final StatelessForm<String> form = new StatelessForm<String>(
				"commitForm",new CompoundPropertyModel<String>(commitInformation)) {
			@Override
			protected void onSubmit() {
				commit(commitInformation.commiter, commitInformation.email,
						commitInformation.message,
						id, lang, content);
				setResponsePage(new SubtitleEditorPage(id, lang,
						"Legenda foi alterada"));
			}
		};
		add(form);
		form.add(new TextField<String>("commiter"));
		form.add(new TextField<String>("email"));
		form.add(new TextField<String>("message"));
	}
}
