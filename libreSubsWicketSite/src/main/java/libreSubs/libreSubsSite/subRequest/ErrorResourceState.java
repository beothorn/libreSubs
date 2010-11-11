package libreSubs.libreSubsSite.subRequest;

import org.apache.wicket.markup.html.DynamicWebResource.ResourceState;

public class ErrorResourceState extends ResourceState {

	private final String errorMessage;

	public ErrorResourceState(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getContentType() {
		return "text/plain";
	}

	@Override
	public byte[] getData() {
		return errorMessage.getBytes();
	}

}
