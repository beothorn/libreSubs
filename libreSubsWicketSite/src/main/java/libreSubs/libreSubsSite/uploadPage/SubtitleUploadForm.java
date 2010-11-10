package libreSubs.libreSubsSite.uploadPage;

import java.io.File;

import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
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
public class SubtitleUploadForm extends Form<String> {

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

		sha1Field = new TextField<String>("sha1");
		localeSelection = new DropDownChoice<String>("localeSelect", Language
				.getLanguagesAsStringList());

		add(fileUploadField);
		add(sha1Field);
		add(localeSelection);
	}

	@Override
	protected void onSubmit() {
		if (formProperties.sha1 == null) {
			info("SHA1 dos primeiros "
					+ SHA1Utils.getPartialSHA1SizeAsHumanReadable()
					+ " devem ser informados.");
		}

		if (!Language.isValidLanguage(formProperties.localeSelect)) {
			info("Idioma inválido.");
			return;
		}

		if (WicketApplication.subtitleExists(formProperties.sha1,
				formProperties.localeSelect)) {
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
					formProperties.sha1, formProperties.localeSelect, newFile);
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