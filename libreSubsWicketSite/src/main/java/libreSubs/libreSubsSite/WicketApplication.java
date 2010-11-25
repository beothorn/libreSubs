package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.downloadPage.DownloadSubtitle;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

public class WicketApplication extends WebApplication
{    

	private static final Class<HomePage> HOME_PAGE_CLASS = HomePage.class;
	private static SubtitlesRepositoryHandler subtitles;

    /**
     * Constructor
     */
	public WicketApplication()
	{
	}

	public static SubtitlesRepositoryHandler getSubtitlesRepositoryHandler() {
		return subtitles;
	}

	public static String getBasePath() {
		final Request request = RequestCycle.get().getRequest();
		final String relativePathPrefix = request
				.getRelativePathPrefixToWicketHandler();
		return RequestUtils.toAbsolutePath(relativePathPrefix);
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HOME_PAGE_CLASS;
	}

	@Override
	protected void init() {
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		setupSubtitleRepository();
		scanForWicketAnnotations();
		addAppletsFolderToPublicResources();
		mountSubRequestResource();
	}

	private void mountSubRequestResource() {
		getSharedResources().add(DownloadSubtitle.RESOURCE_NAME,
				new DownloadSubtitle());
		mountSharedResource("/" + DownloadSubtitle.RESOURCE_NAME,
				new ResourceReference(DownloadSubtitle.RESOURCE_NAME)
				.getSharedResourceKey());
	}

	private void scanForWicketAnnotations() {
		final String wicketPagesPackage = HOME_PAGE_CLASS.getPackage()
				.getName();
		new AnnotatedMountScanner().scanPackage(
				wicketPagesPackage)
				.mount(this);
	}

	private void setupSubtitleRepository() {
		final SubtitleRepositoryLocation subtitleDefaultRepository = new SubtitleDefaultRepository();
		final SubtitlesRepository subtitlesRepository = new SubtitlesRepository(
				subtitleDefaultRepository);

		WicketApplication.subtitles = new SubtitlesRepositoryHandler(
				subtitlesRepository);
	}

	private void addAppletsFolderToPublicResources() {
		final IResourceSettings resourceSettings = getResourceSettings();
		resourceSettings.addResourceFolder("/applets");
		resourceSettings.setResourceStreamLocator(new ResourceStreamLocator());
	}
}
