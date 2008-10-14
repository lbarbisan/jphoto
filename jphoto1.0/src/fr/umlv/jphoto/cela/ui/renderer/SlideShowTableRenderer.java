/*
 * Created on 24 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.umlv.jphoto.cela.ui.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import fr.umlv.jphoto.cela.data.Slide;


/**
 * CellRenderer use for the JTable in JTimeBar.
 * The JTable is only use to show  duration.
 * 
 * @author lbarbisa
 *
 */
public class SlideShowTableRenderer extends DefaultTableCellRenderer {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		
		Slide tbl = (Slide)table.getColumnModel().getColumn(column).getIdentifier();
		if(tbl!=null)
		{
			this.setText("Durée : " + Long.toString(tbl.getDuration()) + " ms");
		}

		return this;
	}
}
