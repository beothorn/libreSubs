package org.installer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.SiteAdresses;
import org.subtitleDownloadLogic.utils.IOUtils;

public class WindowsInstaller implements Installer{

	private static final String PROGRAM_FILES_DIR = System.getenv("programfiles");//System.getenv("appdata");
	private static final  File libresubsDir = new File(PROGRAM_FILES_DIR+"\\Libresubs");
	private static final  File libresubsfile = new File(libresubsDir,"subFinder.jar");
	private static final  File uninstallfile = new File(libresubsDir,"uninstall.bat");
	
	@Override
	public void install() throws IOException {
		final URL url = new URL(SiteAdresses.getAppleturl());
		final URLConnection conn = url.openConnection();
		final InputStream libreSubsInputStream = conn.getInputStream();
		libresubsDir.mkdir();
		final Class<WindowsInstaller> thisClass = WindowsInstaller.class;
		final InputStream uninstallerResourceStream = thisClass.getResourceAsStream("/installer/uninstall.bat");
		IOUtils.inputStreamToFile(uninstallerResourceStream,uninstallfile);
		IOUtils.inputStreamToFile(libreSubsInputStream,libresubsfile);
		Runtime.getRuntime().exec("REG ADD \"HKEY_CLASSES_ROOT\\*\\shell\\Sincronizar legenda\\Command\" /ve /d \"\\\"C:\\WINDOWS\\system32\\javaw.exe\\\" -jar \\\""+libresubsfile.getAbsolutePath()+"\\\" \\\"%1\\\"\"");
		Runtime.getRuntime().exec("REG ADD \"HKEY_CLASSES_ROOT\\Folder\\shell\\Sincronizar legenda\\Command\" /ve /d \"\\\"C:\\WINDOWS\\system32\\javaw.exe\\\" -jar \\\""+libresubsfile.getAbsolutePath()+"\\\" \\\"%1\\\"\"");
	}

	public static boolean isUsingWindows() {
		final String os = System.getProperty("os.name").toLowerCase();
		return os.contains("win") && os.contains("xp");
	}
	
	public static boolean isInstalledOnWindows() {
		return libresubsDir.exists();
	}

}
