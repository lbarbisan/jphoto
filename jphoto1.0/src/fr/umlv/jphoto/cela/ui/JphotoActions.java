/*
 * Créé le 4 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import fr.umlv.jphoto.cela.model.JphotoTreeModel;
import fr.umlv.jphoto.cela.util.JphotoFilter;
import fr.umlv.jphoto.cela.util.JphotoManager;

/**
 *
 *
 * @author cfodouop
 *
 */
public class JphotoActions {
    
	private static final JphotoManager manager = JphotoManager.getJphotoManager();
	
    protected static final JFileChooser fc = new JFileChooser();

	private static final String STRING_OPEN16 = "/resources/icons/open_16.gif";
	private static final ImageIcon open = (ImageIcon) manager.getIcon(STRING_OPEN16);
	
	private static final String STRING_CLOSE16 = "/resources/icons/exit_16.gif";
	private static final ImageIcon close = (ImageIcon) manager.getIcon(STRING_CLOSE16);
	
	private static final String STRING_SAVE16 = "/resources/icons/save_16.gif";
	private static final ImageIcon save = (ImageIcon) manager.getIcon(STRING_SAVE16);
	
	private static final String STRING_REFRESH16 = "/resources/icons/refresh_16.gif";
	private static final ImageIcon refresh = (ImageIcon) manager.getIcon(STRING_REFRESH16);
	
	private static final String STRING_INSERT1 = "/resources/icons/insert_16.gif";
	private static final ImageIcon insertD = (ImageIcon) manager.getIcon(STRING_INSERT1);
	
	private static final String STRING_INSERT2 = "/resources/icons/insert_116.gif";
	private static final ImageIcon insertT = (ImageIcon) manager.getIcon(STRING_INSERT2);
	
    static {
        fc.setFileFilter(new ImageFilter());
    }
    
    /**
     *
     * A filter which assume to display only pictures and jar's files.
     * @author cfodouop
     *
     */
    private static final class ImageFilter extends FileFilter {
        
        /**
         * 
         */
        public ImageFilter() {
            super();
        }
        
        /**
         * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
         */
        public boolean accept(File f) {
            if(f.isDirectory()) {
                return true;
            }
            return JphotoFilter.endsWithGoodExtension(f);
        }
        
        /**
         * @see javax.swing.filechooser.FileFilter#getDescription()
         */
        public String getDescription() {
            return "Directories or jar's files or images(.gif, .jpg etc.)";
        }
        
    }
    
    /**
     * Action call when user want to add a Directory or a Transition.
     *
     *
     * @author cfodouop
     *
     */
    public static final class ActionAdd extends AbstractAction {
        
		private static final long serialVersionUID = 1L;
		/**
		 * type of Node that we want to add
		 */
		private boolean type;
        
        /**
         * @param name name of this action
         * @param type of Node to add
         */
        public ActionAdd(String name, boolean type) { //Controler les VK_KEYS
            super(name,type==true?insertD:insertT);
            putValue(ACTION_COMMAND_KEY, (type == true)?"AddD":"AddT");
            int key = type == true?KeyEvent.VK_A:KeyEvent.VK_T;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, new Integer(key));
            
            this.type = type;
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            setDialog();
            int returnValue = fc.showOpenDialog((Component) e.getSource());
            
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                Object node = JphotoManager.testValidity(f, type);
            
                if(f.exists()) {
                    if(node != null) {
                        TreePanel panel = TreePanel.getTreePanel();
                        panel.getModel().insertNewNode(node,JphotoTreeModel.INSERT_FROM_NODE, type);                                    
                    } else {
                    	JOptionPane.showMessageDialog(JphotoUI.getFrame(),"Erreur a la lecture du fichier " + f.getName(),"Une erreur est survenue"
                    			,JOptionPane.OK_OPTION);
                    	System.err.println("Erreur a la lecture du fichier " + f.getName());
                    }
                }
            }
        }
        
        private void setDialog() {
            fc.setDialogTitle((type == JphotoTreeModel.DIRECTORY)?"Ajouter un repertoire":"Ajouter une transition");
            if(type) {
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            } else {
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            }
        }
    }
    
    /**
     *
     * Action call when the user close Jphoto's main frame.
     * @author cfodouop
     *
     */
    public static final class ActionClose extends AbstractAction {
        
		private static final long serialVersionUID = 1L;

		/**
         * @param name
         */
        public ActionClose(String name) {
            super(name,close);
            putValue(ACTION_COMMAND_KEY,"Exit");
            //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_Q));
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            /*
             * FIXME Verifier avant de sortir si l'utilisateur a modifier
             * des données.
             */
            System.exit(1);
        }
        
    }
    
    /**
     *
     * Action call when the use want to open a jphoto's project.
     * @author cfodouop
     *
     */
    public static final class ActionOpen extends AbstractAction {
        
		private static final long serialVersionUID = 1L;

		/**
         * @param name action name
         */
        public ActionOpen(String name) {
            super(name,open);
            putValue(ACTION_COMMAND_KEY,"Open");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            fc.setDialogTitle("Ouvrir un projet");
            int returnValue = fc.showOpenDialog((Component) e.getSource());
            
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                System.out.println(fc.getSelectedFile());
                JphotoManager.open(TreePanel.getTreePanel().getModel(), fc.getSelectedFile());
            }
        }
        
    }
    
    
    /**
     *
     * Action call when user want to manage Folders or Transitions.
     * @author cfodouop
     *
     */
    public static final class ActionManager extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private boolean type;
        
        /**
         * @param name
         * @param type
         */
        public ActionManager(String name, boolean type) { //Controler les VK_KEYS
            super(name);
            putValue(ACTION_COMMAND_KEY, (type == true)?"ManD":"ManT");
            int key = (type == true)?KeyEvent.VK_G:KeyEvent.VK_E;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, new Integer(key));
            
            this.type = type;
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            TreePanel panel = TreePanel.getTreePanel();
            DialogManager manager = new DialogManager(JphotoUI.getFrame(),panel.getModel(),type); //FIXME a modifier
            manager.setVisible(true);
        }
        
    }
    
    
    /**
     *
     * Action with allow us to create an action associated with a menu.
     * @author cfodouop
     *
     */
    public static final class ActionMenu extends AbstractAction {
    	
		private static final long serialVersionUID = 1L;

		/**
         * @param name menu's name
		 * @param mnemonic the key associate with this menu
         */
        public ActionMenu(String name, int mnemonic) {
            super(name);
            putValue(MNEMONIC_KEY,new Integer(mnemonic));
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {}
        
    }
    
    /**
     *
     * Action call when user want to refresh frame. 
     * @author cfodouop
     *
     */
    public static final class ActionRefresh extends AbstractAction {
        
		private static final long serialVersionUID = 1L;

		/**
         * @param name name of this action
         */
        public ActionRefresh(String name) {
            super(name,refresh);
            putValue(ACTION_COMMAND_KEY,"Refresh");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            TreePanel.getTreePanel().refresh();
        }
        
    }
       
    /**
     *
     * Action call when the user want to save a project.
     * @author cfodouop
     *
     */
    public static final class ActionSave extends AbstractAction {
        
        /**
         * 
         */
        public ActionSave() {
            super();
        }
        
        /**
         * @param name action name
         */
        public ActionSave(String name) {
            super(name,save);
            putValue(ACTION_COMMAND_KEY,"Save");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        }
        
        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            fc.setDialogTitle("Enregistrer un projet");
            int returnValue = fc.showSaveDialog((Component) e.getSource());
            
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                System.out.println(fc.getSelectedFile());
                JphotoManager.save(TreePanel.getTreePanel().getModel(), fc.getSelectedFile());
            }
        }
        
    }
    
     public static final class ActionLaunch extends AbstractAction {
       
   	private JTimeBar timebar;
       /**
        * @param name
        */
       public ActionLaunch(JTimeBar timebar, String name) {
           super(name);
           putValue(ACTION_COMMAND_KEY,"Lancement");
           putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
           putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
           this.timebar = timebar;
       }
       
       /* (non-Javadoc)
        * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
        */
       public void actionPerformed(ActionEvent e) {
       	
       	JSlideShow.Launch(new JSlideShow(timebar.getColumnModel().getSlideShow()));
       }
       
   }
   
}
