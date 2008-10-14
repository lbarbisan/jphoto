/*
 * Créé le 2 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.model;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import fr.umlv.jphoto.cela.data.Picture;

/**
 *
 * The table model of Jphoto project.
 * This model is a bridge for {@link fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel JphotoAdaptaterListModel}.
 * 
 * It's use to display details of pictures or transitions.
 * @author cfodouop
 *
 */
public class JphotoTableModel extends AbstractTableModel {

  	private static final long serialVersionUID = 1L;
    private JphotoAdaptaterListModel model;
    
    private String[] header = {"File's Name","Size","Last Modification"};

    /**
     * @param model
     */
    public JphotoTableModel(JphotoAdaptaterListModel model) {
        super();
        this.model = model;
    }
    
    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return model.getSize();
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return header.length;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return header[column];
    }
    
    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Picture value = (Picture) model.getElementAt(rowIndex);
        
        switch (columnIndex) {
        case 0:
            return value.getPictureName();
                        
        case 1:
            return Long.valueOf(value.getPictureSize());
        
        case 2:
            return DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(value.getPictureLastModifiedDate()));
            //ong.valueOf(value.getPictureLastModifiedDate());
            
        default:
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * @return Renvoie header.
     */
    public String[] getHeader() {
        return header;
    }
	/**
	 * @return Returns the model.
	 */
	public JphotoAdaptaterListModel getModel() {
		return model;
	}
	
	public void addRoow(Picture picture) {
		model.addNewInterval(picture);
		fireTableStructureChanged();
	}
}
