package org;

import javax.swing.JFrame;

public class CommandLineOutput implements OutputListener {

	public CommandLineOutput() {
		final JFrame commandOutputScreen = new JFrame();
		commandOutputScreen.setVisible(true);
	}
	
	@Override
	public void info(final String info) {
		System.out.println(info);
	}

	@Override
	public void error(final String error) {
		System.err.println(error);
	}

}
