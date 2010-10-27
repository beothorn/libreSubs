package org.libreSubsApplet;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DropFilesTarget extends DropTarget{
	
	
	private final List<DropFileListener> dropFileListeners;
	
	public DropFilesTarget() {
		dropFileListeners = new ArrayList<DropFileListener>();
	}
	
	public void addDropFileListener(final DropFileListener dropFileListener){
		dropFileListeners.add(dropFileListener);
	}
	
	@Override
	public synchronized void drop(final DropTargetDropEvent dtde) {
		try {
			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			dtde.getCurrentDataFlavors();
			final Transferable transferable = dtde.getTransferable();
			final List<File> filesDropped = getTransferableAsFileArrayOrNull(transferable);
			if(filesDropped != null)
				callListeners(filesDropped);
		} catch (final UnsupportedFlavorException e) {
			System.out.println("You dragged something here that wasn't a file or a group of files.\n" +
					"Or maybe you're using an OS I dont know...."+e);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<File> getTransferableAsFileArrayOrNull(
			final Transferable transferable)
			throws UnsupportedFlavorException, IOException,
			MalformedURLException, UnsupportedEncodingException {
		final boolean isListFile = transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
		//windows
		if(isListFile){
			final Object transferData = transferable.getTransferData(DataFlavor.javaFileListFlavor);
			return (List<File>) transferData;				
		}
		
		final boolean isFileText = transferable.isDataFlavorSupported(DataFlavor.stringFlavor);
		//linux
		if(isFileText){
			final Object transferData = transferable.getTransferData(DataFlavor.stringFlavor);
			final String filesDroppedString = (String) transferData;
			final String[] filesURLsArray = filesDroppedString.split("\n");
			return getFileNamesFromStringURLArray(filesURLsArray);
		}
		return null;
	}

	private List<File> getFileNamesFromStringURLArray(
			final String[] filesURLsArray) throws MalformedURLException,
			UnsupportedEncodingException {
		final List<File> filesDropped = new ArrayList<File>();
		for (final String urlString : filesURLsArray) {
			final URL fileURL = new URL(java.net.URLDecoder.decode(urlString,"UTF-8"));
			final File file = new File(fileURL.getFile());
			filesDropped.add(file);
		}
		return filesDropped;
	}

	private void callListeners(final List<File> filesDropped) {
		for (final DropFileListener listener : dropFileListeners) {
			listener.droppedFiles(filesDropped);
		}
	}

}
