package libreSubs.libreSubsSite.upload;

import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "uploadForm")
public class UploadFormPage extends WebPage {
	
	public UploadFormPage() {
		setStatelessHint(true);
		add(new MenuPanel("menu"));
		add(new LanguageChooserDropDown("lang"));
	}
}