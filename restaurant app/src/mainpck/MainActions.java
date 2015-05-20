/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpck;

import categorypck.CategoryActions;
import internalframepck.IFLoading;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import orderpck.OrderActions;
import productpck.ProductActions;
import userpck.User;
import userpck.UserActions;

/**
 * Manage all the actions between the user (clicks in teh interface) and the actions and database
 *
 * @author vaio
 */
public class MainActions{
    
    User user = null;
    MainFrame fr = null;
    private static MainActions singleton = new MainActions( );
    CategoryActions ca = null;
    ProductActions pa = null;
    OrderActions oa = null;
    UserActions ua = null;
    Boolean enableEvent=true;
    JPanel pnlIF = null, pnlInnerIF= null;
    boolean isSuperUser = false;
   
   /* Static 'instance' method */

    /**
     * retuns an instance of the class
     *
     * @return MainActions
     */
   public static MainActions getInstance( ) {
     return singleton;
   }
    
    MainActions(){
    }


    /**
     * main actions, displays the main creen of the application
     *
     */
    public void start(){
        
        this.fr = new MainFrame();
        this.fr.setPreferredSize(new Dimension(1024, 860));
        
        //get username from logged user, if there isn't it takes the default
        this.ua = UserActions.getInstance();
        String username = this.ua.getUsername();
        this.updateMenuBar(username, this.ua.getUserPhotoPath());
        
        //loads the categories
        this.ca = CategoryActions.getInstance();
        JScrollPane categoriesScrllPnl = this.ca.getPnlCategories();
        this.fr.add(categoriesScrllPnl);
        
        //set the panel of the product (but is invisible)
        this.pa = ProductActions.getInstance();
        JScrollPane productScrllPnl = this.pa.getScrllPnlProducts();
        this.fr.add(productScrllPnl);
        
        //set the order panel
        this.oa = OrderActions.getInstance();
        JPanel orderPnl = this.oa.getOrderPnl();
        this.fr.add(orderPnl);
        
        this.inicializePnlIF();
        
        this.fr.pack(); 
        this.fr.setVisible(true);        
    }

    /**
     * updates the top menu of the aplication, it updates all the menus of the bar
     * (user menu, category menu, user info...)    
     *
     * @param username
     * @param photo_path
     */
        public void updateMenuBar(String username, String photo_path){
        this.isSuperUser = this.ua.isSuperUser();
        boolean isLoggedUser = this.ua.isLoggedUser();
        this.fr.displayMenuBar(username, photo_path, this.isSuperUser, isLoggedUser);
    }

    /**
     * displays in the second horizontal panel (the green one) the info of the products 
     * that have a certain category clicked by the user
     *
     * @param category_id
     */
        public void displayCategoryProducts(int category_id){
        if(this.enableEvent){
            this.pa.displayProductsOfCategory(category_id);
        }
    }

    /**
     * displays the internal frame with the info of an especific product
     * it adds a black panel  and over a white panel (black and white panels are the Internal Frame)
     * with the info of the product (name, description, price...)
     *
     * @param productId
     */
        public void showInternalFrameProduct(int productId){
        if(this.enableEvent){
            this.enableEvent=false;
            if(productId > 0){
                JPanel pnl = this.pa.displayDescriptionPanel(productId);
                if(pnl != null){
                    this.pnlInnerIF = pnl;
                    this.addPnlIF(true); 
                }
            }
        }
    }

    /**
     * when a user clicks on the button "add to order" that is in the InternalFrame description, 
     * this function takes the product id and the quantity, updates the order table adding the new product
     * and updates the final price of the order 
     *
     * @param productId
     * @param quantity
     */
        public void addProuctToOrder(int productId, int quantity){
        if(!isSuperUser){
            float discount = this.ua.getUserDiscount();
            this.pa.hideScrllPnlProducts();
            this.oa.addToOrder(productId, quantity);
            this.oa.updatePnlOrder(this.pa.getProducts(),discount);
        }
    }

    /**
     * this function makes the opposite action of the previous function (addProuctToOrder) 
     * removes the product of the table, updates the price and removes the product from the internal array
     *
     * @param productId
     */
        public void removeProuctFromOrder(int productId){
        if(this.enableEvent){
            float discount = this.ua.getUserDiscount();
            this.oa.removeToOrder(productId);
            this.oa.updatePnlOrder(this.pa.getProducts(),discount);
        }
    }
        
    /**
     * removes from the aplication any internal panel, removes the black panel and the white panel 
     * that is over the black and any other info that can be above those two and also the listeners
     *
     */
    public void removeInternalFrame(){
        if(this.pnlIF != null && this.pnlInnerIF != null){
            this.pnlIF.remove(this.pnlInnerIF);
            this.pnlIF.setVisible(false);
            this.fr.validate();
            this.fr.repaint();
            this.enableEvent=true;
            System.out.println("quitando capa");
        }
    }

    /**
     * when a user clicks on the button "pay" we display and internal frame with a recipt with a summary of the
     * order (products, quantities..) and the final price with the discount of the user
     *
     */
    public void showInternalFrameReceipt(){
        if(this.enableEvent){
            float discount = this.ua.getUserDiscount();
            JPanel pnl = this.pa.displayIFReceipt(this.oa.getOrderList(),discount);
            if(pnl != null){
                this.pnlInnerIF = pnl;
                this.addPnlIF(true);
            }
        }
    }

    /**
     * this function makes a reset of the aplication, remove any other internal frame and displays an internal frame
     * with the panel form IFLoading, this internal framse dissapears a few seconds later
     *
     */
    public void launchReload(){
        this.removeInternalFrame();
        IFLoading IFL = new IFLoading();
        this.pnlInnerIF = IFL.displayLoadingLogo();
        this.addPnlIF(false);        
        this.startDelay();
    }

    /**
     * this function makes logout of the logged user, updates the top menu bar with the default user info
     * also launch the reboot internal frame, empty the order array and hide the product horizontal panel
     *
     */
    public void logOutUser(){
        this.ua.logOutUser();
        String username = this.ua.getUsername();
        this.updateMenuBar(username, this.ua.getUserPhotoPath());
        this.launchReload();
        this.resetScreen();  
        this.pa.hideScrllPnlProducts();
    }

    /**
     * when a user clicks in any option of the menu user of the top bar the actions came
     * here and then this funcion calls the pertinent function in the UserActions class
     *
     * @param option
     */
    public void clickUserMenu(int option){
        this.removeInternalFrame();
        if(option < 5)
            this.pnlInnerIF = this.ua.getUserPanel(option);
        else
            this.pnlInnerIF = this.oa.getOrderPanel(option);
        
        if(this.pnlInnerIF != null)
            this.addPnlIF(true);
    }

    /**
     * when a user clicks in any option of the menu product of the top bar the actions 
     * came here and then this funcion calls the perinent function in the PorductActions class
     *
     * @param option
     */
    public void clickProductMenu(int option){
        this.removeInternalFrame();
        this.pa.hideScrllPnlProducts();
        if(this.isSuperUser){
            this.pnlInnerIF = this.pa.getProductPanel(option);
            if(this.pnlInnerIF != null)
                this.addPnlIF(true);
        }
    }

    /**
     * when a user clicks in any option of the menu category of the top bar the actions came 
     * here and then this funcion calls the perinent function in the CategoryActions class   
     *
     * @param option
     */
    public void clickCategoryMenu(int option){
        this.removeInternalFrame();
        this.pa.hideScrllPnlProducts();
        if(this.isSuperUser){
            this.pnlInnerIF = this.ca.getCategoryPanel(option);
            if(this.pnlInnerIF != null)
                this.addPnlIF(true);
        }
    }

    /**
     * this function inserts in the database the current order for teh current userId
     *
     */
    public void saveOrder(){
        int userId = this.ua.getUserId();
        this.oa.insertOrder(userId);
    }
    
    /**
     * this function empty the internal order and updates the order table with the "new" empty order array
     *
     */
    private void resetScreen(){
        float discount = this.ua.getUserDiscount();
        this.oa.emptyOrderList();
        this.oa.updatePnlOrder(this.pa.getProducts(),discount);
    }

    /**
     * starts a delay and anfter 5 seconds removes any internal frame and resets all the aplication 
     * info (user logged, order..)
     *
     */
    private void startDelay(){
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    MainActions ma = MainActions.getInstance();
                    ma.removeInternalFrame();
                    ma.resetScreen();
                }
            }, 5000);
    }
    
    /**
     * ads the inner internal frame to the internal frame (add the white panel to the black panel) also enables 
     * the listener of the internal frame (when we click the black panel it close it self) and displays the black panel
     *
     */
    private void addPnlIF(boolean addListener){
        this.pnlIF.add(this.pnlInnerIF);
        this.pnlIF.setVisible(true);
        this.fr.pack();
        if(addListener)
            this.addPnlIFListener();
       
    }
    
    /**
     * inicialize the internal frame (the black panel) that is not visible unless a user makes an 
     * action that requires displays it
     *
     */
    private void inicializePnlIF(){
        
        this.pnlIF = new JPanel();
     
        this.pnlIF.setBackground(new java.awt.Color(0, 0, 0, 120));
        this.pnlIF.setSize(1024,860);

        //Set the window's location.
        this.pnlIF.setLocation(0,0);
        this.pnlIF.setLayout(null);
        
        this.pnlIF.setVisible(false);
        
        this.fr.add(this.pnlIF, 0);
    }
    
    /**
     * adds the listener of the internal frame that consists in when a user clicks ONLY in the black panel,
     * the internal panel dissapears
     *
     */
    private void addPnlIFListener(){
                
        this.pnlIF.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e)  
            {  
                MainActions ma = MainActions.getInstance();
                System.out.println("click sobre panel negro, borrar capa");
                ma.removeInternalFrame();
            }  
        }); 
    }
}
