package org;

public class CommandLineOutput implements OutputListener {

	@Override
	public void info(final String info) {
		System.out.println(info);
	}

	@Override
	public void error(final String error) {
		System.err.println(error);
	}

}
