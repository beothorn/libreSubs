package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "subList")
public class SubListPage extends WebPage {

	public SubListPage() {
		add(new MenuPanel("menu"));
		addSubtitlesListPrintForDebug();
	}

	private void addSubtitlesListPrintForDebug() {
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		add(new MultiLineLabel("message", "SubList: "
				+ subtitlesRepositoryHandler.listSubtitles()));
	}
}
