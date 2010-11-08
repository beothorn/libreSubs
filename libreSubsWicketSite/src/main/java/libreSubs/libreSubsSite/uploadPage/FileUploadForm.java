package libreSubs.libreSubsSite.uploadPage;

import java.io.File;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;

/**
 * Form for uploads.
 */
@SuppressWarnings("serial")
public class FileUploadForm extends Form<Void> {

	private static final int MAX_SUB_SIZE = 200;
	private FileUploadField fileUploadField;

	/**
	 * Construct.
	 * 
	 * @param name
	 *            Component name
	 */
	public FileUploadForm(final String name) {
		super(name);

		// set this form to multipart mode (allways needed for uploads!)
		setMultiPart(true);

		// Add one file input field
		add(fileUploadField = new FileUploadField("fileInput"));

		// Set maximum size to 1000K for demo purposes
		setMaxSize(Bytes.kilobytes(MAX_SUB_SIZE));
	}

	/**
	 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
	 */
	@Override
	protected void onSubmit() {
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
		}
		info("File sent");
	}

	/**
	 * Check whether the file allready exists, and if so, try to delete it.
	 * 
	 * @param newFile
	 *            the file to check
	 */
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