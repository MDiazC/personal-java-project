/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpck;

import java.util.HashMap;

/**
 *
 * @author vaio
 */
public class User {
    
    private int id;
    private String Name;
    private String Surname;
    private String Email;
    private String Password;
    private String ID_number;
    private String Photo_path;
    private float Discount;
    //private HashMap<Integer, Integer> frequentOrder;
    
    /**
     * default constructior for the class user
     */
    public User(){
        this.id=2;
        this.Name="Usuario";
        this.Surname="anónimo";
        this.Email="defaultuser@healthyburgervalencia.com";
        this.Password="usuario";
        this.ID_number="00000000q";
        this.Photo_path="img/imagepackage/various/no_user.png";
        this.Discount=0;
        //this.frequentOrder=null;
    }
    
    /**
     * parameter constructor for user
     *
     * @param id
     * @param name
     * @param surname
     * @param email
     * @param password
     * @param id_number
     * @param discount
     */
    public User(int id, String name, String surname, String email, String password, String id_number, float discount){
        this.id=id;
        this.Surname=surname;
        this.Name=name;
        this.Email= email;
        this.Password=password;
        this.ID_number=id_number;
        this.Discount = discount;
        //this.frequentOrder=null;
        this.Photo_path="img/imagepackage/various/no_user.jpg";
    }
    
    /**
     * parameter constructor for user
     * @param id
     * @param name
     * @param surname
     * @param email
     * @param password
     * @param id_number
     * @param discount
     * @param photo_path
     */
    public User(int id, String name, String surname, String email, String password, String id_number, float discount, String photo_path){
        this.id=id;
        this.Surname=surname;
        this.Name=name;
        this.Email= email;
        this.Password=password;
        this.ID_number=id_number;
        this.Discount = discount;
        //this.frequentOrder=null;
        this.Photo_path=photo_path;
    }
    
    /**
     * get the id of the user
     *
     * @return int
     */
    public int getUserId(){
        return this.id;
    }
            
    /**
     * get the name of the user
     *
     * @return String
     */
    public String getName(){
        return this.Name;
    }
                
    /**
     * get the surname of the user
     *
     * @return String
     */
    public String getSurname(){
        return this.Surname;
    }

    /**
     * get the email of the user
     *
     * @return String
     */
    public String getEmail(){
        return this.Email;
    }

    /**
     * get the password of the user
     *
     * @return String
     */
    public String getPassword(){
        return this.Password;
    }  

    /**
     * get the idNumber of the user
     *
     * @return String
     */
    public String getIdNumber(){
        return this.ID_number;
    }
        
    /**
     * get the photoPath of the user
     *
     * @return String
     */
    public String getPhotoPath(){
        return this.Photo_path;
    }
            
    /**
     * get the discount of the user
     *
     * @return float
     */
    public float getDiscount(){
        return this.Discount;
    }
            
    /*public HashMap<Integer, Integer> getFrequentOrder(){
        return this.frequentOrder;
    }*/

    /**
     * set the surname of ther user
     *
     * @param surname
     */
    public void setSurname(String surname){
        this.Surname=surname;
    }

    /**
     * set the name of ther user
     *
     * @param name
     */
    public void setName(String name){
        this.Name=name;
    }

    /**
     * set the email of ther user
     *
     * @param email
     */
    public void setEmail(String email){
        this.Email=email;
    }

    /**
     * set the password of ther user
     *
     * @param password
     */
    public void setPassword(String password){
        this.Password=password;
    }

    /**
     * set the id number of ther user
     *
     * @param id_number
     */
    public void setIdNumber(String id_number){
        this.ID_number=id_number;
    }

    /**
     * set the photo path of ther user
     *
     * @param photo_path
     */
        public void setPhotoPath(String photo_path){
        this.Photo_path=photo_path;
    }
        
    /*public void setFrequentOrder(HashMap<Integer, Integer> frequentOrder){
        this.frequentOrder=frequentOrder;
    }*/

    /**
     * return if the logged user is the default user (the not logged user)
     *
     * @return boolean
     */
     public Boolean isDefaultUser(){
        return this.id == 2;
    }
        
    /**
     * return if the logged user is the super user
     *
     * @return boolean
     */
    public Boolean isSuperUser(){
        return this.id==1;
    }
        
    /**
     * return if the logged user is a current user
     *
     * @return boolean
     */
    public Boolean isPublicUser(){
        return this.id > 2;
    }
}
