package fr.umlv.jphoto.cela.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreeNode;

import fr.umlv.jphoto.cela.util.JphotoFilter;

/**
 * 
 * A TreeNode which allow us to display File's system user in a Tree.
 * This Class take back course of Remi Forax on JTree Component.
 * @author cfodouop
 *
 */
public class FileTreeNode implements TreeNode {
    
    private final File file;
    private List<FileTreeNode> children = null;
    private final FileTreeNode parent;
    
    private final JphotoFilter filter = JphotoFilter.getJphotoFilter();
    
    /**
     * @param node
     * @param file
     */
    public FileTreeNode(FileTreeNode node, File file) {
        this.parent = node;
        this.file = file;
    }
    
    /**
     * @see javax.swing.tree.TreeNode#getChildAt(int)
     */
    public TreeNode getChildAt(int childIndex) {
        return getChildren().get(childIndex);
    }
    
    /**
     * @see javax.swing.tree.TreeNode#getChildCount()
     */
    public int getChildCount() {
        return getChildren().size();
    }
    
    /**
     * @see javax.swing.tree.TreeNode#getParent()
     */
    public TreeNode getParent() {
        return this.parent;
    }
    
    /**
     * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
     */
    public int getIndex(TreeNode node) {
        return getChildren().indexOf(node);
    }
    
    /**
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    public boolean getAllowsChildren() {
        return true;
    }
    
    /**
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    public boolean isLeaf() {
        if(this.file == null) {
            return false;
        }
        return (this.file.isDirectory() == false);
    }
    
    /**
     * @see javax.swing.tree.TreeNode#children()
     */
    public Enumeration children() {
        return Collections.enumeration(getChildren());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if(getFile() == null) {
            return "Poste de Travail : ";
        }
        if(FileSystemView.getFileSystemView().isRoot(getFile())) {
            return getFile().getAbsolutePath();
        }
        return getFile().getName();
    }
    
    /**
     * 
     */
    public void clear() {
        this.children.clear();
    }
    
    /**
     * @return the list of subDirectories of a file 
     */
    public List<FileTreeNode> getChildren() {
        
        File[] files = null;
        if(this.children != null) {
            return this.children;
        }
        
        ArrayList<FileTreeNode> list = new ArrayList<FileTreeNode>();
        
        if(this.file == null) { // c'est le root
            files = File.listRoots();
            for (File tmpfile : files) {
                if(tmpfile.canRead()) {
                    FileTreeNode node = new FileTreeNode(this,tmpfile);
                    list.add(node);
                }
            }
            this.children = list;
            return list;
        }
        
        files = this.file.listFiles(filter);
        for (File tmpfile : files) {
            if(tmpfile.canRead()) {
                FileTreeNode node = new FileTreeNode(this,tmpfile);
                list.add(node);
            }
        }
        
        this.children = list;
        return list;
    }
    
    /**
     * @return the file's object of this <code>FileTreeNode</code>
     */
    public File getFile() {
        return this.file;
    }
}