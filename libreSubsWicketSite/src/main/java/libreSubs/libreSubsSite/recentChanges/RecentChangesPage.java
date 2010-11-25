package libreSubs.libreSubsSite.recentChanges;

import libreSubs.libreSubsSite.WicketApplication;
import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "recentChanges")
public class RecentChangesPage extends WebPage {

	public RecentChangesPage() {
		add(new MenuPanel("menu"));
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		add(new MultiLineLabel("commitLog",
				subtitlesRepositoryHandler.getLastNCommits(10)));
	}

}
