/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframepck;

import categorypck.Category;
import categorypck.CategoryVisual;
import java.util.ArrayList;
import javax.swing.JPanel;
/**
 * Actions of categories that are displayed in the internal frame (add, disables and remove category)
 * 
 * @author Moisés Díaz, email:moisesdiazcantos@gmail.com
 */
public class IFCategory extends InternalFrame{

    /**
     * Displays a list of labels with the image of the category, if the category is disabled 
     * it appears the image with a red border, this function displays the labels for the option
     * disabled/remove, in the moment when the user makes click in the label of the category 
     * selected the action removes or disables the element depending the value of showDisabledOption
     *
     * @param showDisabledOption
     * @param categories
     * @param cv
     * @return JPanel
     */
        public JPanel displayListCategories(Boolean showDisabledOption, ArrayList<Category> categories, CategoryVisual cv){
        
        int pnlWhiteHeight=600;
        int pnlWhiteWidth=900;
        this.pnlWhite.setSize(pnlWhiteWidth, pnlWhiteHeight);
        this.pnlWhite.setLocation(50,50);
        this.pnlWhite.setLayout(null);
        JPanel pnl = cv.displayListCategories(showDisabledOption, categories, pnlWhiteWidth, pnlWhiteHeight);
        pnl.setSize(pnlWhiteWidth, pnlWhiteHeight);
        this.pnlWhite.add(pnl); 
        return super.display();
    }

    /**
     * if the user wants to create a new category, this function is called, this function add to a panel 
     * a textname a textfield and a buton for create this category and returns this panel to be inserted
     * in the InternalFrame
     *
     * @param cv
     * @return JPanel
     */
        public JPanel displayAddCategory(CategoryVisual cv){
        
        JPanel pnlWhite = new JPanel();
        this.pnlWhite.setSize(500, 300);
        this.pnlWhite.setLocation(300,200);

        JPanel pnl = cv.displayAddCategoryPanel();
        this.pnlWhite.add(pnl);
        return super.display();
    }
    
}
