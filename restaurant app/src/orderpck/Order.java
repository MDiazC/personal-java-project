/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderpck;

import java.util.HashMap;

/**
 *
 * @author vaio
 */
public class Order {
    private int id;
    private int User_id;
    private HashMap<Integer, Integer> Order_info;
    private String Timestamp;
    
    Order(int id, int user_id, HashMap<Integer, Integer> order, String timestamp){
        this.id=id;
        this.User_id = user_id;
        this.Order_info = order;
        this.Timestamp = timestamp;
    }
    
    /**
     * return the id of the order
     *
     * @return int
     */
    public int getId(){
        return this.id;
    }
        
    /**
     * return the userid of the order
     *
     * @return int
     */
    public int getUserId(){
        return this.User_id;
    }
        
    /**
     * return the order
     *
     * @return HashMap<Integer, Integer>
     */
    public HashMap<Integer, Integer> getOrder(){
        return this.Order_info;
    }
        
    /**
     * return the timestamp of the order
     *
     * @return String
     */
    public String getTimestamp(){
        return this.Timestamp;
    }
        
    /**
     * set the id of the user of the order
     *
     * @param user_id
     */
    public void setUserId(int user_id){
        this.User_id = user_id;
    }
        
    /**
     * set the order
     *
     * @param order
     */
    public void setOrder(HashMap<Integer, Integer> order){
        this.Order_info = order;
    }

}
