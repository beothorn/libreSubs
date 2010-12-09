package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


public class TextPage extends WebPage {
	
	public static void redirectToPageWithText(final String text) {
		throw new RestartResponseException(new TextPage(text));
	}

	public TextPage(final String text) {
		add(new MenuPanel("menu"));
		add(new Label("text", text));
	}
	

}
