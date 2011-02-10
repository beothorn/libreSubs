package libreSubs.libreSubsSite;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "subList")
public class SubListPage extends BasePage {

	public SubListPage() {;
		addSubtitlesListPrintForDebug();
	}

	private void addSubtitlesListPrintForDebug() {
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		final String listSubtitles = subtitlesRepositoryHandler.listSubtitles();
		final int subtitlesQuantity = subtitlesRepositoryHandler.subtitlesQuantity();
		final long subtitlesRepoSize = subtitlesRepositoryHandler.subtitlesRepoSize();
		final long kBytes = subtitlesRepoSize/1024;
		final long mBytes = kBytes/1024;
		add(new MultiLineLabel(
				"message", "Quantidade de legendas: "+subtitlesQuantity+"\n"
				+"Tamanho do repositório: "+subtitlesRepoSize+" bytes ou "+ kBytes+"kbytes ou "+ mBytes+"mb \n"
				+" Legendas: \n"
				+ listSubtitles
		));
	}
}
