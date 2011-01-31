package org;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.libreSubsApplet.SubtitleDropComponent;

public class WindowOutputListener implements OutputListener {

	private JFrame frame;
	private SubtitleDropComponent output;

	/**
	 * Create the application.
	 */
	public WindowOutputListener() {
		initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 807, 346);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		output = new SubtitleDropComponent();
		output.addComponents(frame);
	}

	@Override
	public void info(final String info) {
		output.info(info);
	}

	@Override
	public void error(final String error) {
		output.error(error);
	}

}
