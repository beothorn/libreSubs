package libreSubs.libreSubsSite.recentChanges;

import libreSubs.libreSubsSite.BasePage;
import libreSubs.libreSubsSite.WicketApplication;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "recentChanges")
public class RecentChangesPage extends BasePage {

	public RecentChangesPage() {
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		add(new MultiLineLabel("commitLog",
				subtitlesRepositoryHandler.getLastNCommits(10)));
	}

}
