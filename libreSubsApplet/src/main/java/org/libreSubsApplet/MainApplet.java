package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import org.libreSubsApplet.dropFile.DropFilesTarget;
import org.libreSubsApplet.utils.LocaleUtil;
import org.libreSubsApplet.utils.SubtitleResourceResolver;

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
		info("Se você arrastar um diretório ele será escaneado, fazendo upload das legendas que tiverem um arquivo");
		info("com o mesmo nome e download para os arquivos que não tivrem legenda.");
	}

	private JComboBox createLanguageChooser(
			final DroppedFilesProcessor dropFileListener) {
		
		final JComboBox languageChooser = new JComboBox(LocaleUtil.getSortedAvailableLocales());
		languageChooser.setRenderer(new DefaultListCellRenderer(){
			@Override
			public Component getListCellRendererComponent(final JList list,
					final Object value, final int index, final boolean isSelected,
					final boolean cellHasFocus) {
				final Component listCellRendererComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				setText(((Locale)value).getDisplayName());
				return listCellRendererComponent;
			}
		});
		languageChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final Locale language = (Locale)languageChooser.getSelectedItem();
				dropFileListener.setLanguage(language.toString());
			}
		});
		
		languageChooser.setSelectedItem(Locale.getDefault());
		
		return languageChooser;
	}

	private DroppedFilesProcessor createDroppedFileProcessor() {
		final String srtProviderURL = getDownloadUrl();
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(srtProviderURL);
		final DroppedFilesProcessor dropFileListener = new DroppedFilesProcessor(srtSource, this, Locale.getDefault().toString());
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

    public String getDownloadUrl() {
        final String srtProviderURL = getParameter("download");
		
		if(srtProviderURL==null){
			return "http://127.0.0.1:8081/download?id=%id&lang=%lang";
		}
        return srtProviderURL;
    }
    
    public String getUploadUrl() {
        final String srtProviderURL = getParameter("upload");
		
		if(srtProviderURL==null){
			return "http://127.0.0.1:8081/upload?id=%id&lang=%lang";
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
