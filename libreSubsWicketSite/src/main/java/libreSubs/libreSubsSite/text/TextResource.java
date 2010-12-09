package libreSubs.libreSubsSite.text;

import org.apache.wicket.markup.html.DynamicWebResource.ResourceState;

public class TextResource extends ResourceState {

	private final String content;

	public TextResource(final String content) {
		this.content = content;
	}

	@Override
	public String getContentType() {
		return "text/plain";
	}

	@Override
	public byte[] getData() {
		return content.getBytes();
	}

}
