package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import org.OutputListener;
import org.libreSubsApplet.dropFile.DropFilesTarget;

public class SubtitleDropComponent implements OutputListener{
	
	private final SubtitleDropTextArea textArea;
	private final JScrollPane scrollPane;

	public SubtitleDropComponent() {
		textArea = new SubtitleDropTextArea();
		scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		final Border border = BorderFactory.createEmptyBorder( 0, 0, 0, 0 );
		scrollPane.setBorder(border);
		scrollPane.setViewportBorder(border);
	}

	@Override
	public void info(final String info) {
		textArea.append(info+"\n");
		scrollDown();
	}

	private void scrollDown() {
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void error(final String error) {
		textArea.append(error+"\n");
		scrollDown();
	}

	public void setDropTarget(final DropFilesTarget dropFilesTarget) {
		textArea.setDropTarget(dropFilesTarget);
	}

	public void addComponents(final Container container) {
		container.add(scrollPane, BorderLayout.CENTER);
	}
	
}
