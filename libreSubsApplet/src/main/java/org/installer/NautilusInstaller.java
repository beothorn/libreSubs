package org.installer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NautilusInstaller implements Installer {

	public static void main(final String[] args) throws IOException {
		new NautilusInstaller().install();
	}
	
	@Override
	public void install() throws IOException {
		final InputStream resourceAsStream = NautilusInstaller.class.getResourceAsStream("/installer/Sincronizar legenda");
		final File nautilusScript = InstallerFactory.NAUTILUS_SCRIPT;
		nautilusScript.createNewFile();
		nautilusScript.setExecutable(true);
		final OutputStream out = new FileOutputStream(nautilusScript);
		int read=0;
		final byte[] bytes = new byte[1024];
		while((read = resourceAsStream.read(bytes))!= -1){
			out.write(bytes, 0, read);
		}
		resourceAsStream.close();
		out.flush();
		out.close();
	}
}
