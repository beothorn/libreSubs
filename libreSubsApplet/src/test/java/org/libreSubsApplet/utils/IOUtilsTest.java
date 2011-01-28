package org.libreSubsApplet.utils;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;
import org.subtitleDownloadLogic.utils.IOUtils;
import org.subtitleDownloadLogic.utils.SHA1CalculationException;

public class IOUtilsTest {
	
	@Test
	public void testSha1() throws SHA1CalculationException{
		final String expected =  "a40422b6ccec60f73679246ade9425268d81ff7a";
		final URL resource = IOUtilsTest.class.getResource("/libreSubs.png");
		final File fileToTestSha1 = new File(resource.getFile());
		final String actual = IOUtils.getPartialSHA1ForFile(fileToTestSha1);
		Assert.assertEquals(expected, actual);
	}

}
