package libreSubs.libreSubsSite;

import java.io.IOException;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see libreSubs.libreSubsSite.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    

	public static SubtitlesRepository subtitles;

    /**
     * Constructor
     */
	public WicketApplication()
	{
	}
	
	public static String getBasePath() {
		final Request request = RequestCycle.get().getRequest();
		final String relativePathPrefix = request
				.getRelativePathPrefixToWicketHandler();
		return RequestUtils.toAbsolutePath(relativePathPrefix);
	}

	@Override
	protected void init() {

		final SubtitleRepositoryLocation subtitleDefaultRepository = new SubtitleDefaultRepository();
		final SubtitlesRepository subtitlesRepository = new SubtitlesRepository(
				subtitleDefaultRepository);
		try {
			new SubtitleRepositoryLoader(subtitlesRepository);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		WicketApplication.subtitles = subtitlesRepository;

		addAppletsFolderToPublicResources();
		getSharedResources().add("subRequest", new SubRequest());
		mountSharedResource("/sub", new ResourceReference("subRequest")
				.getSharedResourceKey());
	}

	private void addAppletsFolderToPublicResources() {
		final IResourceSettings resourceSettings = getResourceSettings();
		resourceSettings.addResourceFolder("/applets");
		resourceSettings.setResourceStreamLocator(new ResourceStreamLocator());
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

}
