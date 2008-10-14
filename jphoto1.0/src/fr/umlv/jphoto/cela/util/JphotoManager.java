/*
 * Créé le 4 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fr.umlv.jphoto.cela.data.Directory;
import fr.umlv.jphoto.cela.data.RootNode;
import fr.umlv.jphoto.cela.data.TransitionObject;
import fr.umlv.jphoto.cela.model.JphotoTreeModel;
import fr.umlv.jphoto.cela.ui.JphotoUI;

/**
 *
 *
 * @author cfodouop
 *
 */
public class JphotoManager {

	private static JphotoManager manager = null;
	
	private JphotoManager() {
		super();
	}
	
    public static Object testValidity(File file, boolean type) {
        Object value = null;
        System.out.println("on ajoute");
        if(file.isDirectory() && type == JphotoTreeModel.DIRECTORY) {
            value = new Directory(file);
        } else if(file.isFile() && type == JphotoTreeModel.TRANSITION) {
        	System.out.println("transition add");
            if(file.getName().endsWith("jar")) {
                value = TransitionObject.getTransitionFromJar(file);
                System.out.println("ouveau " + value);
            }
        }        
        return value;
    }
    
    /**
     * Save changes made by the user into a file.
     * 
     * @param file the file that we use 
     */
    public static void save(JphotoTreeModel treemodel, File file) {
        
        RootNode root = (RootNode) treemodel.getRoot();
        
    	try {
			FileOutputStream ostream = new FileOutputStream(file);
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			p.writeObject(root);
			p.flush();
			ostream.close();
		} 
		catch (Exception e) {
			JOptionPane.showConfirmDialog(JphotoUI.getFrame(),"","Erreur a la sauvegarde",
					JOptionPane.OK_OPTION);
			System.err.println("Une erreur lors de l'enregistrement de l'arbre "
					+ "dans le fichier "+file.getName()+" est survenue. ");
		}

    }
    
    /*
     * A l'ouverture il faut verifier que les chemins vers les repertoires sont valides
     * Par exemple, quand l'utilisateur change de machine, les chemins ne sont plus valides.
     */
    public static void open(JphotoTreeModel treemodel, File file) {
        
        RootNode root = null;
        
    	try {
			FileInputStream istream = new FileInputStream(file);
			ObjectInputStream p = new ObjectInputStream(istream);
			root = (RootNode)p.readObject();
			istream.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(JphotoUI.getFrame(),"Une erreur dans la lecture du fichier "
					+file.getName()+" est survenue. ",
					"Erreur de chargement",JOptionPane.OK_OPTION);
			System.err.println("Une erreur dans la lecture du fichier "
					+file.getName()+" est survenue. ");
		}
		
		if(root != null) {
		    root.refresh(); //On met l'arbre a jour
		    treemodel.setRoot(root);
		}
    }
    
    public static JphotoManager getJphotoManager() {
    	if(manager == null) {
    		manager = new JphotoManager();
    	}
		return manager;
	}

	/**
     * Load the Icon which corresponds to the file's name given
     * by the user
     * @param iconFileName the icon file's name
     * @return an Icon or <code>null</code> if the file is not find. 
     */
    public Icon getIcon(String iconFileName) {
    	URL url = JphotoManager.this.getClass().getResource(iconFileName);
    	if(url == null)
    		return null;
    	return new ImageIcon(url);
    }
    
    /**
     * Test if the a file is a jar file.<br>
     * this method test if the file's name ends with <i>jar</i> 
     * @param file file to test
     * @return true if we have a jar's file, <code>false</code> otherwise
     */
    public static boolean isJarFile(File file) {
    	if(file.getName().endsWith("jar"))
    		return true;
    	else
    		return false;
    }
}
