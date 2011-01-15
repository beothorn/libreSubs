package libreSubs.libreSubsSite;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.basic.Label;


public class TextPage extends BasePage {
	
	public static void redirectToPageWithText(final String text) {
		throw new RestartResponseException(new TextPage(text));
	}

	public TextPage(final String text) {
		add(new Label("text", text));
	}
	

}
