package libreSubs.libreSubsSite;

import java.io.IOException;

import libreSubs.libreSubsSite.subRequest.SubRequest;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.libreSubsEngine.SubtitlesRepositoryHandler;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see libreSubs.libreSubsSite.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    

	private static SubtitlesRepositoryHandler subtitles;

    /**
     * Constructor
     */
	public WicketApplication()
	{
	}

	private void mountSubRequestResource() {
		getSharedResources().add("subRequest", new SubRequest());
		mountSharedResource("/sub", new ResourceReference("subRequest")
				.getSharedResourceKey());
	}
	
	public static String getBasePath() {
		final Request request = RequestCycle.get().getRequest();
		final String relativePathPrefix = request
				.getRelativePathPrefixToWicketHandler();
		return RequestUtils.toAbsolutePath(relativePathPrefix);
	}

	public static String getSubtitleOrNull(final String id, final String lang) {
		return subtitles.getSubtitleOrNull(id, lang);
	}

	@Override
	protected void init() {
		setupSubtitleRepository();
		scanForWicketAnnotations();
		addAppletsFolderToPublicResources();
		mountSubRequestResource();
	}

	private void scanForWicketAnnotations() {
		String wicketPagesPackage = HomePage.class.getPackage().getName();
		new AnnotatedMountScanner().scanPackage(
				wicketPagesPackage)
				.mount(this);
	}

	private void setupSubtitleRepository() {
		final SubtitleRepositoryLocation subtitleDefaultRepository = new SubtitleDefaultRepository();
		final SubtitlesRepository subtitlesRepository = new SubtitlesRepository(
				subtitleDefaultRepository);
		try {
			new SubtitleRepositoryLoader(subtitlesRepository);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		WicketApplication.subtitles = new SubtitlesRepositoryHandler(
				subtitlesRepository);
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

	public static String listSubtitles() {
		return subtitles.listSubtitles();
	}

	public static boolean subtitleExists(final String id, final String language) {
		return subtitles.subtitleExists(id, language);
	}

}
