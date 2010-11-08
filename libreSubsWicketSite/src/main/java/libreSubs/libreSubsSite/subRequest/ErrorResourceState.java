package libreSubs.libreSubsSite.subRequest;

import java.io.UnsupportedEncodingException;

import org.apache.wicket.markup.html.DynamicWebResource.ResourceState;

public class ErrorResourceState extends ResourceState {

	private final String errorMessage;

	public ErrorResourceState(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

	@Override
	public byte[] getData() {

		// TODO: somehow rende upload page

		final String htmlMessage = "<html><head><title>Erro</title></head><body>"
				+ errorMessage + "</body></html>";
		try {
			return htmlMessage.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
