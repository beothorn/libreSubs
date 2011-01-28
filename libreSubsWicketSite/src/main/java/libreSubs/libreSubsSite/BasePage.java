package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.recentChanges.RecentChangesPage;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.protocol.http.WebApplication;

public abstract class BasePage extends WebPage {
	
	private static final String FAVICON = "favicon.png";

	public static void registerResources(final WebApplication webApplication){
		final ResourceReference faviconReference = new ResourceReference(BasePage.class,FAVICON);
		final String sharedResourceKey = faviconReference.getSharedResourceKey();
		webApplication.mountSharedResource("/favicon.png",sharedResourceKey);
	}
	
	@SuppressWarnings("serial")
	public BasePage() {
		add(CSSPackageResource.getHeaderContribution(BasePage.class,"style.css"));
		add(new HeaderContributor(new IHeaderContributor() {
			@Override
			public void renderHead(final IHeaderResponse response) {
				response.renderString("<link rel=\"shortcut icon\" href=\"./"+FAVICON+"\">");
			}
		}));
		add(new Image("headerIcon", new ResourceReference(BasePage.class, "headerIcon.png")));
		add(new Image("libreSubsLogo", new ResourceReference(BasePage.class, "libreSubsLogo.png")));
		add(new Image("libreSubsTitle", new ResourceReference(BasePage.class, "libreSubsTitle.png")));
		add(new BookmarkablePageLink<String>("home", HomePage.class));
		add(new BookmarkablePageLink<String>("subOperations",SubtitlesOperationsWebInterface.class));
		add(new BookmarkablePageLink<String>("recentChanges",RecentChangesPage.class));
	}

}
