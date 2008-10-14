/*
 * Créé le 13 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.model;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import fr.umlv.jphoto.cela.data.*;

/**
 *
 * A treemodel with manage the nodes that we display in jphoto's tree.
 * @author cfodouop
 *
 */
public class JphotoTreeModel implements TreeModel {
    
    /**
     * A static boolean which specify if user insert a <code>Node</code
     * from the main frame or from the DialogManage window.
     */
    public static boolean INSERT_FROM_ROOT = true;
    /**
     * A static boolean which specify if user insert a <code>Node</code
     * from the main frame or from the DialogManage window.
     */
    public static boolean INSERT_FROM_NODE = false;
    
    private AbstractRoot root = null;
    
    private ArrayList<TreeModelListener> listenersList = new ArrayList<TreeModelListener>();
    EventListenerList listenerList = new EventListenerList();
    
    /**
     * Type of Node that we use.<br>
     */
    public static boolean DIRECTORY = true;
    /**
     * Type of Node that we use.<br>     
     */
    public static boolean TRANSITION = false;
    
    /**
     * get a new instance of Jphototreemodel
     */
    public JphotoTreeModel() {
        super();
    }

    /**
     * get a new instance of Jphototreemodel and set the root of this model.
     * @param root the root associate with this new instance
     */
    public JphotoTreeModel(AbstractRoot root) {
        this.root = root;
    }
    
    /**
     * Returns the root of the tree.  Returns <code>null</code>
     * only if the tree has no nodes.
     *
     * @return  the root of the tree
     */
    public Object getRoot() {
        return root;
    }
    
    /**
     * set the associate with this model and prevents all views that model
     * has changed.
     * @param root the new root for this model.
     */
    public void setRoot(AbstractRoot root) {
        this.root = root;
        nodeStructureChanged(root);
    }
    
    /**
     * Returns the child of <code>parent</code> at index <code>index</code>
     * in the parent's
     * child array.  <code>parent</code> must be a node previously obtained
     * from this data source. This should not return <code>null</code>
     * if <code>index</code>
     * is a valid index for <code>parent</code> (that is <code>index >= 0 &&
     * index < getChildCount(parent</code>)).
     *
     * @param   parent  a node in the tree, obtained from this data source
     * @param index the index's child
     * @return  the child of <code>parent</code> at index <code>index</code>
     */
    public Object getChild(Object parent, int index) {
        return ((AbstractRoot)parent).get(index);
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        return ((AbstractRoot)parent).size();
    }

    /**
     * Returns <code>true</code> if <code>node</code> is a leaf.
     * It is possible for this method to return <code>false</code>
     * even if <code>node</code> has no children.
     * A directory in a filesystem, for example,
     * may contain no files; the node representing
     * the directory is not a leaf, but it also has no children.
     *
     * @param   node  a node in the tree, obtained from this data source
     * @return  true if <code>node</code> is a leaf
     */
    public boolean isLeaf(Object node) {
        return (node instanceof Directory || node instanceof TransitionObject);
    }

    /**
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        return ((AbstractRoot)parent).indexOf(child);
    }

    /**
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener l) {
        this.listenersList.add(l);
        listenerList.add(TreeModelListener.class, l);
    }

    /**
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener l) {
        this.listenersList.remove(l);
        listenerList.remove(TreeModelListener.class, l);
    }
    
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the parameters passed into 
     * the fire method.
     *
     * @param source the node where new elements are being inserted
     * @param path the path to the root node
     * @param childIndices the indices of the new elements
     * @param children the new elements
     * @see EventListenerList
     */
    protected void fireTreeNodesInserted(Object source, Object[] path, 
                                        int[] childIndices, 
                                        Object[] children) {
        // Guaranteed to return a non-null array
        //Object[] listeners = this.listenersList.toArray();
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path, 
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesInserted(e); 
            }          
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the parameters passed into 
     * the fire method.
     *
     * @param source the node where elements are being removed
     * @param path the path to the root node
     * @param childIndices the indices of the removed elements
     * @param children the removed elements
     * @see EventListenerList
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path, 
                                        int[] childIndices, 
                                        Object[] children) {
        // Guaranteed to return a non-null array
        //Object[] listeners = this.listenersList.toArray();
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path, 
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesRemoved(e); 
            }          
        }
    }
    
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the parameters passed into 
     * the fire method.
     *
     * @param source the node where the tree model has changed
     * @param path the path to the root node
     * @param childIndices the indices of the affected elements
     * @param children the affected elements
     * @see EventListenerList
     */
    protected void fireTreeStructureChanged(Object source, Object[] path, 
                                        int[] childIndices, 
                                        Object[] children) {
        // Guaranteed to return a non-null array
        //Object[] listeners = this.listenersList.toArray();
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path, 
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeStructureChanged(e); 
            }          
        }
    }
    
    protected AbstractRoot[] getPathToRoot(AbstractRoot node) {
        AbstractRoot[] path = null;
        
        if(node == null) {
            return null;
        }
        
        if(node.equals(getRoot())) { //Appel a partir du gestionnaire
            path = new AbstractRoot[1];
            path[0] = node;
        } else {
            path = new AbstractRoot[2];
            path[0] = (AbstractRoot) getRoot();
            path[1] = node;
        }
        
        return path;
    }
    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     * @param fromWhere tell if insert is initialize from main frame or DialogManager Frame.
     * @param type type of node.
     * @return an array of AbtsractRoot giving the path from the root to the
     *         specified node 
     * 
     */
    protected AbstractRoot[] getPathToRoot(boolean fromWhere, boolean type) {
        AbstractRoot[] path = null;
        AbstractRoot currentRoot = (AbstractRoot) getRoot();
        
        if(fromWhere == INSERT_FROM_ROOT) {
            path = new AbstractRoot[1];
            path[0] = currentRoot;
        } else {
            path = new AbstractRoot[2];
            path[0] = currentRoot;
            path[1] = (AbstractRoot) (type == DIRECTORY? ((RootNode) currentRoot).getRootDirectory():((RootNode) currentRoot).getRootTransition());
        }
        
        return path;
    }
    
    /**
     * Invoked this to insert newChild.
     * This is the preferred way to add children as it will create
     * the appropriate event.
     * @param newChild child
     * @param fromWhere from where we insert this node
     * @param type node's type
     */
    public void insertNewNode(Object newChild, boolean fromWhere, boolean type) {
     
        int childIndex = ((AbstractRoot) getRoot()).addNewNode(newChild);
        int[] index = {childIndex};
        Object[] childs = new Object[] {newChild};
        
        fireTreeNodesInserted(this, getPathToRoot(fromWhere,type), index, childs);
    }
    /**
     * Message this to remove node from its parent. This is the
     * preferred way to remove a node as it handles the event creation
     * for you.
     * @param child chidl to remove
     * @param fromWhere from where we remove this node
     * @param type
     */
    public void removeNode(Object child, boolean fromWhere, boolean type) {
        
        int childIndex = ((AbstractRoot) getRoot()).removeNode(child);
        int[] index = {childIndex};
        Object[] childs = new Object[] {child};
        
        fireTreeNodesRemoved(this, getPathToRoot(fromWhere,type), index, childs);
    }
    
    /**
     * Invoke this method if you've totally changed the children of
     * node and its childrens children...  This will post a
     * treeStructureChanged event.
     * @param node
     */
   public void nodeStructureChanged(AbstractRoot node) {
       if(node != null) {
          fireTreeStructureChanged(this, getPathToRoot(node), null, null);
       }
   }

}
