package libreSubs.libreSubsSite;

import java.io.File;
import java.io.IOException;

import org.OutputListener;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.subtitleDownloadLogic.VideoWithSubtitle;
import org.subtitleDownloadLogic.utils.DownloaderImpl;
import org.subtitleDownloadLogic.utils.IOUtils;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;
import org.subtitleDownloadLogic.utils.UploaderImpl;

public class FunctionalTest implements OutputListener {

	private StartUsingJetty startUsingJetty;

	@Before
	public void setup(){
		startUsingJetty = new StartUsingJetty();
		startUsingJetty.start();		
	}
	
	@After
	public void tearDown(){
		startUsingJetty.stop();
		startUsingJetty = null;
	}
	
	@Test
	public void happyDayAppletUploadDownloadTest() throws IOException{
		final String originalSubContent = "foo áéíàçã";
		
		final String uploadUrl = "http://localhost:8081/upload";
		final UploaderImpl uploaderImpl = new UploaderImpl(uploadUrl, "pt_BR");
		final File subtitle = createSubtitleFileOnTmpToUpload(originalSubContent);
		final File video = createFakeVideoOnTmp();
		final VideoWithSubtitle videoWithSubtitle = new VideoWithSubtitle(video, subtitle);
		uploaderImpl.upload(this, videoWithSubtitle);
		final String downloadUrl = "http://localhost:8081/download?id=%id&lang=%lang";
		final SubtitleResourceResolver subtitleSource = new SubtitleResourceResolver(downloadUrl, uploadUrl);
		final DownloaderImpl downloaderImpl = new DownloaderImpl(subtitleSource, "pt_BR");
		downloaderImpl.download(this, video);
		final String downloadedSubContents = getSubtitlesThatWereDonloaded(video);
		Assert.assertEquals(originalSubContent, downloadedSubContents);
	}

	private String getSubtitlesThatWereDonloaded(final File video)
			throws IOException {
		final String videoAbsolutePath = video.getAbsolutePath();
		final String videoWithoutExtension = IOUtils.removeExtension(videoAbsolutePath);
		final File subDownloaded = new File(videoWithoutExtension+".srt");
		subDownloaded.deleteOnExit();
		final String downloadedSubContents = FileUtils.readFileToString(subDownloaded,"cp1252");
		return downloadedSubContents;
	}

	private File createFakeVideoOnTmp() throws IOException {
		final File video = File.createTempFile("AAAA", ".avi");
		FileUtils.writeStringToFile(video, "DOES NOT MATTER");
		video.deleteOnExit();
		return video;
	}

	private File createSubtitleFileOnTmpToUpload(final String originalSubContent)
			throws IOException {
		final File subtitle = File.createTempFile("AAAA", ".srt");
		FileUtils.writeStringToFile(subtitle, originalSubContent,"cp1252");
		subtitle.deleteOnExit();
		return subtitle;
	}

	@Override
	public void info(final String info) {
	}

	@Override
	public void error(final String error) {
	}
	
}
