package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;

import org.libreSubsApplet.dropFile.DropFilesTarget;
import org.libreSubsCommons.SubtitleResourceResolver;

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
			add(new JLabel(e1.getMessage()));
			return;
		}

		final ImageIcon icon = new ImageIcon(image);

		stringBucketLabel = new JLabel(icon);
		final DropFilesTarget dropFilesTarget = new DropFilesTarget();
		
		final String srtProviderURL = getParameter("srtProviderURL");
		if(srtProviderURL==null){
			add(new JLabel("You must add a parameter srtProviderURL. Example\n" +
					"<param name=srtProviderURL value=\"http://www.yoursite.com/sub?id=%id&lang=%lang\"> "));
			return;
		}
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(srtProviderURL);
		
		dropFilesTarget.addDropFileListener(new DownloadSubtitles(srtSource, this));
		
		stringBucketLabel.setDropTarget(dropFilesTarget);
		add(stringBucketLabel);
	}

	@Override
	public void info(final String info) {
		stringBucketLabel.setText(info);
	}
}
