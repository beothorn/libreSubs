package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.libreSubsEngine.testUtils.TempRepositoryRepo;

public class AddSubFromFileTest {
	
	
	@Test
	public void testAddSubFromFileAndDelete() throws IOException{
		final File srtFile = File.createTempFile("libreSubsTest", ".srt");		
		final String expectedContent = "fooáéí";
		FileUtils.writeStringToFile(srtFile, expectedContent,"cp1252");
		final TempRepositoryRepo tempRepo = new TempRepositoryRepo();
		tempRepo.loadSubtitleBase();
		final SubtitlesRepository subRepo = tempRepo.getSubRepo();
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = new SubtitlesRepositoryHandler(subRepo);
		final String id = "42";
		final String lang = "pt_BR";
		subtitlesRepositoryHandler.addSubtitleFromFileAndDeleteIt(id,lang,srtFile);
		
		final String subtitleOrNull = subtitlesRepositoryHandler.getSubtitleOrNull(id, lang);
		
		Assert.assertEquals(expectedContent, subtitleOrNull);
		Assert.assertFalse(srtFile.exists());
	}

}
