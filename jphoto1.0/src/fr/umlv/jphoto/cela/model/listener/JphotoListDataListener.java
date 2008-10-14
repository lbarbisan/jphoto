/*
 * Créé le 11 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.model.listener;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel;
import fr.umlv.jphoto.cela.model.JphotoTableModel;

/**
 *
 * A listener which listening {@link fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel JphotoAdaptaterListModel}.
 * This listener fire good events to the good {@link fr.umlv.jphoto.cela.model.JphotoTableModel JphotoTableModel}
 * associate with the source.
 * @author cfodouop
 *
 */
public class JphotoListDataListener implements ListDataListener {

    private JphotoTableModel listener;
        
    /**
     * @param listener the TableModel which listening ListModel's changing 
     * @param source the ListModel which fire events.
     */
    public JphotoListDataListener(JphotoTableModel listener,
            JphotoAdaptaterListModel source) {
        super();
        this.listener = listener;
        source.addListDataListener(this);
    }
    
    /**
     * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
     */
    public void intervalAdded(ListDataEvent e) {
        listener.fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
    }

    /**
     * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
     */
    public void intervalRemoved(ListDataEvent e) {
        listener.fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
    }

    /**
     * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
        listener.fireTableDataChanged();
    }

}
