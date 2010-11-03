package org.libreSubsApplet;

import junit.framework.Assert;

import org.junit.Test;
import org.libreSubsCommons.Language;

public class ParameterSubstitutionTest {

	
	@Test
	public void parameterSubstitutionOnUrlTest(){
		final String urlParameter = "http://www.lucass.is-a-geek.com:8080/latestLibresubs/?id=%id&lang=%lang&file=%file";
		final SubtitleResourceResolver subtitleResourceResolver = new SubtitleResourceResolver(urlParameter);
		final String id = "42";
		final Language lang = Language.pt_BR;
		final String file = "foo";
		final String resolved = subtitleResourceResolver.resolve(id, lang, file);
		Assert.assertEquals("http://www.lucass.is-a-geek.com:8080/latestLibresubs/?id=42&lang=pt_BR&file=foo", resolved);
	}
	
}
