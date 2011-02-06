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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.OutputListener;
import org.SiteAdresses;
import org.installer.InstallerFactory;
import org.libreSubsApplet.dropFile.DropFilesTarget;
import org.subtitleDownloadLogic.DroppedFilesProcessor;
import org.subtitleDownloadLogic.utils.LocaleUtil;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

@SuppressWarnings("serial")
public class MainApplet extends JApplet implements OutputListener{
	
	private OutputListener output;
	
	@Override
	public void init() {
		setLookAndfeel();
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		final DroppedFilesProcessor dropFileListener = createDroppedFileProcessor();
		createDropFileTextArea(dropFileListener);
		final JComboBox languageChooser = createLanguageChooser(dropFileListener);
		add(languageChooser,BorderLayout.PAGE_START);
		if(InstallerFactory.isInstallAvailable()){
			add(new InstallLabel(),BorderLayout.PAGE_END);	
		}
		printIntroductionText();
	}
	
	private void setLookAndfeel() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);
	}

	private void printIntroductionText() {
		output.info("Para pegar a legenda arraste o arquivo de vídeo aqui.");
		output.info("A legenda vai ser salva junto do arquivo de vídeo.");
		output.info("Para fazer upload de uma legenda que ainda não existe,");
		output.info("arraste o arquivo de vídeo que tenha um arquivo srt aqui.");
		output.info("O arquivo .srt deve ter o mesmo nome do arquivo de vídeo.");
		output.info("Se você arrastar um diretório ele será escaneado, fazendo upload das legendas que tiverem um arquivo");
		output.info("com o mesmo nome e download para os arquivos que não tiverem legenda .");
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
		final String downloadurl = SiteAdresses.getDownloadurl();
		final String uploadurl = SiteAdresses.getUploadurl();
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(downloadurl, uploadurl);
		final DroppedFilesProcessor dropFileListener = new DroppedFilesProcessor(srtSource, this, Locale.getDefault().toString());
		return dropFileListener;
	}

	private void createDropFileTextArea(final DroppedFilesProcessor dropFileListener) {
		final SubtitleDropComponent subtitleDropTextArea = new SubtitleDropComponent();
		final DropFilesTarget dropFilesTarget = new DropFilesTarget();
		dropFilesTarget.addDropFileListener(dropFileListener);
		subtitleDropTextArea.setDropTarget(dropFilesTarget);
		subtitleDropTextArea.addComponents(this);
		output = subtitleDropTextArea;
	}

	@Override
	public void info(final String info) {
		output.info(info);
	}

	@Override
	public void error(final String error) {
		output.error(error);
	}

	
}
