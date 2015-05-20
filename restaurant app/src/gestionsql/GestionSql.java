/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionsql;
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
public class GestionSql {

    /**
     * @param args the command line arguments
     */
    static String DRIVER_JDBC;
    static String DB_URL;
    static String USER;
    static String PASS;
    private  Connection conn;
    
    /**
     * Inicialize the parameters of the class
     */
    public GestionSql(){
        this.conn=null;
        this.DRIVER_JDBC = "com.mysql.jdbc.Driver";
        this.DB_URL = "jdbc:mysql://localhost:3306/Java";
        this.USER = "vaio";
        this.PASS = "vaio";
    }
    
    /**
     * Open an sql connection
     * @throws SQLException
     */
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
    
    /**
     * Close the sql connection
     * @throws SQLException
     */
    public void closeConnection()  throws SQLException{      
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    /**
     * returns the connection
     * 
     * @return Connection 
     */
    public Connection getConn(){
        return this.conn;
    }
    
    /**
     * checks if we have a connection opened
     * 
     * @return boolean
     */
    public Boolean hasConn(){
        return this.conn != null;
    }

    //executes a SELECT query and returns the resultset

    /**
     *
     * @param sql
     * @return
     */
        public ResultSet executeSQL(String sql){
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

    /**
     *
     * @param query
     * @return
     */
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

