package libreSubs.libreSubsSite.upload;

import libreSubs.libreSubsSite.BasePage;
import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;

import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "uploadForm")
public class UploadFormPage extends BasePage {
	
	public UploadFormPage() {
		setStatelessHint(true);
		add(new LanguageChooserDropDown("lang"));
	}
}