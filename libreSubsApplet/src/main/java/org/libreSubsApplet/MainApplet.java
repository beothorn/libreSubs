package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import org.libreSubsApplet.dropFile.DropFilesTarget;
import org.libreSubsCommons.Language;
import org.libreSubsCommons.SubtitleResourceResolver;

@SuppressWarnings("serial")
public class MainApplet extends JApplet implements OutputListener{

	private JTextArea stringBucketLabel;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		
		final DroppedFilesProcessor dropFileListener = createDroppedFileProcessor();
		
		final JComboBox languageChooser = createLanguageChooser(dropFileListener);
		add(languageChooser,BorderLayout.PAGE_START);
		
		final JScrollPane scrollPane = createDropFileTextArea(dropFileListener);
		add(scrollPane,BorderLayout.CENTER);
		
		printIntroductionText();
	}

	private void printIntroductionText() {
		info("Para pegar a legenda arraste o arquivo de vídeo aqui.");
		info("A legenda vai ser salva junto do arquivo de vídeo.");
		info("Para fazer upload de uma legenda que ainda não existe,");
		info("arraste o arquivo de vídeo e o arquivo srt aqui.");
		info("O arquivo .srt deve ter o mesmo nome do arquivo de vídeo.");
	}

	private JComboBox createLanguageChooser(
			final DroppedFilesProcessor dropFileListener) {
		final JComboBox languageChooser = new JComboBox(Language.values());
		
		languageChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final Language language = (Language)languageChooser.getSelectedItem();
				dropFileListener.setLanguage(language);
			}
		});
		
		final String userLanguage = Locale.getDefault().toString();
		if(Language.isValidLanguage(userLanguage)){
			languageChooser.setSelectedItem(Language.valueOf(userLanguage));
		}
		
		return languageChooser;
	}

	private DroppedFilesProcessor createDroppedFileProcessor() {
		final String srtProviderURL = getSrtProviderUrl();
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(srtProviderURL);
		final DroppedFilesProcessor dropFileListener = new DroppedFilesProcessor(srtSource, this, Language.pt_BR);
		return dropFileListener;
	}

	private JScrollPane createDropFileTextArea(final DroppedFilesProcessor dropFileListener) {
		stringBucketLabel = new JTextArea();
		stringBucketLabel.setEditable(false);
		stringBucketLabel.setBorder(new LineBorder(Color.BLACK));
		final JScrollPane scrollPane = new JScrollPane(stringBucketLabel);
		final DropFilesTarget dropFilesTarget = new DropFilesTarget();
		dropFilesTarget.addDropFileListener(dropFileListener);
		stringBucketLabel.setDropTarget(dropFilesTarget);
		return scrollPane;
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
