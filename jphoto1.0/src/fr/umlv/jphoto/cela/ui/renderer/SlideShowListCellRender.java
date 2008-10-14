/*
 * Created on 25 sept. 2004
 *
 */
package fr.umlv.jphoto.cela.ui.renderer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

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
public class SlideShowListCellRender extends DefaultListCellRenderer  {
    
    /* (non-Javadoc)
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
            if(path != null && !path.equals(""));
            //this.setIcon(new ImageIcon(Rescale(path)));
            this.setIcon(new ImageIcon(picture.getPictureIcon()));
        }
        catch(Exception e)
        {
            System.out.println("Format d'image non valide");
        }
        
        return this;
    }
    //FIXME A REVOIR
    private ImageIcon rescale(ImageIcon image) {
        
        int thumbWidth = 100;
        int thumbHeight = 100;
        double thumbRatio = (double)thumbWidth / (double)thumbHeight;
        int imageWidth = image.getIconWidth();
        int imageHeight = image.getIconHeight();
        double imageRatio = (double)imageWidth / (double)imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int)(thumbWidth / imageRatio);
        } else {
            thumbWidth = (int)(thumbHeight * imageRatio);
        }
        
        BufferedImage thumbImage = new BufferedImage(thumbWidth, 
                thumbHeight, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image.getImage(), 0, 0, thumbWidth, thumbHeight, null);
        return new ImageIcon(thumbImage); 
    }
    
    private Image Rescale(String imagePath)
    {
        
        // load image from FILE
        Image image = Toolkit.getDefaultToolkit().getImage(imagePath);
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        try{
            mediaTracker.waitForID(0);
        }
        catch(InterruptedException e)
        {
            System.out.println("Problème dans le resizing de l'image");
        }
        
        // determine thumbnail size from WIDTH and HEIGHT
        int thumbWidth = 100;
        int thumbHeight = 100;
        double thumbRatio = (double)thumbWidth / (double)thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double)imageWidth / (double)imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int)(thumbWidth / imageRatio);
        } else {
            thumbWidth = (int)(thumbHeight * imageRatio);
        }
        // draw original image to thumbnail image object and
        // scale it to the new size on-the-fly
        BufferedImage thumbImage = new BufferedImage(thumbWidth, 
                thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
        return thumbImage ; 
    }
    
}

