package libreSubs.libreSubsSite.uploadPage;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.upload.FileUploadException;

public class UploadServlet extends MultipartServletWebRequest {

	public UploadServlet(final HttpServletRequest request, final Bytes maxSize)
			throws FileUploadException {
		super(request, maxSize);
	}

	@Override
	protected void onUploadCompleted() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not impelemented");
	}

}
