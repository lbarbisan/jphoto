/*
 * Créé le 6 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.model;

import java.awt.EventQueue;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractListModel;

import fr.umlv.jphoto.cela.data.*;

import fr.umlv.jphoto.cela.util.JphotoFilter;
import fr.umlv.jphoto.cela.util.JphotoManager;

/**
 * Bridge of ListModel which manage the picture of a {@link fr.umlv.jphoto.cela.data.Directory Directory}.
 * This model contains an ArrayList of {@link fr.umlv.jphoto.cela.data.Picture Picture} where we store <i>pictures</i>
 * or transitions that we found.
 *
 * @author cfodouop
 *
 */
public class JphotoAdaptaterListModel extends AbstractListModel {
    
  	private static final long serialVersionUID = 1L;
  
  	/**
  	 * List of pictures currently displayed.
  	 */
    protected ArrayList<Picture> list;
    /**
     * A reference to the node currently traverse.
     */
    protected Node currentnode = null;
    /**
     * ID of the Thread which are currently running
     */
    protected long currentThread = 0;
    
    /**
     * Create a new Instance of {@link JphotoAdaptaterListModel JphotoAdaptaterListModel}.
     * This constructor initializes {@link JphotoAdaptaterListModel#list list}
     */
    public JphotoAdaptaterListModel() {
       list = new ArrayList<Picture>();
    }
    
    /**
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        return list.size();
    }

    /**
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
        return list.get(index);
    }
    
    /**
     * @param childs
     * @param runningThread
     */
    public void addInBackground(final Picture[] childs, final long runningThread) {
        try {
            EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    if(currentThread == runningThread) {
                            addNewInterval(childs);
                    }
                }
                
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if(cause instanceof RuntimeException)
                throw (RuntimeException)cause;
            else if (cause instanceof Error)
                throw (Error)cause;
            else
                throw new Error(cause);
        }
    }
    
    /**
     * @param childs
     * @param runningThread
     */
    public void addInBackground(final Picture childs, final long runningThread) {
        
        try {
            EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    if(currentThread == runningThread) {
                        addNewInterval(childs);
                    }
                }
                
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if(cause instanceof RuntimeException)
                throw (RuntimeException)cause;
            else if (cause instanceof Error)
                throw (Error)cause;
            else
                throw new Error(cause);
        }
    }


    protected void addNewInterval(Picture[] childs) {     
        int index = list.size();
        Collections.addAll(list, childs);
        fireIntervalAdded(JphotoAdaptaterListModel.this, index, index+childs.length-1);
    }
    
    public void addNewInterval(Picture childs) {     
        int index = list.size();
        list.add(childs);
        fireIntervalAdded(this, index, index);
    }
    
    public void addNewIntervalAt(int index, Picture childs) {     
       
            list.add(index, childs);
            fireIntervalAdded(this, index, index);
    }
    
    public void moveInterval(int index,int newIndex)
    {
        Picture picture = list.get(index);
        list.remove(index);
        list.add(newIndex, picture);
        fireContentsChanged(this, index, newIndex);
    }
    
    public void removeInterval(int index)
    {
        list.remove(index);
        fireIntervalRemoved(this, index,index);
    }
    
    /**
     * Start the scan to display pictures.
     * This method creates and initializes a Thread to start the scan and set <code>currentThread</code>
     * with ID of the thread and then start it.
     */
    public void scanList() {
        
        Thread t = new Thread(runner);
        currentThread = t.getId(); //On change l'id de la thread courante
        list.clear();
        t.start();
    }
    
    /**
     * traverse the File <code>file</code> and gets the pictures that it contains.
     * The File </code>file</code> must be a Folder. 
     * @param file to traverse
     */
    public void scanDirectory(File file){
        
        if(file.exists() == false)
            return;
        
        for(File f: file.listFiles(JphotoFilter.getJphotoFilter())) {
            if(Thread.currentThread().getId() != currentThread)
                return ;
            if(f.exists()) {
                if(f.isFile() && JphotoManager.isJarFile(f) == false)
                    addInBackground(new AbstractPicture(f),Thread.currentThread().getId());
                else if(f.isDirectory())
                    scanDirectory(f);
            }
        }
    
    }
    
    /**
     * @param tr RootTransition to traverse
     */
    public void scanTransition(RootTransition tr) {
        for(TransitionObject t: tr.getChildren()) {
            if(Thread.currentThread().getId() != currentThread)
                return ;
        	addInBackground(t,Thread.currentThread().getId());
        }
    }

    /**
     * @param node
     */
    public void checkNewPath(Node node){
    	if(node.isViewable()) {
    	    currentnode = node;
    	    scanList();
        }
    }
  
    final Runnable runner = new Runnable() {

        public void run() {
            
            if(currentnode instanceof Directory)
                scanDirectory(((Directory)currentnode).getDirectory());
            else
                scanTransition((RootTransition) currentnode);
        } 
    };
}
