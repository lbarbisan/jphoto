/*
 * Created on 25 sept. 2004
 *
 */
package fr.umlv.jphoto.cela.ui.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.border.TitledBorder;

import fr.umlv.jphoto.cela.data.Picture;


/**
 * @author Administrateur
 *
 */
public class JphotoListCellRender extends DefaultListCellRenderer  {
    
    /**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        super.getListCellRendererComponent( list, value,index, isSelected,cellHasFocus );
        this.setText("");
        Picture picture = (Picture)value;
        
        //this.setToolTipText(picture.getPicturePath());
        this.setBorder(BorderFactory.createTitledBorder(null,picture.getPictureName() ,TitledBorder.CENTER, TitledBorder.BOTTOM));
        try
        {
            String path = picture.getPicturePath();
            this.setIcon(new ImageIcon(picture.getPictureIcon()));
        }
        catch(Exception e)
        {
            System.out.println("Format d'image non valide");
        }
        
        return this;
    }
    
}

