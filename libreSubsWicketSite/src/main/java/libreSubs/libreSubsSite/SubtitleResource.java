package libreSubs.libreSubsSite;

import java.io.IOException;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.libreSubsEngine.subtitleRepository.repository.SubtitleKey;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

@SuppressWarnings("serial")
public class SubtitleResource extends WebResource {

	private final String subtitle;

	public SubtitleResource(SubtitlesRepository subtitlesRepository,
			final SubtitleKey key, final String fileName) throws IOException {
		subtitle = subtitlesRepository.getSubtitleContentsForKey(key);
		setCacheable(false);
		final String strFileName = fileName + ".srt";
		RequestCycle.get().setRequestTarget(
				new ResourceStreamRequestTarget(getResourceStream(),
						strFileName));
	}

	@Override
	public IResourceStream getResourceStream() {
		return new StringResourceStream(subtitle, "text/srt");
	}

}
