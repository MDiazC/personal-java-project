/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderpck;

import internalframepck.IFOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import productpck.Product;

/**
 *
 * @author vaio
 */
public class OrderActions {
    
    Map<Integer, Integer> order = null;
    OrderVisual ov = null;
    private static OrderActions singleton = new OrderActions( );
    
    /**
     * default constructor
     */
    public void OrderActions(){
        this.order = new HashMap<Integer, Integer>();
        this.ov = new OrderVisual();
    }
    
    /* Static 'instance' method */

    /**
     * retuns an instance of the class
     *
     * @return OrderActions
     */
   public static OrderActions getInstance( ) {
     return singleton;
   }
    
    /**
     * return the order panel
     *
     * @return JPanel
     */
    public JPanel getOrderPnl(){
        return this.ov.displayOrderPanel();
    }

    /**
     * this function adds to the internal map of products of the order the new product added
     *
     * @param productId
     * @param quantity
     */
        public void addToOrder(int productId, int quantity){
        if(quantity > 0){
            if(!this.order.isEmpty()){
                if(!this.order.containsKey(productId)) {
                    try{
                        this.order.put(productId,quantity);
                    }catch(Exception e){
                        System.out.println("No se puede hacer put en map no vacio y sin ese key");
                    }
                } else {
                    try{
                        this.order.put(productId,this.order.get(productId)+quantity);
                    }catch(Exception e){
                        System.out.println("No se puede hacer put en map no vacio y con ese key");
                    }
                }
            }
            else{
                try{
                    this.order.put(productId,quantity);
                }catch(Exception e){
                    System.out.println("No se puede hacer put en map vacio");
                }
            }
        }
    }

    /**
     * this functions updates the main table of the order that is in the main screen with 
     * the current info of the order
     *
     * @param products
     * @param discount
     */
    public void updatePnlOrder(ArrayList<Product> products, float discount){
        
        Integer elemId, elemQuantity;
        DefaultTableModel tabla = new DefaultTableModel(new String[]{"Borrar", "id", "Nombre del Producto", "Dto", "Cant", "Precio", "Subtotal"}, 0);
        float price=0;
        if(!products.isEmpty() && this.order != null){
            Iterator iteratorOrder = this.order.entrySet().iterator();
            while (iteratorOrder.hasNext()) {
                Map.Entry orderElem = (Map.Entry) iteratorOrder.next();
                try{
                    elemId = (Integer) orderElem.getKey();
                }catch(Exception e){
                    System.out.println("Error al sacar la key del map de ordenes");
                    break;
                }
                try{
                    elemQuantity = (Integer) orderElem.getValue();
                }catch(Exception e){
                    System.out.println("Error al sacar la catidad del map de ordenes");
                    break;
                }
                price = price + insertInTable(products, elemId, elemQuantity, discount, tabla);
            }
        }
        this.ov.setTableModel(tabla);
        this.ov.hideColumnId();
        this.ov.adjustTableWidth(this.ov.getTableOrder());
        this.ov.updatePrice(price);
    }

    //
    /**
     * this funtcion inserts in the table of orders the product of the map of products that are in the order
     *
     * @param products
     * @param elemId
     * @param elemQuantity
     * @param discount 
     * @param tabla
     */
    private float insertInTable(ArrayList<Product> products, Integer elemId, Integer elemQuantity, float discount, DefaultTableModel tabla) {
        Product product;
        Vector filaTabla;
        ListIterator iteratorProducts = products.listIterator();
        float price=0;
        while(iteratorProducts.hasNext()){
            product = (Product) iteratorProducts.next();
            if(!product.isEmpty() && product.getId() == elemId){
                price = ((100-discount)*product.getPrice()*elemQuantity)/100;
                price = (float) (Math.round(price*100.0)/100.0);
                filaTabla = new Vector();
                filaTabla.add("Borrar");
                filaTabla.add("" +product.getId());
                filaTabla.add(product.getName());
                filaTabla.add(discount);
                filaTabla.add(elemQuantity);
                filaTabla.add(product.getPrice());
                filaTabla.add(price);
                tabla.addRow(filaTabla);
                break;
            }
        }
        return price;
    }

    /**
     * this function remove a product (if exists) form the current maps of product of the current order
     *
     * @param productId
     */
        public void removeToOrder(int productId){
        if(productId > 0){
            Iterator<Map.Entry<Integer, Integer>> it = this.order.entrySet().iterator();
            Map.Entry<Integer, Integer> entry;
            while(it.hasNext()) {
                entry = it.next();
                if( entry.getKey() == productId ) {
                    it.remove();
                }
            }
        }
    }

    /**
     * returns the current map of the order
     *
     * @return
     */
    public Map<Integer, Integer> getOrderList() {
        return this.order;
    }

    /**
     * empty the maps of the current order
     */
    public void emptyOrderList() {
        this.order = new HashMap<Integer, Integer>();
    }

    /**
     * inserts an order in the database
     *
     * @param userId
     */
    public void insertOrder(int userId) {
        if(!this.order.isEmpty()){
            ServiceOrder odb = new ServiceOrder();
            odb.insertOrder(userId, this.order);
        }
    }

    /**
     * when the user user wants list all the products or list all the products of a day the 
     * flow comes here and returns the panel of one of the options selected
     *
     * @param option
     * @return JPanel
     */
        public JPanel getOrderPanel(int option){
        JPanel pnl = null;
        IFOrder ifo = new IFOrder();
              
        ServiceOrder odb = new ServiceOrder();
        ArrayList<Order> orderList=null;
        switch(option){
            case 5: 
                orderList = odb.getAllOrders();
                pnl = ifo.removeOrder(orderList, this.ov);
                break;
            case 6: 
                pnl = ifo.listOrderByDay(this.ov);
                break;
        }
        return pnl; 
    }

    /**
     * removes an order from the database
     *
     * @param orderId
     */
        public void removeOrder(int orderId){
        if(orderId > 0){
            ServiceOrder so = new ServiceOrder();
            so.removeOrder(orderId);
        }
    }

    /**
     * this functions loads all the orders of a day and display them in the table
     *
     * @param date
     */
        public void updateTableOrdersDay(String date){
        ServiceOrder so = new ServiceOrder();
        ArrayList<Order> orderList = so.getOrdersByDate(date);
        this.ov.updateTableOrdersDay(orderList);
    }
}
