package libreSubs.libreSubsSite.uploadPage;

import java.io.File;
import java.io.IOException;

import libreSubs.libreSubsSite.SubParameters;
import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.libreSubsApplet.utils.IOUtils;
import org.libreSubsApplet.utils.LocaleUtil;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;

/**
 * Form for uploads.
 */
@SuppressWarnings("serial")
public class SubtitleUploadForm extends StatelessForm<String> {

	private static final int MAX_SUB_SIZE = 200;
	private final FileUploadField fileUploadField;
	private final TextField<String> sha1Field;
	private final SubParameters formProperties;

	public SubtitleUploadForm(
			final String name,
			final SubParameters subtitleUploadFormModelProperties) {
		super(name, new CompoundPropertyModel<String>(
				subtitleUploadFormModelProperties));
		this.formProperties = subtitleUploadFormModelProperties;
		setMultiPart(true);
		fileUploadField = new FileUploadField("fileName");
		setMaxSize(Bytes.kilobytes(MAX_SUB_SIZE));

		sha1Field = new TextField<String>("id");


		add(fileUploadField);
		add(sha1Field);
		add(new LanguageChooserDropDown("lang"));
	}

	@Override
	protected void onSubmit() {
		if (formProperties.id == null) {
			info("SHA1 dos primeiros "
					+ IOUtils.getPartialSHA1SizeAsHumanReadable()
					+ " devem ser informados.");
		}

		if (!LocaleUtil.isValidLanguage(formProperties.lang)) {
			info("Idioma inválido.");
			return;
		}

		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		if (subtitlesRepositoryHandler.subtitleExists(formProperties.id,
				formProperties.lang)) {
			info("Legenda já existe.");
			return;
		}

		if (formProperties.fileName == null) {
			info("Arquivo deve ser informado.");
		}

		final FileUpload upload = fileUploadField.getFileUpload();
		if (upload != null) {
			// Create a new file
			final File newFile = new File(getUploadFolder(), upload
					.getClientFileName());

			// Check new file, delete if it allready existed
			checkFileExists(newFile);
			try {
				// Save to new file
				newFile.createNewFile();
				upload.writeTo(newFile);
			} catch (final Exception e) {
				info("Erro ao salvar legenda na base");
				Logger.getLogger(SubtitleUploadForm.class).error(
						"Erro ao salvar legenda na base", e);
				return;
			}

			try {
				subtitlesRepositoryHandler.addSubtitleFromFileAndDeleteIt(
						formProperties.id, formProperties.lang, newFile);
			} catch (final IOException e) {
				info("Erro ao adicionar legenda a base");
				Logger.getLogger(SubtitleUploadForm.class).error(
						"Erro ao adicionar legenda a base", e);
				return;
			}
			info("Arquivo Enviado");
		} else {
			info("Arquivo inválido.");
		}

	}

	private void checkFileExists(final File newFile) {
		if (newFile.exists()) {
			// Try to delete the file
			if (!Files.remove(newFile)) {
				throw new IllegalStateException("Unable to overwrite "
						+ newFile.getAbsolutePath());
			}
		}
	}

	private Folder getUploadFolder() {
		return new Folder(System.getProperty("java.io.tmpdir"));
	}
}