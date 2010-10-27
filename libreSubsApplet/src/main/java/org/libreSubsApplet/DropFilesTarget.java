package org.libreSubsApplet;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void drop(final DropTargetDropEvent dtde) {
		try {
			dtde.acceptDrop(DnDConstants.ACTION_MOVE);
			final Object transferData = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
			final List<File> filesDroped = (List<File>) transferData;
			for (final DropFileListener listener : dropFileListeners) {
				listener.dropedFiles(filesDroped);
			}
		} catch (final UnsupportedFlavorException e) {
			System.out.println("You dragged something here that wasn't a file or a group of files.");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
