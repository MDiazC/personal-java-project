/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframepck;

import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author vaio
 */


public class InternalFrame{
    
    /**
     * default params of the panel
     *
     */
    public JPanel pnlWhite;
    private int pnlWhiteWidth=400;
    private int pnlWhiteHeight =600;

    /**
     * default constructor for the class
     *
     */
    public InternalFrame() {
      
        this.pnlWhite = new JPanel();

        this.pnlWhite.setBackground(new java.awt.Color(255, 255, 255));
        this.pnlWhite.setSize(this.pnlWhiteWidth,this.pnlWhiteHeight);
        this.pnlWhite.setLocation(400,50);
        this.pnlWhite.setLayout(new GridBagLayout());

        //Set the window's location.
        this.pnlWhite.setVisible(true);
    }

    /**
     * returns the panel with the event listener added
     *
     * @return JPanel
     */
    public JPanel display(){
        this.addWhitePanelListener();
        return this.pnlWhite;
    }
    
    /**
     * retuns the width of teh panel
     *
     * @return int
     */
    public int getPnlWhiteWidth(){
        return this.pnlWhiteWidth;
    }
    
    /**
     * return the height of the panel
     *
     * @return int
     */
    public int getPnlWhiteHeight(){
        return this.pnlWhiteHeight;
    }
    
    /**
     * add to the panel an event listener, that avoids that the internal dissapears when teh user
     * makes click in teh white panel (propagation from the black panel event listener close
     *
     * @return int
     */
    private void addWhitePanelListener(){
        this.pnlWhite.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e)  
            {  
                
            }  
        }); 
    }
}