package libreSubs.libreSubsSite.menuPanel;

import libreSubs.libreSubsSite.HomePage;
import libreSubs.libreSubsSite.download.DownloadFormPage;
import libreSubs.libreSubsSite.editPage.EditSubtitleFormPage;
import libreSubs.libreSubsSite.recentChanges.RecentChangesPage;
import libreSubs.libreSubsSite.upload.UploadFormPage;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

@SuppressWarnings("serial")
public class MenuPanel extends Panel {

	public MenuPanel(final String id) {
		super(id);
		add(new BookmarkablePageLink<String>("home", HomePage.class));
		add(new BookmarkablePageLink<String>("downloadSub",DownloadFormPage.class));
		add(new BookmarkablePageLink<String>("uploadSub", UploadFormPage.class));
		add(new BookmarkablePageLink<String>("editSub",EditSubtitleFormPage.class));
		add(new BookmarkablePageLink<String>("recentChanges",RecentChangesPage.class));
	}

}
