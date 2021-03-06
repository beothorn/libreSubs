package org.libreSubsApplet.utils;

import junit.framework.Assert;

import org.junit.Test;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

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
				urlParameter, null);
		final String id = "42";
		final String lang = "pt_BR";
		final String file = "foo";
		final String resolved = subtitleResourceResolver
				.resolve(id, lang.toString(), file);
		Assert
				.assertEquals(
						"http://www.lucass.is-a-geek.com:8080/latestLibresubs/?id=42&lang=pt_BR&file=foo&commandLine=true",
						resolved);
	}
}