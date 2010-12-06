package libreSubs.libreSubsSite.uploadPage;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.upload.FileItem;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path = "upload")
public class UploadServlet extends WebPage {

	// private static final byte MAX_SUB_SIZE = 200;

	public UploadServlet(PageParameters pageParameters) {
		if (pageParameters.size() <= 0) {
			final Bytes defaultMaximumUploadSize = getApplication()
					.getApplicationSettings().getDefaultMaximumUploadSize();
			final MultipartServletWebRequest multipartWebRequest = (MultipartServletWebRequest) ((WebRequest) getRequest())
					.newMultipartWebRequest(defaultMaximumUploadSize);
			getRequestCycle().setRequest(multipartWebRequest);
			pageParameters = new PageParameters(
					multipartWebRequest.getParameterMap());

			final Map<String, FileItem> files = multipartWebRequest.getFiles();
			if (files.size() > 1) {

			}
		}
	}

}
