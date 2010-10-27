package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainApplet extends JApplet {

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

		final JLabel stringBucketLabel = new JLabel(icon);
		final DropFilesTarget dropFilesTarget = new DropFilesTarget();
		dropFilesTarget.addDropFileListener(new DownloadSubtitles());
		stringBucketLabel.setDropTarget(dropFilesTarget);
		add(stringBucketLabel);
	}
}
