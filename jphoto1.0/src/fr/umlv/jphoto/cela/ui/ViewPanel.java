/*
 * Créé le 27 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import fr.umlv.jphoto.cela.data.Node;
import fr.umlv.jphoto.cela.data.TransitionObject;
import fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel;
import fr.umlv.jphoto.cela.model.JphotoTableModel;
import fr.umlv.jphoto.cela.model.listener.JphotoListDataListener;
import fr.umlv.jphoto.cela.ui.renderer.JphotoListCellRender;

/**
 *
 *
 * @author cfodouop
 *
 */
public class ViewPanel extends JPanel implements TreeSelectionListener {

    private JphotoAdaptaterListModel model = new JphotoAdaptaterListModel();
    private JphotoTableModel tablemodel = null;
    private final JList jphotoList = new JList();
    private final JTable jphotoTable = new JTable();
    private JScrollPane view = new JScrollPane();
    
    private static ViewPanel viewpanel = null;
    
    public enum View {
		VIEW_LIST { String value() { return "Liste"; }},
		VIEW_DETAILS { String value() { return "Détail";}};
		
		abstract String value();
	}
    
    public static ViewPanel getViewPanel() {
        if(viewpanel == null) {
            viewpanel = new ViewPanel();
        }
        return viewpanel;
    }

    private ViewPanel() {
    	super(new BorderLayout());
        initView();
        add(view,BorderLayout.CENTER);
    }
    
    /**
     * Init the ViewPanel wich displays pictures of folders that
     * the user choose.<br>
     * By default the pictures are display as thumbnails.
     * 
     */
    private void initView() {
        jphotoList.setModel(model);
        tablemodel = new JphotoTableModel(model);
        jphotoTable.setModel(tablemodel);
        
        final JphotoListDataListener listenr = new JphotoListDataListener(tablemodel,model);
        jphotoList.setCellRenderer(new JphotoListCellRender());
        jphotoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jphotoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jphotoList.setVisibleRowCount(-1);
        
        jphotoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        jphotoList.setDragEnabled(true);
        jphotoTable.setDragEnabled(true);
        view.setViewportView(jphotoList);
        
    }

    public void changeView(View val) {
		if(val.compareTo(View.VIEW_DETAILS) == 0) {
			view.setViewportView(jphotoTable);
			//jphotoList.setVisible(false);
		} else if(val.equals(View.VIEW_LIST)) {
			view.setViewportView(jphotoList);
			//jphotoTable.setVisible(false);
		}
		
		view.revalidate();
	}
    
    /* (non-Javadoc)
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getNewLeadSelectionPath();
        if(path != null) {
        	Object value = path.getLastPathComponent();
        	Node node = null;       	
        	/*
        	 * Pour le cas particulier des TransitionsObject on recupere le root
        	 * 
        	 */
        	if(value instanceof TransitionObject)
        		node = (Node) path.getParentPath().getLastPathComponent(); //FIXME tester si bonne valeur
        	else
        		node = (Node) path.getLastPathComponent();
        	
            model.checkNewPath(node);
        }
    }
    
    class ActionView extends AbstractAction {

        private ViewPanel.View viewtype;
        private ViewPanel panel;
               
        /**
         * @param viewtype
         */
        public ActionView(ViewPanel panel, ViewPanel.View viewtype) {
            super(viewtype.value());
            this.panel = panel;
            this.viewtype = viewtype;
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
           panel.changeView(viewtype); 
        }
        
    }
	/**
	 * @return Returns the jphotoList.
	 */
	public JList getJphotoList() {
		return jphotoList;
	}
	/**
	 * @return Returns the jphotoTable.
	 */
	public JTable getJphotoTable() {
		return jphotoTable;
	}
}
