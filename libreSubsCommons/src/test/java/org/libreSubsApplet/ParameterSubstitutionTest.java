package org.libreSubsApplet;

import junit.framework.Assert;

import org.junit.Test;
import org.libreSubsApplet.utils.SubtitleResourceResolver;
import org.libreSubsCommons.Language;

public class ParameterSubstitutionTest {

	@Test
	public void parameterSubstitutionOnUrlTest() {
		final String idParam = SubtitleResourceResolver.idParameter;
		final String langParam = SubtitleResourceResolver.langParameter;
		final String fileParam = SubtitleResourceResolver.fileParameter;
		final String urlParameter = "http://www.lucass.is-a-geek.com:8080/latestLibresubs/?"
				+ idParam + "=%" + idParam
				+ "&"
				+ langParam	+ "=%" + langParam
				+ "&" + fileParam + "=%" + fileParam;
		final SubtitleResourceResolver subtitleResourceResolver = new SubtitleResourceResolver(
				urlParameter);
		final String id = "42";
		final Language lang = Language.pt_BR;
		final String file = "foo";
		final String resolved = subtitleResourceResolver
				.resolve(id, lang.toString(), file);
		Assert
				.assertEquals(
						"http://www.lucass.is-a-geek.com:8080/latestLibresubs/?id=42&lang=pt_BR&file=foo",
						resolved);
	}

}
