/*
 * Created on 24 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.umlv.jphoto.cela.ui.renderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import fr.umlv.jphoto.cela.data.Slide;


/**
 * CellRenderer use for TableHeader in the JTimerBar
 *
 * @author lbarbisa
 *
 */
public class HeaderSlideShowRenderer extends DefaultTableCellRenderer {

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
		
		/* Construit la bordure */
		this.setBorder(BorderFactory.createEtchedBorder());
		Slide tbl = (Slide)table.getColumnModel().getColumn(column).getIdentifier();
		if(tbl!=null)
		{
			this.setIcon(new ImageIcon(tbl.getThumbnail())); //.getScaledInstance(40,40,0)));
			this.setPreferredSize(new Dimension(30,30));
	}
		
		return this;
	}
}
