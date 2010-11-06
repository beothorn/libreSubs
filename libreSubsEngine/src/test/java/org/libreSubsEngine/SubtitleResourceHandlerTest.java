package org.libreSubsEngine;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLoader;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepository;
import org.libreSubsEngine.testUtils.TempRepositoryCreator;

public class SubtitleResourceHandlerTest {
	
	private TempRepositoryCreator tempRepositoryCreator;
	private SubtitlesRepository subtitlesBase;
	private SubtitlesRepositoryHandler subtitleRepositoryHandler;
	
	
	private SubtitlesRepository loadSubtitleBase() throws IOException {
		final SubtitlesRepository subtitlesBase = new SubtitlesRepository(tempRepositoryCreator);
		new SubtitleRepositoryLoader(subtitlesBase);
		return subtitlesBase;
	}
	
	
	@Before
	public void setup() throws IOException{
		tempRepositoryCreator = new TempRepositoryCreator();
		subtitlesBase = loadSubtitleBase();
		subtitleRepositoryHandler = new SubtitlesRepositoryHandler(subtitlesBase);
	}
	
	@Test
	public void testSubtitleResourceHandlerTests(){
		Assert.assertTrue(subtitleRepositoryHandler.isValidLanguage("pt_BR"));
		Assert.assertFalse(subtitleRepositoryHandler.isValidLanguage("foo"));
	}
	
	@Test
	public void testSubtitleResourceHandler(){
		final String lang = "pt_BR";
		final String id = "12345";
		final String subtitle = subtitleRepositoryHandler.getSubtitleOrNull(id,lang);
		Assert.assertEquals("pioneer str content", subtitle);
	}
	
	@Test
	public void testListSubtitles(){
		Assert.assertEquals("videoID: 12345 language: pt_BR".trim(), subtitleRepositoryHandler.listSubtitles().trim());
	}
	
	@Test
	public void testSubtitlesExistx(){
		final String lang = "pt_BR";
		final String id = "12345";
		Assert.assertTrue(subtitleRepositoryHandler.subtitleExists(id,lang));
		
		Assert.assertFalse(subtitleRepositoryHandler.subtitleExists("42","pt_BR"));
	}

}
