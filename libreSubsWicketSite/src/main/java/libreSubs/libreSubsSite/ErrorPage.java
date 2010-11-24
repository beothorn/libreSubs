package libreSubs.libreSubsSite;

import libreSubs.libreSubsSite.menuPanel.MenuPanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


public abstract class ErrorPage extends WebPage {
	
	public ErrorPage() {
		setStatelessHint(true);
		add(new MenuPanel("menu"));
		add(new Label("error",errorMessage()));
	}
	
	protected abstract String errorMessage(); 

}
