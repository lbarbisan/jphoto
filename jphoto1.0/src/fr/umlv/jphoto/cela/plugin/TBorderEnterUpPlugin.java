/*
 * Created on 6 déc. 2004
 */
package fr.umlv.jphoto.cela.plugin;

import fr.umlv.jphoto.Plugin;
import fr.umlv.jphoto.Transition;

/**
 * @author Administrateur
 */
public class TBorderEnterUpPlugin implements Plugin{

	public static void main(String[] args) {
	}

	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Plugin#createTransition()
	 */
	public Transition createTransition() {
		return new TBorderEnterUp();
	}
}
