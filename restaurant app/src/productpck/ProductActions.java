/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productpck;

import categorypck.CategoryActions;
import internalframepck.IFProduct;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author vaio
 */
public class ProductActions {
    
    ProductVisual pv = null;
    ArrayList<Product> arrayProducts = null;
    private static ProductActions singleton = new ProductActions( );
    
    /**
     * default construction of the class
     */
    public ProductActions(){
        this.pv = new ProductVisual();
        this.arrayProducts = loadProducts();
    }
    
    /**
     * retuns an instance of the class
     *
     * @return ProductActions
     */
   public static ProductActions getInstance( ) {
     return singleton;
   }

    /**
     * returns the arrayList with all the products
     *
     * @return ArrayList
     */
    public ArrayList<Product> getProducts(){
        return this.arrayProducts;
    }

    /**
     * get all products from database
     *
     * @return ArrayList
     */
    public ArrayList<Product> loadProducts(){
        ServiceProduct pdb = new ServiceProduct();
        ArrayList<Product> products = new ArrayList<Product>();
        Product product = null;
        products = pdb.getProducts();
        
        if(products.isEmpty()){
            System.out.println("Error al cargar als cetgorias, estan vacia. ProductsAction::getProduct");
            return null;
        }
        else
            return products;
    }
    
    /**
     * inserts a new product to the database qand also to teh local array of products
     *
     * @param category_name
     * @param name
     * @param description
     * @param price
     * @return Boolean
     */
    public Boolean addProduct(String category_name, String name, String description, float price){
        ServiceProduct pdb = new ServiceProduct();
        CategoryActions ca = CategoryActions.getInstance();
        int categoryId = ca.getCategoryIdByName(category_name);
        if(categoryId > 0){
            int result = pdb.addProduct(categoryId, name, description, price);
            if(result > 0){
                Product product = new Product(result,categoryId, name, description, price);
                this.arrayProducts.add(product);
                return true;
            }
        }
        return false;
    }
        
    /**
     * updates the products panel of the main screen with the products of a selected category
     *
     * @param category_id
     */
    public void displayProductsOfCategory(int category_id){
        ArrayList<Product> newProducts = new ArrayList<Product>();
        Product product;
        boolean add;
        if(!this.arrayProducts.isEmpty()){
            ListIterator iterator = this.arrayProducts.listIterator();
            while(iterator.hasNext()){
                product = (Product) iterator.next();
                if(!product.isEmpty() && product.getCategoryId() == category_id){
                     add =newProducts.add(product);
                     if(add == false)
                         System.out.println("Problema al devolver lista filtrada por category id, no se ha insertado "+product.getName());
                }
            }
        }
        this.pv.updateProductsPanel(newProducts);
    }

    /**
     * returns the panel of the products
     *
     * @return JScrollPane
     */
    public JScrollPane getScrllPnlProducts(){
        return this.pv.getScrllPnlProducts();
    }
    
    //

    /**
     * hides the panel of the products
     */
    public void hideScrllPnlProducts(){
        this.pv.getScrllPnlProducts().setVisible(false);
    }

    /**
     * returns the label with the image of the product
     *
     * @param elem
     * @param pnlProducts
     * @return JLabel
     */
   /* public JLabel insertProductInPanel(Product elem, JPanel pnlProducts){
        
        ImageIcon image = loadImage(elem.getPhotoPath(),150, 150);
        if(image  != null){
            JLabel imagelabel2 = new JLabel();
            imagelabel2.setIcon(image);
            imagelabel2.setSize(150, 150);
            imagelabel2.setVisible(true);
            imagelabel2.setBorder(new EmptyBorder(10,0,0,0));
            imagelabel2.setBackground(new java.awt.Color(255, 255, 255, 120));
            imagelabel2.setOpaque(true);
 
            return imagelabel2;
        }
        return null;
    }
    
    /**
     * Adds a image in a ImageIcon
     *
     * @param pathImage
     * @param imageWidth
     * @param imageHeight
     * @return ImageIcon
     */
    /*private ImageIcon loadImage(String pathImage,int menuItemWidth, int menuItemHeight) {
        try {
            BufferedImage img = ImageIO.read(new File(pathImage));
            if(img == null){
                System.out.println("Error al cargar imagen "+pathImage);
                return null;
            }
            Image dimg = img.getScaledInstance(menuItemWidth, menuItemHeight, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(dimg);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }       
    }*/

    /**
     * returns an object type product according to the productId given
     *
     * @param productId
     * @return Product
     */
    public Product getProductById(int productId){
        ArrayList<Product> newProducts = new ArrayList<Product>();
        Product product = null;
        ListIterator iterator = this.arrayProducts.listIterator();
        while(iterator.hasNext()){
            product = (Product) iterator.next();
            if(!product.isEmpty() && product.getId() == productId){
                break;
            }
        }
        return product;
    }

    /**
     * when the super user clicks on any product option of the top menu bar the flow comes here and 
     * calls to the correct function
     *
     * @param option
     * @return JPanel
     */
    public JPanel getProductPanel(int option){
        JPanel pnl = null;
        IFProduct ifp = new IFProduct();
        switch(option){
            case 1: pnl = ifp.displayAddProduct(this.pv);break;
            case 2: pnl = ifp.displayListProduct(false, this.getProducts(), this.pv);break;
            case 3: pnl = ifp.displayListProduct(true, this.getProducts(), this.pv);break;
        }
        return pnl; 
    }

    /**
     * if a superuser wan to change the status of a product (disable it or remove) the flow comes here and do the action
     *
     * @param productId
     * @param disabledOption
     */
    public void updateProductStatus(int productId, Boolean disabledOption){
        ServiceProduct pdb = new ServiceProduct();
        Product product;
        ArrayList<Product> productsList = this.getProducts();
        Iterator iteratorProduct = productsList.iterator();
        while(iteratorProduct.hasNext()){
            product = (Product) iteratorProduct.next();
            if(productId == product.getId()){
                if(disabledOption){
                    int updateDisabledValue = (product.getDisabled() == 1)?0:1;
                    product.setDisabled(updateDisabledValue);
                    pdb.disableProduct(productId, updateDisabledValue);
                }
                else{
                    iteratorProduct.remove();
                    pdb.deleteProduct(productId);
                }
                break;
            }
        }
    }

    /**
     * returns the panel with the description of the product
     *
     * @param productId
     * @return JPanel
     */
    public JPanel displayDescriptionPanel(int productId){
        JPanel pnl = null;
        Product product;
        product = this.getProductById(productId);
        if(product != null && !product.isEmpty()){
            IFProduct ifp = new IFProduct();
            int spnnrDefValue = 1;
            pnl = ifp.displayDescription(product,spnnrDefValue, this.pv);
        }
        return pnl;
    }

    /**
     * returns the panel with the receipt of the order
     *
     * @param orderList
     * @param discount
     * @return JPanel
     */
    public JPanel displayIFReceipt(Map<Integer, Integer> orderList, float discount){
        IFProduct ifp = new IFProduct();
        return ifp.displayReceipt(orderList, discount, this.pv);
    }
}
