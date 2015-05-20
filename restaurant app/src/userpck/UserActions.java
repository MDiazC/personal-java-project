/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpck;

import internalframepck.IFUser;
import javax.swing.JPanel;
import mainpck.MainActions;

/**
 *
 * @author vaio
 */
public class UserActions {
    
    User user = null;
    UserVisual uv = null;
    private static UserActions singleton = new UserActions();
    
    /**
     * default constructor
     */
    public UserActions(){
        this.user = new User();
        this.uv = new UserVisual();
    }
    
       /* Static 'instance' method */

    /**
     * retuns an instance of the class
     *
     * @return UserActions
     */
   public static UserActions getInstance( ) {
     return singleton;
   }

    /**
     * return the username of the logged user
     *
     * @return String
     */
    public String getUsername(){
        return this.user.getName() +" "+this.user.getSurname(); 
    }

    /**
     * when the user clicks on any option of teh top bar menu, the function returns a different panel
     *
     * @param option
     * @return JPanel
     */
    public JPanel getUserPanel(int option){
        JPanel pnl = null;
        IFUser ifu = new IFUser();
        switch(option){
            case 1: 
                pnl = ifu.displayLoginUser(this.uv);break;
            case 2: 
                pnl = ifu.displayRegisterUser(this.uv);break;
            case 3: 
                pnl = ifu.displayHistoryPnl(this.getUserId(), this.getUsername(), this.uv);break;
            case 4:
                pnl = ifu.displayListUserPnl(this.uv);break;
        }
        return pnl; 
    }

    /**
     * return the logged user id
     *
     * @return int
     */
    public int getUserId(){
        return this.user.getUserId();
    }

    /**
     * returns the logged user photo path
     *
     * @return String
     */
    public String getUserPhotoPath(){
        return this.user.getPhotoPath();
    }

    /**
     * starts the porcess of login a user, first ask the database the info of the logged user (if the email
     * and the password and correct) then saves in a local variable the info of the user 
     *
     * @param email
     * @param password
     * @return boolean
     */
    public boolean processLoginUser(String email, String password){
        ServiceUser udb = new ServiceUser();
        User userLogged = udb.getUserLogged(email, password);
        if(userLogged != null){
            this.user=userLogged;
            //this.updateFrequentOrder();
            MainActions ma = MainActions.getInstance();
            ma.updateMenuBar(this.user.getName()+" "+this.user.getSurname(), this.user.getPhotoPath());
            ma.removeInternalFrame();
        }
        return userLogged!=null;
    }

    /**
     * starts the process of register a new user, creates in the database a new register of a new user 
     * and then save in a local variabel all teh info of the user created
     *
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param idNumber
     * @return boolean
     */
    public boolean processRegisterUser(String email, String password, String name, String surname, String idNumber){
        ServiceUser udb = new ServiceUser();
        User user = udb.insertUser(name, surname, email, password, idNumber);
        if(user != null){
            this.user=user;
            MainActions ma = MainActions.getInstance();
            ma.updateMenuBar(user.getName()+" "+user.getSurname(), user.getPhotoPath());
            ma.removeInternalFrame();
        }
        return user!=null;
    }
    
    /**
     * logout the logged user
     */
    public void logOutUser(){
        this.user=new User();
    }
    
    /**
     * checks if teh logged user is the superuser
     *
     * @return
     */
    public boolean isSuperUser(){
        return this.user.isSuperUser();
    }
    
    /**
     * checks if there is a logged user of teh current logged user is teh default
     *
     * @return
     */
    public boolean isLoggedUser(){
        return this.user.isPublicUser();
    }
        
    /**
     * get the disocunt of the logged user
     *
     * @return
     */
    public float getUserDiscount(){
        return this.user.getDiscount();
    }
     
    /*
    public void updateFrequentOrder(){
        if(this.user.getFrequentOrder() == null && this.user.isPublicUser()){
            ServiceOrder odb = new ServiceOrder();
            HashMap<Integer, Integer> history = odb.getFrequentOrderByUser(this.user.getUserId());
            this.user.setFrequentOrder(history);
        }
    }*/

    /**
     * removes a iser from the database
     *
     * @param user_id
     */
    public void removeUser(int user_id){
        if(user_id > 0){
            ServiceUser su = new ServiceUser();
            su.deleteUser(user_id);
        }
    }
}
