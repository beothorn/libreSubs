package libreSubs.libreSubsSite.uploadPage;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "upload")
public class UploadSubtitle extends WebPage {
	public UploadSubtitle() {
		add(new Label("message", "Fazer upload de legenda"));


		final SubtitleUploadForm simpleUploadForm = new SubtitleUploadForm(
				"simpleUpload", new SubtitleUploadFormModelProperties());

		// Create feedback panels
		final FeedbackPanel uploadFeedback = new FeedbackPanel("uploadFeedback");

        // Add uploadFeedback to the page itself
		add(uploadFeedback);
		add(simpleUploadForm);
	}
}