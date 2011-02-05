package org.subtitleDownloadLogic.mock;

import org.OutputListener;
import org.subtitleDownloadLogic.VideoWithSubtitle;
import org.subtitleDownloadLogic.utils.Uploader;

public class MockUploader implements Uploader {

	@Override
	public void upload(OutputListener outputListener,
			VideoWithSubtitle videoWithSubtitle) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not impelemented");
	}

}
