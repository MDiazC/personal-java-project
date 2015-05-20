/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframepck;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * displays a internal frame that symbolizes the reboot of the aplication
 * 
 * @author vaio
 */
public class IFLoading extends InternalFrame{

    /**
     * displays a panel that emulates a rebooting screen after the user finish the process
     *
     * @return JPanel
     */
        public JPanel displayLoadingLogo(){

        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        
        this.addLogo(c);
        this.addLblLoading(c);
        
        return super.display();
    }

    /**
     * adds a text to the internal frame
     *
     * @params c
     */
    private void addLblLoading(GridBagConstraints c) {
        //Add a label with the name of the product
        
        JLabel lblLoading = new JLabel("Reiniciando proceso de venta...");
        lblLoading.setFont(new Font("Serif", Font.PLAIN, 23));
        lblLoading.setVisible(true);
        c.insets=new Insets(40,20,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        this.pnlWhite.add(lblLoading, c);
    }

    /**
     * adds the logo of the enterprise to the internal frame
     *
     * @params c
     */
    private void addLogo(GridBagConstraints c) {
        //Add the image of the product, if it don't have an image it put a default image
        ImageIcon image = loadImage("img/imagepackage/logo/minilogo_small.png",150, 100);
        if(image  != null){
            
            JLabel imagelabel2 = new JLabel("", SwingConstants.CENTER);
            imagelabel2.setIcon(image);
            imagelabel2.setSize(200, 200);
            imagelabel2.setVisible(true);
            imagelabel2.setBackground(new java.awt.Color(0, 0, 0, 120));
            
            c.insets=new Insets(20,20,20,20);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weighty = 0.0;
            c.ipadx = 0;
            c.ipady = 30;
            
            this.pnlWhite.add(imagelabel2, c);
        }
    }
    
    /**
     * Adds a image in a ImageIcon
     *
     * @param pathImage
     * @param imageWidth
     * @param imageHeight
     * @return ImageIcon
     */
    private ImageIcon loadImage(String pathImage,int imageWidth, int imageHeight) {
        try {
            BufferedImage img = ImageIO.read(new File(pathImage));
            if(img == null){
                System.out.println("Error al cargar imagen "+pathImage);
                return null;
            }
            Image dimg = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(dimg);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }       
    }
}
