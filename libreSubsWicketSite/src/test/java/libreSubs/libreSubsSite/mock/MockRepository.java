package libreSubs.libreSubsSite.mock;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.UnhandledException;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;

public class MockRepository implements SubtitleRepositoryLocation {

	private File tmpDir;

	public MockRepository() {
		try {
			tmpDir = File.createTempFile("LIBRESUBS", "TMPREPO");
		} catch (final IOException e) {
			throw new UnhandledException(e);
		}
		tmpDir.delete();
		tmpDir.mkdir();
		tmpDir.deleteOnExit();
	}
	
	@Override
	public File getBaseDir() {
		return tmpDir;
	}

	public void deleteRepo() {
			try {
				FileUtils.deleteDirectory(tmpDir);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
	}

}
