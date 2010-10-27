package libreSubs.libreSubsSite;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public class RequestSrt extends WebPage {

	public RequestSrt() {
		// TODO Auto-generated constructor stub
	}

	public RequestSrt(final PageParameters parameters) {
		final Object lang = parameters.get("lang");
		final Object sha1 = parameters.get("sha1");
		final Object srt = parameters.get("srt");
		System.out.println("" + lang + sha1 + srt + "");
	}

}
