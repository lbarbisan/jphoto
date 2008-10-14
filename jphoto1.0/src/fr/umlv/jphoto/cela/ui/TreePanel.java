/*
 * Créé le 1 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.TreeSelectionModel;

import fr.umlv.jphoto.cela.data.*;
import fr.umlv.jphoto.cela.model.JphotoTreeModel;
import fr.umlv.jphoto.cela.ui.renderer.JphotoTreeCellRenderer;

/**
 *
 *
 * @author cfodouop
 *
 */
public class TreePanel extends JPanel {

    private final JTree tree = new JTree();
    private JphotoTreeModel  model;
    private final RootNode root = new RootNode();
    private static TreePanel treepanel = null;
    
    public static TreePanel getTreePanel() {
        if(treepanel == null) {
          treepanel = new TreePanel();  
        }
        return treepanel;
    }

    private TreePanel() {
        super(new BorderLayout());
        add(createTree(),BorderLayout.CENTER);
    }
    
    /**
     * @return a <code>JScrollPane</code> which contains the JTree of TreePanel
     */
    public JScrollPane createTree() {
        this.model = new JphotoTreeModel(root);
        this.tree.setModel(this.model);
        
        tree.setRootVisible(false);
        tree.setCellRenderer(new JphotoTreeCellRenderer());
    	this.tree.setExpandsSelectedPaths(true);
		this.tree.setShowsRootHandles(true);
		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(this.tree);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		return scrollPane;
    }

    /**
     * @return Renvoie model.
     */
    public JphotoTreeModel getModel() {
        return model;
    }
    
    /**
     * @return the root of the tree contains in this <code>Treepanel</code>.
     */
    public RootNode getRoot() {
        return root;
    }
    /**
     * @return the root node for directories.
     */
    public RootDirectory getRootDirectory() {
        return root.getRootDirectory();
    }
    
    /**
     * @return the root node for transitions.
     */
    public RootTransition getRootTransition() {
        return root.getRootTransition();
    }
    
    /**
     * @return Renvoie tree.
     */
    public JTree getTree() {
        return tree;
    }
    /**
     * 
     */
    public void refresh() {
        root.refresh();
        model.nodeStructureChanged(root);
        tree.expandRow(0);
    }
}
