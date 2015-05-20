/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframepck;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import productpck.Product;
import productpck.ProductVisual;

/**
 * Actions of product that are displayed in the internal frame (orders by date and remove order)
 *
 * @author vaio
 */
public class IFProduct extends InternalFrame{

    /**
     * displays a list of images of products that can be removed or disabled depending of the value 
     * of the boolean disabeldOption if the boolean is true the element clicked will be disabled 
     * if is false the element will be deleted the two actiosn share the same panel the difference 
     * is the action because the boolean
     *
     * @param disabeldOption
     * @param products
     * @param pv
     * @return JPanel
     */
        public JPanel displayListProduct(Boolean disabeldOption, ArrayList<Product> products, ProductVisual pv){
        this.pnlWhite.setLocation(50,50);
        int pnlWhiteWidth=900;
        int pnlWhiteHeight=600;
        this.pnlWhite.setSize(pnlWhiteWidth,pnlWhiteHeight);
        this.pnlWhite.setLayout(null);
        JPanel pnl = pv.displayListProduct(disabeldOption, products, pnlWhiteWidth, pnlWhiteHeight);
        pnl.setSize(pnlWhiteWidth, pnlWhiteHeight);
        this.pnlWhite.add(pnl);
        return super.display();
    }

    /**
     * displays a panel with a form for create a new product with many textfields of teh info of the new product
     * and a "create" button, if the product is created the product is saved in teh database and in the 
     * array of products
     *
     * @param pv
     * @return JPanel
     */
        public JPanel displayAddProduct(ProductVisual pv){

        this.pnlWhite.setSize(700, 400);
        this.pnlWhite.setLocation(200,100);
        JPanel pnl = pv.displayAddProduct();
        this.pnlWhite.add(pnl);
        return super.display();
    
    }

    /**
     * this function returns a JPanel with all the info of the product. A big image, the name of the product, the 
     * description, the price and the spinner and button "ad to the order". This funcion if the user clicks 
     * in the "ad to toder" button inserts the product in the order
     *
     * @param product
     * @param spnnrDefValue
     * @param pv
     * @return JPanel
     */
        public JPanel displayDescription(Product product, int spnnrDefValue, ProductVisual pv){
        this.pnlWhite.setLayout(null);
        JPanel pnl = pv.displayDescription(product, spnnrDefValue); 
        pnl.setSize(this.getPnlWhiteWidth(), this.getPnlWhiteHeight());
        this.pnlWhite.add(pnl);
        return super.display();
    }

    /**
     * displays a panel with the information of the order, all the products, the total price...
     * it displays a receipt ready for paying
     *
     * @param orderList
     * @param discount
     * @param pv
     * @return JPanel
     */
        public JPanel displayReceipt(Map<Integer, Integer> orderList, float discount, ProductVisual pv){
        
        JScrollPane scrllPane = pv.displayReceipt(orderList, discount);
        this.pnlWhite.add(scrllPane);
        
        return super.display();
    }
}
