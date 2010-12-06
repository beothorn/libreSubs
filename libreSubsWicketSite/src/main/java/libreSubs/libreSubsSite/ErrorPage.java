package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


public abstract class ErrorPage extends WebPage {
	
	public static void redirectToError(final String errorMessage) {
		throw new RestartResponseException(new ErrorPage() {
			@Override
			protected String errorMessage() {
				return errorMessage;
			}
		});
	}

	private ErrorPage() {
		add(new MenuPanel("menu"));
		add(new Label("error",errorMessage()));
	}
	
	protected abstract String errorMessage(); 

}
