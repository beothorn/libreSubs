package org.libreSubsEngine;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.libreSubsEngine.subtitleRepository.repository.PartialSHA1;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.testUtils.PioneerFileInfo;
import org.libreSubsEngine.testUtils.TempRepositoryRepo;

public class SubtitlesRepositoryTest {
	
	private TempRepositoryRepo tempRepo;
	private SubtitlesRepository repo;

	@Before
	public void setup() throws IOException{
		tempRepo = new TempRepositoryRepo();
		tempRepo.loadSubtitleBase();
		repo = tempRepo.getSubRepo();
	}
	
	@Test
	public void loadRepositoryTest() throws IOException{
		final PartialSHA1 videoid = PioneerFileInfo.getVideoid();
		final String subtitleFromVideoIDOrNull = repo.getSubtitleContentsFromVideoIDAndLanguageOrNull("pt_BR",videoid);
		Assert.assertEquals(PioneerFileInfo.getContent(), subtitleFromVideoIDOrNull);
	}
	
	@Test
	public void loadRepositoryMultipleTimesBugTest() throws IOException{
		final String path = PioneerFileInfo.getPath();
		final File fileOnTemp = tempRepo.getFileOnTemp(path);
		final long length = fileOnTemp.length();
		
		for(int i=0; i<5;i++){			
			tempRepo.loadSubtitleBase();
		}
		final long lengthSecondMeasurement = fileOnTemp.length();
		Assert.assertEquals(length, lengthSecondMeasurement);
	}
	
	@Test
	public void changeSubtitleContent() throws IOException{
		final String newContent = "New Content";
		repo.changeContentsForSubtitle(newContent,PioneerFileInfo.getLanguage(),PioneerFileInfo.getVideoid());
		final File fileOnTemp = tempRepo.getFileOnTemp(PioneerFileInfo.getPath());
		final String pioneerFileContents = FileUtils.readFileToString(fileOnTemp);
		Assert.assertEquals(newContent, pioneerFileContents);
		final String subtitleFromVideoIDOrNull = repo.getSubtitleContentsFromVideoIDAndLanguageOrNull("pt_BR",
				PioneerFileInfo.getVideoid());
		Assert.assertEquals(newContent, subtitleFromVideoIDOrNull);
	}
}
