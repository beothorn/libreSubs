package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainApplet extends JApplet implements OutputListener{

	private JLabel stringBucketLabel;

	@Override
	public void init() {
		setLayout(new BorderLayout());

		final InputStream resourceAsStream = MainApplet.class.getClassLoader()
				.getResourceAsStream("libreSubs.png");
		Image image;
		try {
			image = ImageIO.read(resourceAsStream);
		} catch (final IOException e1) {
			throw new RuntimeException(e1);
		}

		final ImageIcon icon = new ImageIcon(image);

		stringBucketLabel = new JLabel(icon);
		final DropFilesTarget dropFilesTarget = new DropFilesTarget();
		URL srtSource;
		try {
			srtSource = new URL("http://www.lucass.is-a-geek.com:8080/latestLibresubs/");
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
		
		dropFilesTarget.addDropFileListener(new DownloadSubtitles(srtSource, this));
		stringBucketLabel.setDropTarget(dropFilesTarget);
		add(stringBucketLabel);
	}

	@Override
	public void info(final String info) {
		stringBucketLabel.setText(info);
	}
}
