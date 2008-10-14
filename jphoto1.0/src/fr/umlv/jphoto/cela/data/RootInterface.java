/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * An interface for Roots.
 * @see fr.umlv.jphoto.cela.data.AbstractRoot
 * @see fr.umlv.jphoto.cela.data.RootNode
 * @see fr.umlv.jphoto.cela.data.RootDirectory
 * @see fr.umlv.jphoto.cela.data.RootTransition
 * @author cfodouop
 *
 */
public interface RootInterface<E> {
	/**
     * Add a new child in this <code>AbstractRoot</code>
     * @param o - a reference to the new child
     * @return <tt>true</tt> (as per the general contract of Collection.add).
     */
    public abstract boolean add(E o);

    /**
     * Insert a new child at the specified index
     * @param index - the position where the node will be inserted
     * @param element the Object that we wants to add.
     */
    public abstract void add(int index, E element);

    /**
     * Add a Collection of <code>E</code> objects in the list.
     * @param c collection to add.  
     * @return true if the collection has add otherwise false.
     */
    public abstract boolean addAll(Collection< ? extends E> c);
     
    /**
     * clear the list of the ArrayList include in this <code>AbstractRoot</code>.<br>
     * After this operation the method {@link #size() size} return 0.
     * @see #size()
     */
    public abstract void clear();

    /**
     * get a reference on the element which is at the position <code>index</code>  
     * @param index
     * @return element at the position index.
     */
    public abstract E get(int index);

    /**
     * get the index of an object if it exists in this AbstractRoot
     * @param o the element that we found
     * @return an indice > 0 if <code>o</code> is present.
     */
    public abstract int indexOf(Object o);

    /**
     * Find the status of this AbtractRoot
     * @return true if this AbstractRoot does'nt have any children.
     */
    public abstract boolean isEmpty();

    /**
     * get an iterator on this AbstractRoot's children
     * @return an iterator 
     */
    public abstract Iterator<E> iterator();

    /**
     * remove an object from an AbstractRoot
     * get an index and remove the object locate at these position in 
     * the abstractroot.
     * @param index of object to remove
     * @return a reference to the object remove.
     * @see #remove(Object)
     */
    public abstract E remove(int index);

    /**
     * remove an object.
     * @param o object to remove.
     * @return true if operation success
     */
    public abstract boolean remove(Object o);

    /**
     * @return number of object that contains a Root
     */
    public abstract int size();

    /**
     * 
     * @return an Array
     */
    public abstract Object[] toArray();
}