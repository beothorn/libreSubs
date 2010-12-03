package libreSubs.libreSubsSite.uploadPage;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.util.upload.FileItem;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "upload")
public class UploadServlet extends WebPage {

	public UploadServlet(PageParameters pageParameters) {
		if (pageParameters.size() <= 0) {
			final MultipartServletWebRequest multipartWebRequest = (MultipartServletWebRequest) ((WebRequest) getRequest())
					.newMultipartWebRequest(getApplication()
							.getApplicationSettings()
							.getDefaultMaximumUploadSize());
			getRequestCycle().setRequest(multipartWebRequest);
			pageParameters = new PageParameters(
					multipartWebRequest.getParameterMap());

			final Map<String, FileItem> files = multipartWebRequest.getFiles();
		}
	}

}
