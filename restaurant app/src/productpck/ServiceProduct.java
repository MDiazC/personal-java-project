/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productpck;

import gestionsql.GestionSql;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author vaio
 */
public class ServiceProduct {

    /**
     * get all the products from teh database
     *
     * @return ArrayList
     */
    public ArrayList<Product> getProducts(){
        ResultSet results = null;
        GestionSql gdb = new GestionSql();
        ArrayList<Product> products = new ArrayList<Product>();
        Product product = null;
        try {
            gdb.openConnection();
            String sql = "SELECT * FROM Product WHERE 1";
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    product = new Product(results.getInt("id"), results.getInt("Category_id"), 
                    results.getString("Name"), results.getString("Photo_path"), results.getString("Description")
                    , results.getFloat("Price"), results.getInt("Disabled"));
                    products.add(product);
                }
            }catch(Exception e){
                System.out.println("Error al pasar los producstos al array list");
                return null;
            }
            
            gdb.closeConnection();
        }  catch (Exception e) {
            System.out.println("Error al conectarse a la base de datos");
            e.printStackTrace();
        }
        return products;
    }

    /**
     * adds a new product to the database
     *
     * @param category_id
     * @param name
     * @param description
     * @param price
     * @return int
     */
    public int addProduct(int category_id, String name, String description, float price ){
        ResultSet result;
        int last_inserted_id=0;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[5];
            sql[0]="INSERT INTO `Product` (Category_id, Name, Description, Price) VALUES (?,?,?,?)";
            sql[1]= Integer.toString(category_id);
            sql[2]= name;
            sql[3]= description;
            sql[4]= Float.toString(price);
            result = gdb.executeUpdate(sql);
            
            if(result.next()){
                    last_inserted_id = result.getInt(1);
            }
            gdb.closeConnection();
            return last_inserted_id;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return last_inserted_id;
    }

    /**
     * disables a product in the database
     *
     * @param productId
     * @param disabled
     */
    public void disableProduct(int productId, int disabled ){
        ResultSet result;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[3];
            sql[0]="UPDATE `Product` SET Disabled = ? WHERE id = ?;";
            sql[1]= Integer.toString( disabled);
            sql[2]= Integer.toString( productId);
            result = gdb.executeUpdate(sql);
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * removes a product from the database
     *
     * @param productId
     */
    public void deleteProduct(int productId){
        ResultSet result;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[2];
            sql[0]="DELETE FROM `Product` WHERE id = ?;";
            sql[1]= Integer.toString( productId);
            result = gdb.executeUpdate(sql);
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
