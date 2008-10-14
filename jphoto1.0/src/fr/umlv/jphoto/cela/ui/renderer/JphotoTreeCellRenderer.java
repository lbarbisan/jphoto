/*
 * Créé le 11 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.ui.renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import fr.umlv.jphoto.cela.data.Directory;
import fr.umlv.jphoto.cela.data.Node;
import fr.umlv.jphoto.cela.util.JphotoManager;

/**
 *
 *
 * @author cfodouop
 *
 */
public class JphotoTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	public JphotoTreeCellRenderer() {

		super();
	}
	
    /* (non-Javadoc)
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
    	
    	Node node = (Node) value;
    	super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
    	if(node instanceof Directory) {
    		setIcon(leafDir);
    	}
    	
    	return this;
    }
    
    //Les icones a utiliser.
    //FIXME RAJOUTER LES ICONES
    private static final String STRING_FOLDER = "/resources/icons/blue_folder.gif";
	private static final ImageIcon leafDir = (ImageIcon) JphotoManager.getJphotoManager().getIcon(STRING_FOLDER);
}
