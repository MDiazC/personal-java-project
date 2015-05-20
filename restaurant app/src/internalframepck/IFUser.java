/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframepck;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import orderpck.Order;
import orderpck.ServiceOrder;
import userpck.ServiceUser;
import userpck.User;
import userpck.UserVisual;

/**
 * Actions of user that are displayed in the internal frame (orders by date and remove order)
 * 
 * @author vaio
 */
public class IFUser extends InternalFrame{
        int pnlWhiteWidth=0, pnlWhiteHeight=0;

    /**
     * displays a panel with the historical of the orders of a selected user
     *
     * @param userId
     * @param username
     * @param uv
     * @return JPanel
     */
        public JPanel displayHistoryPnl(int userId, String username, UserVisual uv){
        this.pnlWhiteWidth=800;
        this.pnlWhiteHeight=400;
        this.pnlWhite.setSize(this.pnlWhiteWidth,this.pnlWhiteHeight);
        this.pnlWhite.setLocation(100,100);
        this.pnlWhite.setLayout(null);

        ServiceOrder odb = new ServiceOrder();
        ArrayList<Order> history = odb.getUserOrders(userId);
        JPanel pnl = uv.displayHistoryPnl(history, username, pnlWhiteWidth, pnlWhiteHeight);

         this.pnlWhite.add(pnl);
        return super.display();
    }

    /**
     * dsplays a panel of all the users of the database, with a button that enables the option of delete this user
     *
     * @param uv
     * @return JPanel
     */
        public JPanel displayListUserPnl(UserVisual uv){
        ServiceUser su = new ServiceUser();
        ArrayList<User> userList = su.getAllUsers();
        
        this.pnlWhiteWidth=800;
        this.pnlWhiteHeight=400;
        this.pnlWhite.setSize(this.pnlWhiteWidth,this.pnlWhiteHeight);
        this.pnlWhite.setLocation(100,100);
        this.pnlWhite.setLayout(null);

        JPanel pnl = uv.displayUserListPnl(userList, pnlWhiteWidth, pnlWhiteHeight);
         this.pnlWhite.add(pnl);

        return super.display();
    }
        
    /**
     * displays a panel with the form of login a user
     *
     * @param uv
     * @return JPanel
     */
        public JPanel displayLoginUser(UserVisual uv){

        this.pnlWhite.setSize(400,300);
        this.pnlWhite.setLocation(400,150);
        JPanel pnl = uv.displayLoginUser();
        this.pnlWhite.add(pnl);
        return super.display();
    
    }
        
    /**
     * displays a panel with the form of register a new user
     *
     * @param uv
     * @return JPanel
     */
        public JPanel displayRegisterUser(UserVisual uv){
        
        this.pnlWhite.setSize(500,400);
        this.pnlWhite.setLocation(300,100);
        this.pnlWhite.setLayout(new GridBagLayout());
        
        JPanel pnl = uv.displayRegisterUser();
        pnl.setSize(this.getPnlWhiteWidth(), this.getPnlWhiteHeight());
        this.pnlWhite.add(pnl);
 
        return super.display();
    }
}
