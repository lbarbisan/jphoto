/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;


/**
 * Class which manage {@link fr.umlv.jphoto.cela.data.RootDirectory RootDirectory} and 
 * {@link fr.umlv.jphoto.cela.data.RootTransition RootTransition}
 * <br>
 * This class allow us to have only one Tree to display Folders and Transition of a project.
 * @author cfodouop
 *
 */
public class RootNode extends AbstractRoot<AbstractRoot> {
    
    private static final long serialVersionUID = 1L;
    /**
     * Default name for Root.
     */
    private static final String ROOT = "Jphoto";
    /**
     * A reference to the RootDirectory.
     */
    private RootDirectory rootDirectory = null;
    /**
     * A reference to the RootTransition
     */
    private RootTransition rootTransition = null;
    
    /**
     * get a default rootnode instance.
     * @see #RootNode()
     */
    public RootNode() {
        this(ROOT,null);
        createDefaultNodes();
    }
    
    /**
     * get a <code>RootNode</code> with this parameters.<br>
     * @param name the name of <code>Root</code>
     * @param description a description for this project.
     */
    public RootNode(String name, String description) {
        super(name, description);
    }
    
    /**
     * get a reference to the <code>RootDirectory</code>.<br> 
     * @return a rootDirectory.
     */
    public RootDirectory getRootDirectory() {
        return rootDirectory;
    }
    
    /**
     * set the RootDirectory of this project.
     * @param rootDirectory the new rootDirectory.
     */
    public void setRootDirectory(RootDirectory rootDirectory) {
        this.rootDirectory.addAll(rootDirectory.getChildren());
    }
    
    /**
     * get a reference to the <code>RootTransition</code>.<br> 
     * @return a rootTransition.
     */
    public RootTransition getRootTransition() {
        return rootTransition;
    }
    
    /**
     * set the RootTransition of this project.
     * @param rootTransition the new rootTransition.
     */
    public void setRootTransition(RootTransition rootTransition) {
        this.rootTransition.addAll(rootTransition.getChildren());
    }
    
    /**
     * Create the default nodes for a RootNode's instance.<br>
     * This method set the rootDirectory and the rootTransition fields.
     *
     */
    private void createDefaultNodes() {
        rootDirectory = new RootDirectory();
        rootTransition = new RootTransition();
        
        add(getRootDirectory());
        add(getRootTransition());
    }
    
    /** 
     * @see fr.umlv.jphoto.cela.data.AbstractRoot#addNewNode(java.lang.Object)
     */
    public int addNewNode(Object newChild) {
        
        if(newChild instanceof Directory) {
            return rootDirectory.addNewNode(newChild);
        }
        
        return rootTransition.addNewNode(newChild);      
    }

    /** 
     * @see fr.umlv.jphoto.cela.data.AbstractRoot#removeNode(java.lang.Object)
     */
    public int removeNode(Object child) {
        
        if(child instanceof Directory) {
            return rootDirectory.removeNode(child);
        }
        
        return rootTransition.removeNode(child);
    }
    
    /** 
     * @see fr.umlv.jphoto.cela.data.AbstractRoot#refresh()
     */
    public void refresh() {
        rootDirectory.refresh();
        rootTransition.refresh();
    }
}
