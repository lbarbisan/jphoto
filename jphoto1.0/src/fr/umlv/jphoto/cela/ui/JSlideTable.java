package fr.umlv.jphoto.cela.ui;
import javax.swing.JTable;
/*
 * Created on 28 nov. 2004
 */

/**
 * @author laurent barbisan
 *
 * This class is only for override configureEnclosingScrollPane method.
 *  
 */
public class JSlideTable extends JTable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * this method is override, because we don't want to add the TableHeader 
	 * to the JScrollPane HederColumn
	 */
	protected void configureEnclosingScrollPane() {
			// Do nothing
	}
}
