package org.libreSubsApplet;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JTextArea;

import org.libreSubsApplet.dropFile.DropFilesTarget;
import org.libreSubsCommons.SubtitleResourceResolver;

@SuppressWarnings("serial")
public class MainApplet extends JApplet implements OutputListener{

	private JTextArea stringBucketLabel;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		
		stringBucketLabel = new JTextArea();
		stringBucketLabel.setEditable(false);
        
		final DropFilesTarget dropFilesTarget = new DropFilesTarget();
		
		final String srtProviderURL = getSrtProviderUrl();
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(srtProviderURL);
		
		dropFilesTarget.addDropFileListener(new DownloadSubtitles(srtSource, this));
		
		stringBucketLabel.setDropTarget(dropFilesTarget);
		add(stringBucketLabel);
	}

    public String getSrtProviderUrl() {
        final String srtProviderURL = getParameter("srtProviderURL");
		
		
		if(srtProviderURL==null){
			return "http://127.0.0.1:8081/sub?id=%id&lang=%lang";
		}
        return srtProviderURL;
    }

	@Override
	public void info(final String info) {
		stringBucketLabel.setText(info);
	}
}
