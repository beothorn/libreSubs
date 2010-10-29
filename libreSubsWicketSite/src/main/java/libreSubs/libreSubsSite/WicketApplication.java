package libreSubs.libreSubsSite;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see libreSubs.libreSubsSite.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    
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
		addAppletsFolderToPublicResources();
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
