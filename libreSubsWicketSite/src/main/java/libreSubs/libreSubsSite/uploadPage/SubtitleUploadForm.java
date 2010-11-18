package libreSubs.libreSubsSite.uploadPage;

import java.io.File;

import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.libreSubsCommons.Language;
import org.libreSubsCommons.SHA1Utils;

/**
 * Form for uploads.
 */
@SuppressWarnings("serial")
public class SubtitleUploadForm extends StatelessForm<String> {

	private static final int MAX_SUB_SIZE = 200;
	private final FileUploadField fileUploadField;
	private final TextField<String> sha1Field;
	private final DropDownChoice<String> localeSelection;
	private final SubtitleUploadFormModelProperties formProperties;

	public SubtitleUploadForm(
			final String name,
			final SubtitleUploadFormModelProperties subtitleUploadFormModelProperties) {
		super(name, new CompoundPropertyModel<String>(
				subtitleUploadFormModelProperties));
		this.formProperties = subtitleUploadFormModelProperties;
		setMultiPart(true);
		fileUploadField = new FileUploadField("fileInput");
		setMaxSize(Bytes.kilobytes(MAX_SUB_SIZE));

		sha1Field = new TextField<String>("id");
		localeSelection = new DropDownChoice<String>("lang", Language
				.getLanguagesAsStringList());

		add(fileUploadField);
		add(sha1Field);
		add(localeSelection);
	}

	@Override
	protected void onSubmit() {
		if (formProperties.id == null) {
			info("SHA1 dos primeiros "
					+ SHA1Utils.getPartialSHA1SizeAsHumanReadable()
					+ " devem ser informados.");
		}

		if (!Language.isValidLanguage(formProperties.lang)) {
			info("Idioma inválido.");
			return;
		}

		if (WicketApplication.subtitleExists(formProperties.id,
				formProperties.lang)) {
			info("Legenda já existe.");
			return;
		}

		if (formProperties.fileInput == null) {
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
				throw new IllegalStateException("Unable to write file");
			}

			WicketApplication.addSubtitleFromFileAndDeleteIt(
					formProperties.id, formProperties.lang, newFile);
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