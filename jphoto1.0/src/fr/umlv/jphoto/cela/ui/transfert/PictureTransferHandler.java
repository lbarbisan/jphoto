/*
 * Created on 12 déc. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.umlv.jphoto.cela.ui.transfert;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import fr.umlv.jphoto.cela.data.Picture;

/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class PictureTransferHandler extends TransferHandler {
	
	protected abstract Picture exportPicture(JComponent c);
	protected abstract void importPicture(JComponent c, Picture picture);
	protected abstract void cleanup(JComponent c, boolean remove);
	
	protected Transferable createTransferable(JComponent c) {
		return new PictureTransferable(exportPicture(c));
	}
	
	/**
	 * 
	 */
	public PictureTransferHandler() {
		super();
	}
		
	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent, java.awt.datatransfer.DataFlavor[])
	 */
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		for (int i = 0; i < transferFlavors.length; i++) {
            if (PictureTransferable.getFlavor().equals(transferFlavors[i])) {
                return true;
            }
        }
		System.out.println("can not import");
        return false;
    }

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
	 */
	protected void exportDone(JComponent source, Transferable data, int action) {
		//super.exportDone(source, data, action);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE; //Pour la JLIST ou la JTABLE
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#importData(javax.swing.JComponent, java.awt.datatransfer.Transferable)
	 */
	public boolean importData(JComponent comp, Transferable t) {
		if (canImport(comp, t.getTransferDataFlavors())) {
            try {
                Picture pic = (Picture)t.getTransferData(PictureTransferable.getFlavor());
                importPicture(comp, pic);
                return true;
            } catch (UnsupportedFlavorException ufe) {
            } catch (IOException ioe) {
            }
        }

        return false;
	}
}
