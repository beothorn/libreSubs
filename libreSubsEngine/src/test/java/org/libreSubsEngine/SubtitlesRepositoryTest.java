package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryRepo;

public class SubtitlesRepositoryTest {
	
	private TempRepositoryRepo tempRepo;
	private SubtitlesRepository repo;

	@Before
	public void setup() throws IOException{
		tempRepo = new TempRepositoryRepo();
		repo = tempRepo.getSubRepo();
	}
	
	@Test
	public void loadRepositoryTest() throws IOException{
		final String subtitleFromVideoIDOrNull = repo.getSubtitleContentsFromVideoIDAndLanguageOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(PioneerFileInfo.content, subtitleFromVideoIDOrNull);
	}
	
	@Test
	public void changeSubtitleContentInternally() throws IOException{
		final String newContent = "New Content";
		repo.changeContentsForSubtitle(newContent,PioneerFileInfo.language,PioneerFileInfo.videoID);
		final File fileOnTemp = tempRepo.getFileOnTemp(PioneerFileInfo.path);
		final String pioneerFileContents = FileUtils.readFileToString(fileOnTemp);
		Assert.assertEquals(newContent, pioneerFileContents);
		final String subtitleFromVideoIDOrNull = repo.getSubtitleContentsFromVideoIDAndLanguageOrNull(Language.pt_BR,
				PioneerFileInfo.videoID);
		Assert.assertEquals(newContent, subtitleFromVideoIDOrNull);
	}
}
