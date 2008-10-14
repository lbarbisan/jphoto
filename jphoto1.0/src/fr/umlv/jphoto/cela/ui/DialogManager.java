/*
 * Créé le 2 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import fr.umlv.jphoto.cela.data.AbstractRoot;
import fr.umlv.jphoto.cela.data.Directory;
import fr.umlv.jphoto.cela.data.RootDirectory;
import fr.umlv.jphoto.cela.data.RootNode;
import fr.umlv.jphoto.cela.data.RootTransition;
import fr.umlv.jphoto.cela.data.TransitionObject;
import fr.umlv.jphoto.cela.model.JphotoTreeModel;
import fr.umlv.jphoto.cela.util.JphotoManager;

/**
 * 
 * The Dialog with allow us to manage Folders or Transition.
 * @author cfodouop
 *
 */
public class DialogManager extends JDialog {
    
    private final static String DIRECTORY_MANAGER_STRING = "Gérer les répertoires.";
    private final static String TRANSITION_MANAGER_STRING = "Gérer les transitions.";
    private final static String ADD_STRING = "<html><b>Ajouter</b></html>";
    private final static String REMOVE_STRING = "<html><b>Retirer </b></html>";
    private final static String OK_STRING = "Valider";
    private final static String CANCEL_STRING = "Annuler";
    
    //ScrollPane
    private final JScrollPane scrollSystem = new JScrollPane();
    private final JScrollPane scrollTree = new JScrollPane();
    private final JViewport view1 = new JViewport();
    private final JViewport view2 = new JViewport();
    
    private static JTree sys;
    protected static JTree tree;
    
    protected JphotoTreeModel model;
    protected JphotoTreeModel oldmodel;
    protected boolean type; //stocke le type de l'arbre que l'on gere : repertoires ou transitions
    private AbstractRoot oldValue = null;
       
    /**
     * Create a Dialog Manager Frame
     * @param owner
     * @param model
     * @param type
     * @throws java.awt.HeadlessException
     */
    public DialogManager(Frame owner, JphotoTreeModel model, boolean type) throws HeadlessException {
        super(owner,true);
        
        initScrollPane();
        initDialogManager(model,type);
        
        add(initDialogPanel());
        
        setTitle(type==JphotoTreeModel.DIRECTORY?DIRECTORY_MANAGER_STRING:TRANSITION_MANAGER_STRING);
        //setSize(600,500);
        pack();
        setResizable(false);
    }
    
    /**
     * @param treeModel
     * @param fileType
     */
    private void initDialogManager(JphotoTreeModel treeModel, boolean fileType) { //type indique si ce sont les repertoires ou les transitions qu'on gere
    	this.model = new JphotoTreeModel();
        oldmodel = treeModel;
        this.type = fileType;
        
        final RootNode root = (RootNode) treeModel.getRoot();
        if(fileType == JphotoTreeModel.DIRECTORY) {
            this.model.setRoot(root.getRootDirectory());
            oldValue = backup(root.getRootDirectory());
        } else {
            this.model.setRoot(root.getRootTransition());
            oldValue = backup(root.getRootTransition());
        }
        
        this.model.addTreeModelListener( new TreeModelListener() { // les listeners
            
            public void treeNodesChanged(TreeModelEvent e) {}
            
            public void treeNodesInserted(TreeModelEvent e) {
                tree.updateUI();
            }
            
            public void treeNodesRemoved(TreeModelEvent e) {
                tree.updateUI();
            }
            
            public void treeStructureChanged(TreeModelEvent e) {
                tree.updateUI();
            }
            
        });
        
    }
    
    private final JButton getButton(String name) {
        JButton b = new JButton(name);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        
        return b;
    }
    
    private final JButton createMoveButton(String name, final JTree from, final JTree to) {
        final JButton b = new JButton(name);
        b.setEnabled(false);
        b.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Object value = from.getLastSelectedPathComponent();
                TreeModel frommodel = from.getModel();
                //boolean insert = true;
                
                if(value != null) { //selection effective
                    if(frommodel instanceof JphotoTreeModel) { //Il faut retirer un repertoire ou une transition
                        
                        if( !(value instanceof AbstractRoot) ) { //ce n'est pas la racine
                            model.removeNode(value, JphotoTreeModel.INSERT_FROM_ROOT, type);
                        } 
                    } else { //Il selectionne un fichier du disque
                        File f = ((FileTreeNode)value).getFile();                   
                        Object node = JphotoManager.testValidity(f, type);
                                                
                        if(node != null) {
                            model.insertNewNode(node,JphotoTreeModel.INSERT_FROM_ROOT, type);
                        }
                    }
                }
            }   
        });

        from.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                b.setEnabled(from.getSelectionPath() != null);
            }
            
        });
        
        to.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                b.setEnabled( !(to.getSelectionPath() != null) );
            }
            
        });
        
        from.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                b.setEnabled(true);
            }

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
            
        });
        to.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                b.setEnabled(false);
            }

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
            
        });
        return b;
    }
    
    /**
     * This method initializes the panel which contents all the components of a DialogManager
     * @return the JPanel of the DialogManager Frame.
     */
    public final JPanel initDialogPanel() {
        LayoutManager layoutManager = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(layoutManager);
        
        JPanel panel0 = getSystemPanel();
        JPanel panel1 = getTreePanel();
        JPanel panel2 = getButtonsADD_REMOVE();
        JPanel panel3 = getButtonsOK_CANCEL();
        
        c.weightx = 1.0;
        c.gridheight = 2;
        c.insets = new Insets(10,15,5,15);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        
        //les boutons ajouter et retirer
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(panel2,c);
        
        // le système de fichier
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        panel.add(panel0,c);
        
        // l'arbre
        c.gridx = 2; 
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.fill = GridBagConstraints.BOTH;
        panel.add(panel1,c);
        
        // les bouton OK et Annuler
        c.gridx = 2;
        c.gridy = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTH;
        panel.add(panel3,c);
        
        return panel;
    }
      
    private final JPanel getButtonsADD_REMOVE() { // les boutons Add et Remove
        JPanel panel = new JPanel(null);
        JButton add = createMoveButton(ADD_STRING,sys,tree); 
        JButton del = createMoveButton(REMOVE_STRING,tree,sys);
        
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(add);
        panel.add(Box.createVerticalStrut(40));
        panel.add(del);
        
        return panel;
    }
    
    private final JPanel getButtonsOK_CANCEL() {
        JPanel panel = new JPanel(null);		
        JButton ok = getButton(DialogManager.OK_STRING);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updateModel();
                Component comp = (Component) e.getSource();
                DialogManager.this.dispose();
                comp.getParent().setVisible(false);
            }
            
        });
        
        JButton cancel = getButton(DialogManager.CANCEL_STRING);
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cancelModifications();
                Component comp = (Component) e.getSource();
                DialogManager.this.dispose();
                comp.getParent().setVisible(false);
            }
            
        });
        //Box box = Box.createHorizontalBox();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.add(ok);
        panel.add(Box.createHorizontalStrut(40));
        panel.add(cancel);
        
        return panel;
    }
    
    private final JPanel getSystemPanel() {
        DefaultTreeModel defaultModel = new DefaultTreeModel(new FileTreeNode(null,null));
        sys = new JTree(defaultModel);
        sys.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);   
        
        this.view1.setView(sys);
        
        sys.expandRow(1);
        JPanel panel = new JPanel();
        panel.add(this.scrollSystem);
        
        return panel;
    }
    
    private final JPanel getTreePanel() {
        DefaultTreeModel defaultModel = new DefaultTreeModel(new FileTreeNode(null,new File("/home/")));
        tree = new JTree(this.model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);   
        
        this.view2.setView(tree);
        
        tree.setShowsRootHandles(true);
        JPanel panel = new JPanel();
        panel.add(this.scrollTree);
        
        return panel;
    }
    
    private void initScrollPane() {
        this.scrollSystem.setViewport(this.view1);
        this.scrollSystem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollSystem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollSystem.setPreferredSize(new Dimension(200,400));
        
        this.scrollTree.setViewport(this.view2);
        this.scrollTree.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollTree.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollTree.setPreferredSize(new Dimension(200,400));
    }
    
    protected void updateModel() {
        final RootNode root = (RootNode) oldmodel.getRoot();
        
        if(type == JphotoTreeModel.DIRECTORY) {
            oldmodel.nodeStructureChanged(root.getRootDirectory());
        } else {
            oldmodel.nodeStructureChanged(root.getRootTransition());
        }
    }
    
    protected void cancelModifications() {
        final RootNode root = (RootNode) oldmodel.getRoot();
        
        if(type == JphotoTreeModel.DIRECTORY) {
            root.setRootDirectory((RootDirectory)oldValue);
            oldmodel.nodeStructureChanged(root.getRootDirectory());
        } else {
            root.setRootTransition((RootTransition)oldValue);
            oldmodel.nodeStructureChanged(root.getRootTransition());
        }
    }
    
    /**
     * Make the backup of an AbstractRoot
     * @param root AbstractRoot to backup
     * @return an another instance of this root.
     */
    private AbstractRoot backup(AbstractRoot root) {
        Object[] childrens = root.toArray();
        AbstractRoot oldRoot = null;
        
        if(type == JphotoTreeModel.DIRECTORY) {
            RootDirectory tmp = new RootDirectory();
            for (Object o: childrens) {
                tmp.add((Directory) o);
            }
            oldRoot = tmp;
        } else {
            RootTransition tmp = new RootTransition();
            for (Object o: childrens) {
                tmp.add((TransitionObject) o);
            }
            oldRoot = tmp;
        }
        
        return oldRoot;
    }
}
