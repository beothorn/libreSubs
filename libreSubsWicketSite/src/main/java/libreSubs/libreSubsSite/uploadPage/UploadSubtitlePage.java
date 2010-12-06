package libreSubs.libreSubsSite.uploadPage;

import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "uploadSrtPage")
public class UploadSubtitlePage extends WebPage {
	
	public UploadSubtitlePage() {
		setStatelessHint(true);
		add(new MenuPanel("menu"));
		add(new LanguageChooserDropDown("lang"));
	}
}