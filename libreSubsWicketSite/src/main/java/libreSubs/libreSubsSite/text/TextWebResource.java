package libreSubs.libreSubsSite.text;

import org.apache.wicket.markup.html.DynamicWebResource;

@SuppressWarnings("serial")
public class TextWebResource extends DynamicWebResource {

	private final String text;

	public TextWebResource(final String text) {
		this.text = text;
	}
	
	@Override
	protected ResourceState getResourceState() {
		return new TextResource(text);
	}

}
