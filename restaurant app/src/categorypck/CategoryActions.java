/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorypck;

import internalframepck.IFCategory;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author vaio
 */
public class CategoryActions {
    
    CategoryVisual cv = null;
    ArrayList<Category> arrayCategories = null;
    private static CategoryActions singleton = new CategoryActions( );
    
    /**
     * default constructor of the class
     */
    public CategoryActions(){
        this.cv = new CategoryVisual();
        this.arrayCategories = this.getCategories();
    }
    
   /* Static 'instance' method */

    /**
     * retuns an instance of the class
     *
     * @return CategoryActions
     */
   public static CategoryActions getInstance( ) {
     return singleton;
   }
    

    /**
     * loads the categories of the database and save them in an array
     *
     * @return ArrayList
     */
        public ArrayList<Category> getCategories(){
        ServiceCategory cdb = new ServiceCategory();
        ArrayList<Category> categories;
        if(this.arrayCategories == null){
            categories = cdb.getCategories();
            if(categories.isEmpty()){
                System.out.println("Categorias en CetgoryActions::getCategories está vacio, no hay categorías en al base de datos");
                return null;
            }
            else
                return categories;
        }
        else
            return this.arrayCategories;
    }

    /**
     * return the panel of the categories, with the categories loaded on it
     *
     * @return JScrollPane
     */
        public JScrollPane getPnlCategories(){
        return this.cv.updateCategoriesPanel(this.arrayCategories);
    }

    /**
     * adds a category to the database, if the process is successful it updates the array of categories
     * and also updates the horizontal panel of the main screen with the new category
     *
     * @param name
     * @return Boolean
     */
        public Boolean addCategory(String name){
        ServiceCategory pdb = new ServiceCategory();
        int result = pdb.addCategory(name);
        if(result > 0){
            Category newCategory = new Category(result, name);
            ArrayList categories = this.getCategories();
            categories.add(newCategory);
            this.cv.updateCategoriesPanel(categories);
            return true;
        }
        else
            return false;
    }

    /**
     * when a user clicks on the menu category of the main screen (wich is in the top bar), the flow comes here and can
     * return two different panels, one panel is the add category panel when the user wants to create a new category
     * the other one is the disable/remove category option, this option calls the same display function but depending 
     * if the user wants to disable or remove a category the action associated to the label is different
     *
     * @param option
     * @return
     */
        public JPanel getCategoryPanel(int option){
        JPanel pnl = null;
        IFCategory ifc = new IFCategory();
        switch(option){
            case 1: pnl = ifc.displayAddCategory(this.cv);break;
            case 2: pnl = ifc.displayListCategories(false,this.getCategories(), this.cv);break;
            case 3: pnl = ifc.displayListCategories(true,this.getCategories(), this.cv);break;
        }
        return pnl; 
    }

    /**
     * updates the status of a category. if the disableOption is true the action is
     * change the value of the field disabled of a category in the category array and in the database
     * (that means that if disabled is 1 will become 0 and viceversa)
     * if the disableOption is false this means that the category should be removed
     * from the database and also from the category array
     *
     * @param categoryId
     * @param disabledOption
     */
        public void updateCategoryDisableStatus(int categoryId, Boolean disabledOption){
        ArrayList<Category> categories = this.getCategories();
        Iterator iteratorCategory = categories.iterator();
        Category category;
        ServiceCategory cdb = new ServiceCategory();
        while(iteratorCategory.hasNext()){
            category = (Category) iteratorCategory.next();
            if(category.getId() == categoryId){
                if(disabledOption){
                    category.setDisabled( (category.getDisabled() == 1)?0:1 );
                    cdb.disableCategory(categoryId, category.getDisabled());
                }else{
                    iteratorCategory.remove();
                    cdb.deleteCategory(categoryId);
                }
                this.cv.updateCategoriesPanel(categories);
                break;
            }    
        }
    }

    /**
     * returns the category id of the category name given as a parameter, if there isn't any coincidence 
     * returns zero
     *
     * @param category_name
     * @return int
     */
        public int getCategoryIdByName(String category_name){
        Iterator categoryIterator = this.getCategories().iterator();
        int categoryId=0;
        Category category;
        while(categoryIterator.hasNext()){
            category = (Category) categoryIterator.next();
            if(category_name.equals(category.getName())){
                categoryId=category.getId();
                break;
            }
        }
        return categoryId;
    }
}
