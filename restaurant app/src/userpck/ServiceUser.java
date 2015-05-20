/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpck;

import gestionsql.GestionSql;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vaio
 */
public class ServiceUser {
    
    /**
     * get all the info of the user wants to log
     *
     * @param email
     * @param password
     * @return User
     */
    public User getUserLogged(String email, String password){
        ResultSet results = null;
        GestionSql gdb = new GestionSql();
        User user = null;
        try {
            gdb.openConnection();
            String sql = "SELECT id, Name, Surname, ID_number, Discount, Photo_path FROM User WHERE Email='"+email+"' AND Password='"+password+"';";
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    user = new User(results.getInt("id"), results.getString("Name"), results.getString("Surname"), email, password, results.getString("ID_number"), results.getFloat("Discount"), results.getString("Photo_path"));
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
        return user;
    }

    /**
     * insert a new user in teh database
     *
     * @param name
     * @param surname
     * @param email
     * @param password
     * @param id_number
     * @return User
     */
    public User insertUser(String name, String surname, String email, String password, String id_number){
        ResultSet result;
        User user = null;
        int last_inserted_id=0;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[6];
            sql[0]="INSERT INTO `User` (Name, Surname, Email, Password, ID_number) VALUES (?,?,?,?,?)";
            sql[1]= name;
            sql[2]= surname;
            sql[3]= email;
            sql[4]= password;
            sql[5]= id_number;
            result = gdb.executeUpdate(sql);
            
            if(result.next()){
                    last_inserted_id = result.getInt(1);
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if(last_inserted_id > 100){
            user = new User(last_inserted_id, name, surname, email, password, id_number, 0);
        }
        return user;
    }

    /**
     * returns all the users that exists in the database
     *
     * @return ArrayList
     */
    public ArrayList<User> getAllUsers(){
        ResultSet results;
        GestionSql gdb = new GestionSql();
        ArrayList<User> userList = new ArrayList<User>();
        User user;
        HashMap<Integer, Integer> order_info;
        try {
            gdb.openConnection();
            String sql = "SELECT id, Name, Surname, Email, Password, ID_number, Photo_path, Discount FROM `User` WHERE id > 4";
            results = gdb.executeSQL(sql);
            
            try{
                while (results.next()) {
                    user = new User(results.getInt("id"), results.getString("Name"), results.getString("Surname"), results.getString("Email"), results.getString("Password"), results.getString("ID_number"), results.getFloat("Discount"), results.getString("Photo_path"));
                    userList.add(user);
                }
            }catch(Exception e){
                System.out.println("Erro al extraer las todos los usuarios de la base de datoa al arrayList");
                return null;
            }
            gdb.closeConnection();
        }  catch (Exception e) {
            System.out.println("Error al conectarse a la base de datos");
            e.printStackTrace();
        }
        return userList;
    }

    //

    /**
     * remvoes a user from the database depending on the vale given
     *
     * @param userId
     */
    public void deleteUser(int userId){
        ResultSet result;
        GestionSql gdb = new GestionSql();
        try {
            gdb.openConnection();
            String[] sql = new String[2];
            sql[0]="DELETE FROM `User` WHERE id = ?";
            sql[1]= ""+userId;
            result = gdb.executeUpdate(sql);
            
            gdb.closeConnection();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
