package libreSubs.libreSubsSite;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "error")
public class ErrorPage extends WebPage {

	public ErrorPage(final String errorDescription) {
		add(new Label("message", errorDescription));
	}

}
