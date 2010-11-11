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
		
		dropFilesTarget.addDropFileListener(new DroppedFilesProcessor(srtSource, this));
		
		stringBucketLabel.setDropTarget(dropFilesTarget);
		add(stringBucketLabel);
		
		info("Para pegar a legenda arraste o arquivo de vídeo aqui.");
		info("A legenda vai ser salva junto do arquivo de vídeo.");
		info("Para fazer upload de uma legenda que ainda não existe,");
		info("arraste o arquivo de vídeo e o arquivo srt aqui.");
		info("O arquivo .srt deve ter o mesmo nome do arquivo de vídeo.");
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
		stringBucketLabel.append(info+"\n");
	}

	@Override
	public void error(final String error) {
		stringBucketLabel.append(error+"\n");
	}
}
