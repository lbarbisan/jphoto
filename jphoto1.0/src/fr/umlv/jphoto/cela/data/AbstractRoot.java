/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * An abtract class to represents <code>Node</code> which can have childs.<br>
 * This class implements some methods that we can shared between another classes.
 * @see RootNode
 * @see RootDirectory
 * @see RootTransition
 * @author cfodouop
 *
 */
public abstract class AbstractRoot<E> extends Node implements RootInterface<E> {

   private static final long serialVersionUID = 1L;
    /**
     * <code>children</code> contains all the children of an <code>AbstractRoo</code>
     */
    private final ArrayList<E> children = new ArrayList<E>();

    /**
     * Get a new AbstractRoot.
     * @param name - the name of the node 
     * @param description - a descritption for this node
     */
    public AbstractRoot(String name, String description) {
        super(name, description);
    }
    
    /**
     * Add a new child in this <code>AbstractRoot</code>
     * @param o - a reference to the new child
     * @return <tt>true</tt> (as per the general contract of Collection.add).
     */
    public boolean add(E o) {
        return children.add(o);
    }
    
    /**
     * Insert a new child at the specified index
     * @param index - the position where the node will be inserted
     * @param element the Object that we wants to add.
     */
    public void add(int index, E element) {
        children.add(index, element);
    }
    
    /**
     * Add a Collection of <code>E</code> objects in the list.
     * @param c collection to add.  
     * @return true if the collection has add otherwise false.
     */
    public boolean addAll(Collection< ? extends E> c) {
        clear();
        return children.addAll(c);
    }
    
    /**
     * clear the list of the ArrayList include in this <code>AbstractRoot</code>.<br>
     * After this operation the method {@link #size() size} return 0.
     * @see #size()
     */
    public void clear() {
        children.clear();
    }
    
    /**
     * get a reference on the element which is at the position <code>index</code>  
     * @param index
     * @return element at the position index.
     */
    public E get(int index) {
        return children.get(index);
    }
    
    /**
     * get the index of an object if it exists in this AbstractRoot
     * @param o the element that we found
     * @return an indice > 0 if <code>o</code> is present.
     */
    public int indexOf(Object o) {
        return children.indexOf(o);
    }
    
    /**
     * Find the status of this AbtractRoot
     * @return true if this AbstractRoot does'nt have any children.
     */
    public boolean isEmpty() {
        return children.isEmpty();
    }
    
    /**
     * get an iterator on this AbstractRoot's children
     * @return an iterator 
     */
    public Iterator<E> iterator() {
        return children.iterator();
    }
    
    /**
     * remove an object from an AbstractRoot
     * get an index and remove the object locate at these position in 
     * the abstractroot.
     * @param index of object to remove
     * @return a reference to the object remove.
     * @see #remove(Object)
     */
    public E remove(int index) {
        return children.remove(index);
    }
    
    /**
     * remove an object from this AbtractRoot.
     * @param o object to remove
     * @return true if the operation success, otherwise false.
     */
    public boolean remove(Object o) {
        return children.remove(o);
    }
    
    /**
     * 
     * @return the size of the ArrayList {@link #children children}
     */
    public int size() {
        return children.size();
    }
    
    /**
     * get an Array of object that this root contains.
     * @return an Array
     */
    public Object[] toArray() {
        return children.toArray();
    }
    
    /**
     * a reference to list of children.
     * @return children.
     */
    public ArrayList<E> getChildren() {
        return children;
    }
    
    /**
     * add a child in this AbstractRoot
     * @param newChild child to add.<br>
     * @return index where this child is added
     */
    public int addNewNode(Object newChild) {
        int childIndex = Node.findWhereToInsert(getChildren(), newChild);        
        add(childIndex,(E)newChild);
        return childIndex;
    }
    
    /**
     * remove the object child.
     * @param child child to remove
     * @return the index of <code>child</code> into this AbstractRoot
     */
    public int removeNode(Object child) {
        int childIndex = indexOf(child);
        remove(child);
        return childIndex;
    }
    
    /**
     * Refresh the contains of an AbtractRoot.<br>
     * For {@link RootDirectory RootDirectory} this method verify that all folders
     * are presents on the disk.<br>
     * It's the same operation for {@link RootTransition RootTransition}  
     *
     */
    public abstract void refresh();
}
