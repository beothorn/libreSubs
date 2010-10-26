package org.libreSubsApplet.example;

/*
 * @(#)Test.java	1.0 98/09/21
 *
 * Copyright 1998 by Rockhopper Technologies, Inc.,
 * 75 Trueman Ave., Haddonfield, New Jersey, 08033-2529, U.S.A.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of Rockhopper Technologies, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with RTI.
 */

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
  * File: Test.java<br>
  * Test
  * @author <A HREF="mailto:gene@rockhoppertech.com">Gene De Lisa</A>
  * @version 1.0 Sat Dec 12, 1998
  * @see java.lang.Object
  */

public class Test{

  public Test()  {
    
  }
  
  @SuppressWarnings("serial")
public static void main(final String[] args) {
	  final Image image = Toolkit.getDefaultToolkit().createImage("/libreSubs.png");
	final ImageIcon icon = new ImageIcon(
		  image);

	  final JLabel l = new JLabel("drop here",icon,SwingConstants.CENTER);
	    l.setDropTarget(new DropTarget() {
	    	@Override
			public synchronized void drop(final DropTargetDropEvent dtde) {
	    		try {
	    			dtde.acceptDrop(DnDConstants.ACTION_MOVE);
					System.out.println(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				} catch (final UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    });
	    
	    final JFrame frame = new JFrame();
	    frame.add(l);
	    frame.pack();
	    
	    frame.setVisible(true);
  }
  
}
