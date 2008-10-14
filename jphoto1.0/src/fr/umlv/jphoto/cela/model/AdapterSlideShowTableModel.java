/*
 * Created on 24 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.model;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Create an adapter from {@link SlideShowAdapterTableColumnModel}.
 * This Adapter is used for {@link fr.umlv.jphoto.cela.ui.JSlideTable}
 * @author lbarbisa
 */
public class AdapterSlideShowTableModel extends AbstractTableModel {

	private TableColumnModel model;
	
	/**
	 * Constructor use to adapt a {@link TableColumnModel} to this class.
	 * @param model {@link TableColumnModel} to adapt to.
	 */
	public AdapterSlideShowTableModel(TableColumnModel model)
	{
		this.model = model;
		/* Utilisé pour  mettre à jout la JTable */
		model.addColumnModelListener(new TableColumnModelListener()
				{
					public void columnAdded(TableColumnModelEvent e) {
						fireTableStructureChanged();
						fireTableDataChanged();
					}

					public void columnRemoved(TableColumnModelEvent e) {
						fireTableStructureChanged();
					}

					public void columnMoved(TableColumnModelEvent e) {
						fireTableStructureChanged();
					}

					public void columnMarginChanged(ChangeEvent e) {
						fireTableStructureChanged();
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
					}
					
				});
	}
	
	
	/**
	 * return the name of the specified Column.
	 * <br>
	 * the return String object is created from the {@link javax.swing.table.TableColumn}
	 * of the {@link SlideShowAdapterTableColumnModel} toString() method.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public String getColumnName(int column) {
		return model.getColumn(column).toString();
	}
	
	/**
	 * return the Row count.
	 * 
	 * Here return always 1.
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return 1;
	}
	
	/**
	 * return column count.
	 * <br>
	 * the return int is created from the {@link javax.swing.table.TableColumn}
	 * of the {@link SlideShowAdapterTableColumnModel} getColumnCount method.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return model.getColumnCount();
	}

	/**
	 * return the value of the specified row and column.
	 * This model is used for timeline, the return object is a {@link fr.umlv.jphoto.cela.data.PSlide}
	 * object. the return object doesn't depends of row index.
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
			return model.getColumn(columnIndex).getIdentifier();	
	}
	
}
