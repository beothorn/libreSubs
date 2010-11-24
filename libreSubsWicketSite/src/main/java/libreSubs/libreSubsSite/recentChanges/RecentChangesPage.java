package libreSubs.libreSubsSite.recentChanges;

import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "recentChanges")
public class RecentChangesPage extends WebPage {

	public RecentChangesPage() {
		add(new MenuPanel("menu"));
		add(new MultiLineLabel("commitLog", WicketApplication.getCommitLog()));
	}

}
