/*
 * TransferActionListener.java is used by the 1.4
 * DragPictureDemo.java example.
 */
package fr.umlv.jphoto.cela.ui.transfert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/*
 * A class that tracks the focused component.  This is necessary
 * to delegate the menu cut/copy/paste commands to the right
 * component.  An instance of this class is listening and
 * when the user fires one of these commands, it calls the
 * appropriate action on the currently focused component.
 */
public class TransferActionListener implements ActionListener,
                                              PropertyChangeListener {
    private JComponent focusOwner = null;
    
    public TransferActionListener() {
        KeyboardFocusManager manager = KeyboardFocusManager.
           getCurrentKeyboardFocusManager();
        manager.addPropertyChangeListener("permanentFocusOwner", this);
    }
    
    public void propertyChange(PropertyChangeEvent e) {
        Object o = e.getNewValue();
        if (o instanceof JComponent) {
            focusOwner = (JComponent)o;
        } else {
            focusOwner = null;
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if (focusOwner == null)
            return;
        String action = (String)e.getActionCommand();
        Action a = focusOwner.getActionMap().get(action);
        if (a != null) {
            a.actionPerformed(new ActionEvent(focusOwner,
                                              ActionEvent.ACTION_PERFORMED,
                                              null));
        }
    }
}
