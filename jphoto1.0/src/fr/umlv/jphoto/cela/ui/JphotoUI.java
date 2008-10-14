/*
 * Créé le 10 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.umlv.jphoto.cela.model.JphotoTreeModel;
import fr.umlv.jphoto.cela.ui.JphotoActions.ActionAdd;
import fr.umlv.jphoto.cela.ui.transfert.ListPictureTransferHandler;
import fr.umlv.jphoto.cela.ui.transfert.TransferActionListener;

/**
 *
 *
 * @author cfodouop
 *
 */
public class JphotoUI {

    private static final JFrame frame = new JFrame();
    private static JTimeBar bar = null;
    private final TreePanel treepanel = TreePanel.getTreePanel();
    private final static ViewPanel viewpanel = ViewPanel.getViewPanel();
    private JSplitPane horizontal;
    private JSplitPane vertical;
    
    public JphotoUI() {
        super();
    }
    
    /**
     * @return Renvoie frame.
     */
    public static JFrame getFrame() {
        return frame;
    }
    
    public void initJphotoUI() {
        
    	frame.setLayout(new BorderLayout());
    	Container c = frame.getContentPane();
        /* Make the splitpane */
        horizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treepanel,viewpanel);
		horizontal.setDividerLocation(0.1);
		
		vertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontal, initDnD());
		vertical.setDividerLocation(0.8);
		
        /* ADD viewpanel as a listener on treepanel's tree */
        treepanel.getTree().addTreeSelectionListener(viewpanel);
        
        /* Add component into Frame */
        c.add(vertical,BorderLayout.CENTER);
        JComponent[] menus = createMenu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar((JMenuBar)menus[0]);
        c.add((JToolBar)menus[1],BorderLayout.NORTH);
        frame.setSize(new Dimension(600,400));
        frame.setVisible(true);
    }

    public JPanel initDnD() {
    	viewpanel.getJphotoList().setDragEnabled(true);
    	viewpanel.getJphotoList().setTransferHandler(new ListPictureTransferHandler());
    	
    	JTimeBar timebar = JTimeBar.getJTimeBar();

        timebar.getJlist().setDragEnabled(true);
        timebar.getJlist().setTransferHandler(new ListPictureTransferHandler());
       
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(timebar, BorderLayout.CENTER);
        //panel.setBorder(BorderFactory.createTitledBorder("Table"));
        return panel;
    }
    
    public static JComponent[] createMenu() {
        JComponent[] bar = new JComponent[2];
        JMenuBar menubar = new JMenuBar();
        JToolBar toolbar = new JToolBar();
        
        JMenu menuP = new JMenu(new JphotoActions.ActionMenu("Projets",KeyEvent.VK_P));
        JMenu menuE = new JMenu(new JphotoActions.ActionMenu("Edition",KeyEvent.VK_E));
        JMenu menuA = new JMenu(new JphotoActions.ActionMenu("Affichage",KeyEvent.VK_A));
        JMenu menuO = new JMenu(new JphotoActions.ActionMenu("Organiser",KeyEvent.VK_O));
        JMenu menuT = new JMenu(new JphotoActions.ActionMenu("Outils",KeyEvent.VK_T));
        
        JphotoActions.ActionOpen actionOpen = new JphotoActions.ActionOpen("Ouvrir un projet ...");
        JMenuItem subMenuOpen = new JMenuItem(actionOpen);
        JButton btOpen = new JButton(actionOpen);
        btOpen.setText("");
        JphotoActions.ActionSave actionSave = new JphotoActions.ActionSave("Enregistrer un projet ...");
        JMenuItem subMenuSave = new JMenuItem(actionSave);
        JButton btSave = new JButton(actionSave);
        btSave.setText("");
        JphotoActions.ActionClose actionClose = new JphotoActions.ActionClose("Quitter Jphoto");
        JMenuItem subMenuExit = new JMenuItem(actionClose);
        JButton btExit = new JButton(actionClose);
        btExit.setText("");
        
        menuP.add(subMenuOpen);
        menuP.add(subMenuSave);
        menuP.addSeparator();
        menuP.add(subMenuExit);
        
        toolbar.add(btOpen);
        toolbar.add(btSave);
        toolbar.add(btExit);
        toolbar.addSeparator();
        //toolbar.add(actionSave);
        
        ViewPanel panel = ViewPanel.getViewPanel();
        JCheckBoxMenuItem subMenuList = new JCheckBoxMenuItem(panel.new ActionView(panel,ViewPanel.View.VIEW_LIST));
        JCheckBoxMenuItem subMenuDetails = new JCheckBoxMenuItem(panel.new ActionView(panel,ViewPanel.View.VIEW_DETAILS));
        JTimeBar timebar = JTimeBar.getJTimeBar();
        JCheckBoxMenuItem subMenuListTimeLine = new JCheckBoxMenuItem(timebar.new ActionJTimeBar(timebar,JTimeBar.TypeTimeLineBar.TIMELINE_SIMPLE));
        JCheckBoxMenuItem subMenuTableTimeLine = new JCheckBoxMenuItem(timebar.new ActionJTimeBar(timebar,JTimeBar.TypeTimeLineBar.TIMELINE_ADVANCED));
        JphotoActions.ActionRefresh actionRefresh = new JphotoActions.ActionRefresh("Rafraîchir");
        JMenuItem subMenuRefresh = new JMenuItem(actionRefresh);
        JButton btrefresh = new JButton(actionRefresh);
        
        toolbar.add(btrefresh);
        toolbar.addSeparator();
        ButtonGroup check = new ButtonGroup();
        ButtonGroup chkTimeLineType = new ButtonGroup();
        subMenuList.setSelected(true);
        subMenuListTimeLine.setSelected(true);
        check.add(subMenuDetails);
        check.add(subMenuList);
        chkTimeLineType.add(subMenuListTimeLine);
        chkTimeLineType.add(subMenuTableTimeLine);
        menuA.add(subMenuList);
        menuA.add(subMenuDetails);
        menuA.addSeparator();
        menuA.add(subMenuListTimeLine);
        menuA.add(subMenuTableTimeLine);
        menuA.addSeparator();
        menuA.add(subMenuRefresh);
        
        ActionAdd actionAdd = new JphotoActions.ActionAdd("Ajouter un répertoire",JphotoTreeModel.DIRECTORY);
		JMenuItem subMenuAddD = new JMenuItem(actionAdd);
		JButton btaddD = new JButton(actionAdd);
		btaddD.setText("");
		btaddD.setToolTipText("Ajouter un répertoire...");
		
        JMenuItem subMenuManD = new JMenuItem(new JphotoActions.ActionManager("Gérer les répertoire",JphotoTreeModel.DIRECTORY));
        ActionAdd actionAddT = new JphotoActions.ActionAdd("Ajouter une transition",JphotoTreeModel.TRANSITION);
		JMenuItem subMenuAddT = new JMenuItem(actionAddT);
		JButton btaddT = new JButton(actionAddT);
		btaddT.setText("");
		btaddT.setToolTipText("Ajouter une transition...");
        JMenuItem subMenuManT = new JMenuItem(new JphotoActions.ActionManager("Gérer les transitions",JphotoTreeModel.TRANSITION));
        
        menuO.add(subMenuAddD);
        menuO.add(subMenuManD);
        menuO.addSeparator();
        menuO.add(subMenuAddT);
        menuO.add(subMenuManT);
        
        toolbar.add(btaddD);
        toolbar.add(btaddT);
        JphotoActions.ActionLaunch subMenuLaunch = new JphotoActions.ActionLaunch(timebar,"Lancer le Diaporama");
        menuT.add(subMenuLaunch);
        
        TransferActionListener actionListener = new TransferActionListener();

        JMenuItem menuItem = null;
        menuItem = new JMenuItem("Couper");
        menuItem.setActionCommand((String)TransferHandler.getCutAction().
                 getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setEnabled(false);
        menuE.add(menuItem);
        
        final JMenuItem copy = new JMenuItem("Copier");
        copy.setActionCommand((String)TransferHandler.getCopyAction().
                 getValue(Action.NAME));
        copy.addActionListener(actionListener);
        copy.setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.setMnemonic(KeyEvent.VK_C);
        menuE.add(copy);
        
        final JMenuItem paste = new JMenuItem("Coller");
        paste.setActionCommand((String)TransferHandler.getPasteAction().
                 getValue(Action.NAME));
        paste.addActionListener(actionListener);
        paste.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.setMnemonic(KeyEvent.VK_P);
        menuE.add(paste);
        
        menuE.add(copy);
        copy.setEnabled(false);
        paste.setEnabled(false);
        viewpanel.getJphotoList().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent arg0) {
				boolean enable = ViewPanel.getViewPanel().getJphotoList().getSelectedIndex() != -1;
				copy.setEnabled(enable);
				paste.setEnabled(enable);
			}
        	
        });
        menubar.add(menuP);
        menubar.add(menuE);
        menubar.add(menuA);
        menubar.add(menuO);
        menubar.add(menuT);
        
        bar[0] = menubar;
        bar[1] = toolbar;
        return bar;
    }

    public static void main(String[] args) {
		JphotoUI ui = new JphotoUI();
		
		ui.initJphotoUI();
	}
    
    static void exportToclip(TransferHandler handler, Clipboard clip, JList list, int action) {
    	handler.exportToClipboard(list,clip,action);
    }
    
    protected final static Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
    
}
