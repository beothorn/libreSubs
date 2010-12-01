package org.libreSubsApplet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import org.libreSubsApplet.dropFile.DropFilesTarget;
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
	}

	private JComboBox createLanguageChooser(
			final DroppedFilesProcessor dropFileListener) {
		
		final JComboBox languageChooser = new JComboBox(getSortedAvailableLocales());
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

	private Object[] getSortedAvailableLocales() {
		final Locale[] sortedAvailableLocales = Locale.getAvailableLocales();
		final Comparator<Locale> localeComparator = new Comparator<Locale>() {
		    public int compare( final Locale l1, final Locale l2 ) {  
		        final String l1DisplayName = l1.getDisplayName();
				final String l2DisplayName = l2.getDisplayName();
				return l1DisplayName.compareTo(l2DisplayName); 
		    }
		};
		Arrays.sort(sortedAvailableLocales,localeComparator);
		final ArrayList<Locale> validLocales = new ArrayList<Locale>();
		for (final Locale locale : sortedAvailableLocales) {
			if(!locale.getCountry().isEmpty()){
				validLocales.add(locale);
			}
		}
		return validLocales.toArray();
	}

	private DroppedFilesProcessor createDroppedFileProcessor() {
		final String srtProviderURL = getSrtProviderUrl();
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
