package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.commons.LanguageChooserDropDown;
import libreSubs.libreSubsSite.download.DownloadSubtitle;
import libreSubs.libreSubsSite.editPage.SubtitleEditorPage;
import libreSubs.libreSubsSite.recentChanges.RecentChangesPage;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.IResourceStream;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

public abstract class BasePage extends WebPage {
	
	private static final String FAVICON = "favicon.png";
	private final SubParameters subParameters;
	
	public static void registerResources(final WebApplication webApplication){
		final ResourceReference faviconReference = new ResourceReference(BasePage.class,FAVICON);
		final String sharedResourceKey = faviconReference.getSharedResourceKey();
		webApplication.mountSharedResource("/favicon.png",sharedResourceKey);
	}
	
	public BasePage() {
		addHeaderElements();
		subParameters = new SubParameters();
		setStatelessHint(true);
		addDownloadElements();
		addUploadElements();
		addEditElements();
	}

	private void addUploadElements() {
		add(new LanguageChooserDropDown("lang"));
	}

	private void addEditElements() {
		final CompoundPropertyModel<String> paramsCompoundPropertyModel = new CompoundPropertyModel<String>(subParameters);
		@SuppressWarnings("serial")
		final StatelessForm<String> form = new StatelessForm<String>(
				"editorCallForm",
				paramsCompoundPropertyModel) {
			@Override
			protected void onSubmit() {
				final String idParam = SubtitleResourceResolver.idParameter;
				final String langParam = SubtitleResourceResolver.langParameter;
				final PageParameters pageParameters = new PageParameters(idParam+"="+subParameters.id+","+langParam+"="+subParameters.lang);
				final SubtitleEditorPage editorPage = new SubtitleEditorPage(pageParameters);
				setResponsePage(editorPage);
			}
		};
		form.add(new TextField<String>("id"));
		form.add(new LanguageChooserDropDown("lang"));
		
		add(form);
	}

	@SuppressWarnings("serial")
	private void addDownloadElements() {
		final CompoundPropertyModel<String> paramsCompoundPropertyModel = new CompoundPropertyModel<String>(subParameters);
		final StatelessForm<String> form = new StatelessForm<String>("inputForm", paramsCompoundPropertyModel) {
			@Override
			protected void onSubmit() {
				final DownloadSubtitle downloadSubtitle = new DownloadSubtitle();
				final IResourceStream resourceStream = downloadSubtitle.getResourceStream();
				final ResourceStreamRequestTarget downloadRequestTarget = new ResourceStreamRequestTarget(resourceStream);
				final RequestCycle requestCycle = getRequestCycle();
				requestCycle.setRequestTarget(downloadRequestTarget);
			}

		};
		add(form);
		form.add(new TextField<String>("id"));
		form.add(new TextField<String>("fileName"));
		form.add(new LanguageChooserDropDown("lang"));
	}
	
	@SuppressWarnings("serial")
	private void addHeaderElements() {
		final HeaderContributor cssHeaderContribution = CSSPackageResource.getHeaderContribution(BasePage.class,"style.css");
		add(cssHeaderContribution);
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
		add(new BookmarkablePageLink<String>("recentChanges",RecentChangesPage.class));
		add(new BookmarkablePageLink<String>("faq",FaqPage.class));
	}

}
