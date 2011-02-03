package org.installer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.subtitleDownloadLogic.utils.IOUtils;

public class NautilusInstaller implements Installer {

	private final static String USER_HOME = System.getProperty("user.home");
	public final static File NAUTILUS_SCRIPT_DIR = new File(USER_HOME+"/.gnome2/nautilus-scripts");
	public final static File NAUTILUS_SCRIPT = new File(NAUTILUS_SCRIPT_DIR,"Sincronizar legenda");
	
	public static boolean isInstalledOnNautilus() {
		return NAUTILUS_SCRIPT.exists();
	}

	public static boolean isUsingNautilus() {
		return NAUTILUS_SCRIPT_DIR.exists();
	}
	
	@Override
	public void install() throws IOException {
		final InputStream resourceAsStream = NautilusInstaller.class.getResourceAsStream("/installer/Sincronizar legenda");
		final File nautilusScript = NautilusInstaller.NAUTILUS_SCRIPT;
		IOUtils.inputStreamToFile(resourceAsStream, nautilusScript);
	}

	
}
