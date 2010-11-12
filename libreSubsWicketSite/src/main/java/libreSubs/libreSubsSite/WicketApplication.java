package libreSubs.libreSubsSite;

import java.io.File;
import java.io.IOException;

import libreSubs.libreSubsSite.subRequest.SubRequest;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.libreSubsEngine.subtitleRepository.SubtitleDefaultRepository;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.repository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

public class WicketApplication extends WebApplication
{    

	private static final String DOWNLOAD_SRT_RESOURCE = "downloadSrt";
	private static final Class<HomePage> HOME_PAGE_CLASS = HomePage.class;
	private static SubtitlesRepositoryHandler subtitles;

    /**
     * Constructor
     */
	public WicketApplication()
	{
	}

	public static String listSubtitles() {
		return subtitles.listSubtitles();
	}

	public static boolean subtitleExists(final String id, final String language) {
		return subtitles.subtitleExists(id, language);
	}

	public static void addSubtitleFromFileAndDeleteIt(final String sha1,
			final String localeSelect, final File newFile) {
		try {
			subtitles.addSubtitleFromFileAndDeleteIt(sha1, localeSelect,
					newFile);
		} catch (final IOException e) {
			throw new RuntimeException(
					"Error ocurred when trying to add subtitle to base.", e);
		}
	}

	public static String getSubtitleOrNull(final String id, final String lang) {
		return subtitles.getSubtitleOrNull(id, lang);
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
		setupSubtitleRepository();
		scanForWicketAnnotations();
		addAppletsFolderToPublicResources();
		mountSubRequestResource();
	}

	public static String getDownloadURLPath() {
		return getBasePath() + DOWNLOAD_SRT_RESOURCE;
	}

	private void mountSubRequestResource() {
		getSharedResources().add(DOWNLOAD_SRT_RESOURCE, new SubRequest());
		mountSharedResource("/" + DOWNLOAD_SRT_RESOURCE, new ResourceReference(
				DOWNLOAD_SRT_RESOURCE)
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

}
