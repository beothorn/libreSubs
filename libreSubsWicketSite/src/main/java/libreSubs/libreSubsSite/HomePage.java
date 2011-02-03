package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.wicketComponents.DeployJava;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;

public class HomePage extends BasePage {

	public HomePage() {
		setStatelessHint(true);
		addSubtitleFinderApplet();
		add(new Image("appletReflex", new ResourceReference(BasePage.class, "appletReflex.png")));
	}

	private void addSubtitleFinderApplet() {
		if (WicketApplication.get().getConfigurationType().equals(WicketApplication.DEVELOPMENT)) {
			add(new Label("appletDiv", "APPLET GOES HERE"));
			return;
		}

		final DeployJava div = new DeployJava("appletDiv");
		div.setWidth(802);
		div.setHeight(342);
		div.setCode("org.libreSubsApplet.MainApplet.class");
		div.setCodebase(WicketApplication.getBasePath() + "applets");
		div.setArchive("subFinder.jar");
		div.setMinimalVersion("1.6");
		add(div);
	}
}
