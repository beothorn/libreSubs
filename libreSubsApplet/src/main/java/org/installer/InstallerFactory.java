package org.installer;

import java.io.File;

public class InstallerFactory {

	private final static String USER_HOME = System.getProperty("user.home");
	public final static File NAUTILUS_SCRIPT_DIR = new File(USER_HOME+"/.gnome2/nautilus-scripts");
	public final static File NAUTILUS_SCRIPT = new File(NAUTILUS_SCRIPT_DIR,"Sincronizar legenda");
	
	public static boolean isInstallAvailable(){
		if(NAUTILUS_SCRIPT_DIR.exists() && !NAUTILUS_SCRIPT.exists()){
			return true;
		}
//		final String os = System.getProperty("os.name").toLowerCase();
//	    return (os.indexOf( "win" ) >= 0);
		return false;
	}
	
	public static Installer getInstaller(){
		if(NAUTILUS_SCRIPT_DIR.exists() ){
			return null;
		}
		return null;
	}
	
}
