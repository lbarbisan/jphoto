/*
 * Created on 12 déc. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.umlv.jphoto.cela.ui.transfert;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;

import fr.umlv.jphoto.cela.data.Picture;
import fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel;

/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListPictureTransferHandler extends PictureTransferHandler {
	
	private int[] indices = null;
	private int addIndex = -1; //Location where items were added
	private int addCount = 0;  //Number of items added.
	
	/**
	 * 
	 */
	public ListPictureTransferHandler() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.ui.transfert.PictureTransferHandler#exportPicture(javax.swing.JComponent)
	 */
	protected Picture exportPicture(JComponent c) {
		JList list = (JList)c;
		indices = list.getSelectedIndices();
		Object[] values = list.getSelectedValues();
		
		Picture picture = (Picture)values[0];
		System.out.println("on exporte " + picture.getPicturePath());
		return picture;
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.ui.transfert.PictureTransferHandler#importPicture(javax.swing.JComponent, fr.umlv.jphoto.cela.data.Picture)
	 */
	//TODO INSUPPORTED NORMALLY
	protected void importPicture(JComponent c, Picture picture) {
		 JList target = (JList)c;
		 JphotoAdaptaterListModel listModel = (JphotoAdaptaterListModel)target.getModel();
		 int index = target.getSelectedIndex();
		 System.out.println("lindice" + index);
	        //Prevent the user from dropping data back on itself.
	        //For example, if the user is moving items #4,#5,#6 and #7 and
	        //attempts to insert the items after item #5, this would
	        //be problematic when removing the original items.
	        //So this is not allowed.
	        if (indices != null && index >= indices[0] - 1 &&
	              index <= indices[indices.length - 1]) {
	            indices = null;
	            return;
	        }

	        int max = listModel.getSize();
	        if (index < 0) {
	            index = max;
	        } else {
	            index++;
	            if (index > max) {
	                index = max;
	            }
	        }
	        addIndex = index;
	        listModel.addNewIntervalAt(index, picture);
	        System.out.println("on importe" + picture.getPicturePath());
		
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.ui.transfert.PictureTransferHandler#cleanup(javax.swing.JComponent, boolean)
	 */
	protected void cleanup(JComponent c, boolean remove) {
		if (remove && indices != null) {
			JList source = (JList)c;
			DefaultListModel model  = (DefaultListModel)source.getModel();
			//If we are moving items around in the same list, we
            //need to adjust the indices accordingly, since those
            //after the insertion point have moved.
			if (addCount > 0) {
                for (int i = 0; i < indices.length; i++) {
                    if (indices[i] > addIndex) {
                        indices[i] += addCount;
                    }
                }
            }
            for (int i = indices.length - 1; i >= 0; i--) {
                model.remove(indices[i]);
            }
        }
		indices = null;
		addCount = 0;
        addIndex = -1;
    }
	
}
