/*
 * Created on 12 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.ui.transfert;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import fr.umlv.jphoto.cela.data.Picture;
import fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel;
import fr.umlv.jphoto.cela.model.JphotoTableModel;

/**
 * @author root
 *
 */
public class TablePictureTransferHandler extends PictureTransferHandler {

	private static final long serialVersionUID = 1L;
	private int[] rows = null;
	private int addIndex = -1; //Location where items were added
	private int addCount = 0;  //Number of items added.

	/**
	 * 
	 */
	public TablePictureTransferHandler() {
		super();
	}

	/**
	 * @see fr.umlv.jphoto.cela.ui.transfert.PictureTransferHandler#exportPicture(javax.swing.JComponent)
	 */
	protected Picture exportPicture(JComponent c) {
		 JTable table = (JTable)c;
		 JphotoTableModel model = (JphotoTableModel) table.getModel();
		 
		 rows = table.getSelectedRows();
		 int colCount = table.getColumnCount();
		 
		 Picture picture = (Picture) model.getModel().getElementAt(rows[0]); //FIXME pour l'instant
	        
		 return picture;
	}

	/**
	 * @see fr.umlv.jphoto.cela.ui.transfert.PictureTransferHandler#importPicture(javax.swing.JComponent, fr.umlv.jphoto.cela.data.Picture)
	 */
	protected void importPicture(JComponent c, Picture picture) {
		System.out.println("component " + c);
		JTable target = (JTable) c.getComponent(0);
		System.out.println("component " + target);
		JphotoTableModel model = (JphotoTableModel)target.getModel();
		JphotoAdaptaterListModel adapter = model.getModel();
		System.out.println("avant");
		int index = target.getSelectedRow();
		if(index < 0)
			index = 0;
		System.out.println("apres " + index);
		//Prevent the user from dropping data back on itself.
		//For example, if the user is moving rows #4,#5,#6 and #7 and
		//attempts to insert the rows after row #5, this would
		//be problematic when removing the original rows.
		//So this is not allowed.
		/*if (rows != null && index >= rows[0] - 1 &&
				index <= rows[rows.length - 1]) {
			rows = null;
			return;
		}
		*/
		int max = model.getRowCount();
		if (index < 0) {
			index = max;
		} else {
			index++;
			if (index > max) {
				index = max;
			}
		}
		addIndex = index;
		//String[] values = str.split("\n");
		//addCount = values.length;
		int colCount = target.getColumnCount();
		adapter.addNewInterval(picture);
		//for (int i = 0; i < values.length && i < colCount; i++) {
			//model.insertRow(index++, values[i].split(",")); //FIXME A VOIR
		//}
		System.out.println("on importe" + picture.getPicturePath());
	}

	/**
	 * @see fr.umlv.jphoto.cela.ui.transfert.PictureTransferHandler#cleanup(javax.swing.JComponent, boolean)
	 */
	protected void cleanup(JComponent c, boolean remove) {
        JTable source = (JTable)c;
        if (remove && rows != null) {
            DefaultTableModel model =
                 (DefaultTableModel)source.getModel();

            if (addCount > 0) {
                for (int i = 0; i < rows.length; i++) {
                    if (rows[i] > addIndex) {
                        rows[i] += addCount;
                    }
                }
            }
            for (int i = rows.length - 1; i >= 0; i--) {
                model.removeRow(rows[i]);
            }
        }
        rows = null;
        addCount = 0;
        addIndex = -1;
	}

}
