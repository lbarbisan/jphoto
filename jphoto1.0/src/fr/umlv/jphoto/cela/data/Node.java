/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class wich represents a node which appear in the tree of the user. <br>
 * A <code>Node<code> has two fields : his name and his description.
 *  
 * @author cfodouop
 *
 */
public abstract class Node implements Serializable, Comparable {
    
    /**
     * A <code>description</code> of the node
     */
    private String description;
    
    /**
     * The <code>name</code> of the node.
     */
    private String name;
    
    /**
     * <code>viewable</code> test if a node can be scanned
     * @see Directory
     * @see RootTransition
     */
    protected boolean viewable;
    
    /**
     * Make a new Node which have a name and a description.
     * 
     * @param name the name of the node
     * @param description a description for the node.
     */
    public Node(String name, String description) {
        this.name = name;
        this.description = description;
        viewable = false;
    }
    
    /**
     * get the description of one node.
     * 
     * @return the description of the node.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * get the name of one node
     * @return the name of the node.
     */
    public String getName() {
        return name;
    }
    
    /**
     * set the description of the node.
     * 
     * @param description description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * set the name of the node
     * 
     * @param name name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * This method is use to know if a <code>Node</code>(his contains) can be
     * display in the {@link fr.umlv.jphoto.cela.ui.ViewPanel ViewPanel }.
     * @return <code>true</code> if we can view the contains of the not otherwise <code>false</code>.
     */
    public boolean isViewable() {
        return viewable;
    }
    
    /**
     * @param viewable viewable à définir.
     */
    public void setViewable(boolean viewable) {
        this.viewable = viewable;
    }
    
    /**
     * Compare a node to an another Node.<br>
     * this method is use to sort Nodes in a Tree. it uses the method compareTo() of 
     * String Object.<br> 
     * @param o an another node 
     * @return the result.
     * @see java.lang.Comparable#compareTo(T)
     * @see #findWhereToInsert(ArrayList, Object)
     */
    public int compareTo(Object o) {
        Node node = (Node)o;
        return toString().compareTo(node.toString());
    }
    
    /**
     * Get a String representation of a <code>Node</code>.
     * 
     * @return the name of the node.
     */
    public String toString() {
        return getName();
    }
    
    /**
     * Get the indew where we can insert the Object node in the ArrayList list.<br>
     * This method is use to keep <i>list</i> sorted.
     * In a normal use <i>node</i> is an instance of Node and <i>list</i> contains
     * Directory or Transitions.<br>
     * This method use the method compareTo to find the position where we can insert 
     * <i> node</i>.<br>
     * The integrety of this method must be assume by the user. 
     * 
     *  
     * @param list list to scan
     * @param node new Object that we want to insert into the list
     * @return the position where we should insert node in list if we want to have a 
     * sorted list.
     */
    public static int findWhereToInsert(ArrayList list, Object node) {
        int index = 0;
        int size = list.size();
        boolean find = false;
        int resultat = 0;
        
        while(index < size && find != true) {
            resultat = ((Node) node).compareTo(list.get(index));
            if(resultat <= 0) { //nouveau noeud plus petit
                find = true;
            } else {
                index++;
            }
        }
        
        return index;
    }
}
