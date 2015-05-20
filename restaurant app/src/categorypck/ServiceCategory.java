/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorypck;

import gestionsql.GestionSql;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author vaio
 */
public class ServiceCategory {

    /**
     * access to the database and creates an arraylist of categories with the different categories loaded
     *
     * @return ArrayList
     */
        public ArrayList<Category> getCategories(){
        ResultSet results = null;
        GestionSql gdb = new GestionSql();
        ArrayList<Category> categories = new ArrayList<Category>();
        Category category = null;
        try {
            gdb.openConnection();
            String sql = "SELECT id,Name,Photo_path, Disabled FROM Category WHERE 1";
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    category = new Category(results.getInt("id"), results.getString("Name"), results.getString("Photo_path"), results.getInt("Disabled"));
                    categories.add(category);
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
        return categories;
    }

    /**
     * adds a category to the database and returns the id of the new category created
     *
     * @param name
     * @return int
     */
        public int addCategory(String name){
        ResultSet result;
        int last_inserted_id=0;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[2];
            sql[0]="INSERT INTO `Category` (Name) VALUES (?)";
            sql[1]= name;
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
     * change the value of the field disabled of a category
     *
     * @param categoryId
     * @param disabledOption
     */
        public void disableCategory(int categoryId, int disabledOption ){
        ResultSet result;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[3];
            sql[0]="UPDATE `Category` SET Disabled = ? WHERE id = ?;";
            sql[1]= Integer.toString( disabledOption);
            sql[2]= Integer.toString( categoryId);
            result = gdb.executeUpdate(sql);
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * removes a category from the database
     *
     * @param categoryId
     */
        public void deleteCategory(int categoryId){
        ResultSet result;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[2];
            sql[0]="DELETE FROM `Category` WHERE id = ?;";
            sql[1]= Integer.toString( categoryId);
            result = gdb.executeUpdate(sql);
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
