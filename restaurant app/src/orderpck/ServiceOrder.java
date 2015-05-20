/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderpck;

import gestionsql.GestionSql;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vaio
 */
public class ServiceOrder {

    /**
     * return al arrayList with all the orders of a user
     *
     * @param userId
     * @return
     */
    public ArrayList<Order> getUserOrders(int userId){
        ResultSet results = null;
        GestionSql gdb = new GestionSql();
        ArrayList<Order> orderList = new ArrayList<Order>();
        Order order = null;
        HashMap<Integer, Integer> order_info;
        try {
            gdb.openConnection();
            String sql = "SELECT id,User_id,Order_info,Timestamp FROM `Order` WHERE User_id="+userId;
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    order_info=this.orderToMap(results.getString("Order_info"));
                    order = new Order(results.getInt("id"), results.getInt("User_id"), order_info, results.getString("Timestamp"));
                    orderList.add(order);
                }
            }catch(Exception e){
                System.out.println("Erro al extraer las categorias de la base de datoa al arrayList");
                return null;
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            System.out.println("Error al conectarse a la base de datos");
            e.printStackTrace();
        }
        return orderList;
    }

    /**
     * return al arrayList with all the orders of the database
     *
     * @return ArrayList
     */
    public ArrayList<Order> getAllOrders(){
        ResultSet results = null;
        GestionSql gdb = new GestionSql();
        ArrayList<Order> orderList = new ArrayList<Order>();
        Order order = null;
        HashMap<Integer, Integer> order_info;
        try {
            gdb.openConnection();
            String sql = "SELECT id,User_id,Order_info,Timestamp FROM `Order` WHERE 1";
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    order_info=this.orderToMap(results.getString("Order_info"));
                    order = new Order(results.getInt("id"), results.getInt("User_id"), order_info, results.getString("Timestamp"));
                    orderList.add(order);
                }
            }catch(Exception e){
                System.out.println("Erro al extraer las categorias de la base de datoa al arrayList");
                return null;
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            System.out.println("Error al conectarse a la base de datos");
            e.printStackTrace();
        }
        return orderList;
    }

    /**
     * inserts a new order in the database
     *
     * @param userId
     * @param order
     */
    public void insertOrder(int userId, Map<Integer, Integer> order){
        ResultSet result;
        int last_inserted_id=0;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[3];
            sql[0]="INSERT INTO `Order` (User_id, Order_info) VALUES (?,?)";
            sql[1]= ""+userId;
            sql[2]= this.orderToString(order);
            result = gdb.executeUpdate(sql);
            
            if(result.next()){
                    last_inserted_id = result.getInt(1);
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if(last_inserted_id <= 0)
            System.out.println("Error al insertar la orden");
    }
        
    /**
     * removes an order from the database
     *
     * @param orderId
     */
    public void removeOrder(int orderId){
        ResultSet result;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[2];
            sql[0]="DELETE FROM `Order` WHERE id = ?";
            sql[1]= ""+orderId;
            result = gdb.executeUpdate(sql);
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * returns all the orders of a day
     *
     * @param date
     * @return  ArrayList
     */
    public ArrayList<Order> getOrdersByDate(String date){
        ResultSet results = null;
        GestionSql gdb = new GestionSql();
        ArrayList<Order> orderList = new ArrayList<Order>();
        Order order = null;
        HashMap<Integer, Integer> order_info;
        try {
            gdb.openConnection();
            String sql = "SELECT id,User_id,Order_info,Timestamp FROM `Order` WHERE `Timestamp` LIKE '"+date+"%'";
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    order_info=this.orderToMap(results.getString("Order_info"));
                    order = new Order(results.getInt("id"), results.getInt("User_id"), order_info, results.getString("Timestamp"));
                    orderList.add(order);
                }
            }catch(Exception e){
                System.out.println("Erro al extraer las categorias de la base de datoa al arrayList");
                return null;
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            System.out.println("Error al conectarse a la base de datos");
            e.printStackTrace();
        }
        return orderList;
    }
    
    /*
    public HashMap<Integer, Integer> getFrequentOrderByUser(int userId){
        ResultSet results = null;
        gestionDB gdb = new gestionDB();
        Order order = null;
        HashMap<Integer, Integer> order_info = null;
        try {
            gdb.openConnection();
            String sql = "SELECT Order_info, COUNT(*) AS amount FROM `Order` WHERE User_id = "+userId+" GROUP BY Order_info ORDER BY amount DESC LIMIT 0,1";
            results = gdb.executeQuery(sql);
            
            try{
                if (results.next()) {
                    order_info=this.orderToMap(results.getString("Order_info"));
                }
            }catch(Exception e){
                System.out.println("Erro al extraer las categorias de la base de datoa al arrayList");
                return null;
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return order_info;
    }*/
    
    /**
     * converts an order into a string
     *
     * @param order
     * @return  String
     */
    private String orderToString(Map<Integer, Integer> order){
        return order.toString();
    }
    
    /**
     * transform the strign of an order in a HashMap
     *
     * @param order
     * @return  HashMap<Integer, Integer> 
     */
    private HashMap<Integer, Integer> orderToMap(String order){
        order = order.substring(order.indexOf('{')+1); //remove first character {
        order = order.substring(0,order.lastIndexOf('}')); // remvoe lastCharacter }

        String[] orders = order.split(",");
        String[] line;
        int key, value;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<=orders.length-1; i++) {
            line = orders[i].split("=");
            key = Integer.parseInt(line[0].trim());
            value = Integer.parseInt(line[1].trim());
            map.put(key, value);
        }
        return map;
    }
    
}
