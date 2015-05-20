/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorypck;

/**
 * Structure of the class category 
 * 
 * @author vaio
 */
public class Category {
    private int id;
    private String Name;
    private String Photo_url;
    private int Disabled;
    
    Category(){
        this.id=0;
        this.Name = "";
        this.Photo_url = "";
        this.Disabled=0;
    }
        
    Category(int id, String name){
        this.id=id;
        this.Name = name;
        this.Photo_url = "img/imagepackage/various/no_photo_small.png";
        this.Disabled=0;
    }
    
    Category(int id, String name, String photo_url, int disabled){
        this.id=id;
        this.Name = name;
        this.Photo_url = (photo_url.equals(""))?"img/imagepackage/various/no_photo_small.png":photo_url;
        this.Disabled=disabled;
    }
        
    /**
     * Get the id of the category
     *
     * @return int
     */
    public int getId(){
        return this.id;
    } 
    
    /**
     * Get the name of teh category
     *
     * @return String
     */
    public String getName(){
        return this.Name;
    } 
        
    /**
     * get the path of the photo of the category
     *
     * @return string
     */
    public String getPhotoPath(){
        return this.Photo_url;
    }
       
    /**
     * get the value of teh variable disabled of the category
     *
     * @return int
     */
    public int getDisabled(){
        return this.Disabled;
    }   
           
    /**
     * Return the estate of teh catgeory if is disabled or not
     *
     * @return boolean
     */
    public Boolean isDisabled(){
        return this.Disabled == 1;
    }  
        
    /**
     * set the name of the category
     *
     * @param name
     */
    public void setName(String name){
        this.Name =name;
    } 
        
    /**
     * set the photo path of the category
     *
     * @param photoUrl
     */
    public void setPhotoUrl(String photoUrl){
        this.Photo_url = photoUrl;
    }
            
    /**
     * set the variable disabled of the category
     *
     * @param disabled
     */
    public void setDisabled(int disabled){
        this.Disabled = disabled;
    }   
    
    /**
     * returns if the category is an empty category
     *
     * @return boolean
     */
    public Boolean isEmpty(){
        return this.id == 0 && this.Name.equals("") && this.Photo_url.equals("");
    }
}
