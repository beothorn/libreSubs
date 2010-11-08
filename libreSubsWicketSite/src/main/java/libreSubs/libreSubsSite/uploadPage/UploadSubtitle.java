package libreSubs.libreSubsSite.uploadPage;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class UploadSubtitle extends WebPage {
	public UploadSubtitle() {
		add(new Label("message", "Fazer upload de legenda"));

		// Add simple upload form, which is hooked up to its feedback panel by
		// virtue of that panel being nested in the form.
		final FileUploadForm simpleUploadForm = new FileUploadForm(
				"simpleUpload");
		// Create feedback panels
		final FeedbackPanel uploadFeedback = new FeedbackPanel("uploadFeedback");

        // Add uploadFeedback to the page itself
		add(uploadFeedback);
		add(simpleUploadForm);
	}
}