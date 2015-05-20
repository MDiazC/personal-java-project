/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productpck;

/**
 *
 * @author vaio
 */
public class Product {
    private int id;
    private int Category_id;
    private String Name;
    private String Photo_path;
    private String Description;
    private float Price;
    private int Disabled;
    
    Product(){
        this.id=0;
        this.Category_id=0;
        this.Name="";
        this.Photo_path="";
        this.Description="";
        this.Price=0;
        this.Disabled=0;
    }
        
    Product(int id, int category_id, String name, String description, float price){
        this.id=id;
        this.Category_id=category_id;
        this.Name=name;
        this.Photo_path= "img/imagepackage/various/no_photo_small.png";
        this.Description=description;
        this.Price=price;
        this.Disabled=0;
    }
    
    Product(int id, int category_id, String name, String photo_path, String description, float price, int disabled){
        this.id=id;
        this.Category_id=category_id;
        this.Name=name;
        this.Photo_path= (photo_path.equals(""))?"img/imagepackage/various/no_photo_small.png":photo_path;
        this.Description=description;
        this.Price=price;
        this.Disabled=disabled;
    }
        
    /**
     * returns the id of the product
     *
     * @return int
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * returns the category id of the product
     *
     * @return int
     */
    public int getCategoryId(){
        return this.Category_id;
    }
            
    /**
     * returns the name of the product
     *
     * @return String
     */
    public String getName(){
        return this.Name;
    }
                
    /**
     * returns the photo path of the product
     *
     * @return string
     */
    public String getPhotoPath(){
        return this.Photo_path;
    }
    
    /**
     * returns the description of the product
     *
     * @return string
     */
    public String getDescription(){
        return this.Description;
    }
    
    /**
     * returns the price of the product
     *
     * @return float
     */
    public float getPrice(){
        return this.Price;
    }
        
    /**
     * returns the vairbale disabled of the product
     *
     * @return int
     */
    public int getDisabled(){
        return this.Disabled;
    }
            
    /**
     * returns if a product is disbaled
     *
     * @return boolean
     */
    public Boolean isDisabled(){
        return this.Disabled == 1;
    }
    
    /**
     * set the category id of a product
     *
     * @param category
     */
    public void setCategoryId(int category){
        this.Category_id=category;
    }
            
    /**
     * set the name of a product
     *
     * @param name
     */
    public void setName(String name){
        this.Name=name;
    }
                
    /**
     * set the photo path of a product
     *
     * @param photo_path
     */
    public void setPhotoPath(String photo_path){
        this.Photo_path=photo_path;
    }
    
    /**
     * set the description of a product
     *
     * @param description
     */
    public void setDescription(String description){
        this.Description=description;
    }
    
    /**
     * set the price of a product
     *
     * @param price
     */
    public void setPrice(float price){
        this.Price=price;
    }
    
    /**
     * set the variable disabled of a product
     *
     * @param disable
     */
    public void setDisabled(int disable){
        this.Disabled=disable;
    }
    
    /**
     * returns if an product is a default product (hasn't created correctly)
     *
     * @return boolean
     */
    public Boolean isEmpty(){
        return this.id==0 && this.Category_id ==0 && this.Name.equals("") && this.Photo_path.equals("") && this.Description.equals("");
    }
       
}

