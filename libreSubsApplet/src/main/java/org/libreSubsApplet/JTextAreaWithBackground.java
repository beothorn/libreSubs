package org.libreSubsApplet;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JTextAreaWithBackground extends JTextArea {
	

	private final Image image;

	public JTextAreaWithBackground() {
		setOpaque(false);
		setBorder(null);
		final URL resource = JTextAreaWithBackground.class.getResource("/applet.png");
		final ImageIcon background = new ImageIcon(resource);
		image = background.getImage();
	}
	
	@Override
	public void paintComponent (final Graphics g)
    {
        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), this);
        super.paintComponent(g);
    }

}
