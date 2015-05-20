/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author moi
 */
public class gestionDB {

    /**
     * @param args the command line arguments
     */
    static final String DRIVER_JDBC = "com.mysql.jdbc.Driver";
    static final String DB_URL =
    "jdbc:mysql://localhost:3306/Java";
    static final String USER = "vaio";
    static final String PASS = "vaio";
    
    private  Connection conn=null;
    
    public gestionDB(){
    }
    
    public void openConnection()  throws SQLException{       
        try {
            Class.forName(DRIVER_JDBC);
            System.out.println("Conectando con la BBDD...");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexión extablecida OK...");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection()  throws SQLException{      
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    public Connection getConn(){
        return this.conn;
    }
    
    public Boolean hasConn(){
        return this.conn != null;
    }

    //executes a SELECT query and returns the resultset
    public ResultSet executeQuery(String sql){
        if(this.hasConn()){
            try{
                Statement stmt = this.getConn().createStatement();
                ResultSet rs = stmt.executeQuery(sql);   
                return rs;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    
    //execute an insert, update or remove mysql action and returns the generated ky (just in insert case)
    public ResultSet executeUpdate(String[] query){
        if(this.hasConn()){
            try{
                if(query.length > 1){
                    PreparedStatement ps = conn.prepareStatement(query[0]);
                    int i =1;
                    while(i < query.length){
                        ps.setString(i, query[i]);
                        i++;
                    }
                    ps.executeUpdate();
                    return ps.getGeneratedKeys();   
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}

