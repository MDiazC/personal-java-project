/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalframepck;

import java.util.ArrayList;
import javax.swing.JPanel;
import orderpck.Order;
import orderpck.OrderVisual;

/**
 * Actions of orders that are displayed in the internal frame (orders by date and remove order)
 *
 * @author vaio
 */
public class IFOrder extends InternalFrame{

    /**
     * displays a panel with all the orders of the database and a button for delete them (one by one)
     *
     * @param orderList
     * @param ov
     * @return JPanel
     */
        public JPanel removeOrder(ArrayList<Order> orderList, OrderVisual ov){
        
        int pnlWhiteWidth = 800;
        int pnlWhiteHeight = 600;
        this.pnlWhite.setSize(pnlWhiteWidth, pnlWhiteHeight);
        this.pnlWhite.setLocation(100,50);
        this.pnlWhite.setLayout(null);
        JPanel pnl = ov.displayOrdersPnl(orderList, pnlWhiteWidth, pnlWhiteHeight); 
        
        this.pnlWhite.add(pnl);
        return super.display();
    }

    /**
     * displays a panel with a title, a calendar and an empty table, when the user changes the date the table
     * refresh with the info of the orders of teh day, if the day has not an order it jumps an alert with the text
     *
     * @param ov
     * @return JPanel
     */
        public JPanel listOrderByDay(OrderVisual ov){
        
        int pnlWhiteWidth = 800;
        int pnlWhiteHeight = 600;
        this.pnlWhite.setSize(pnlWhiteWidth, pnlWhiteHeight);
        this.pnlWhite.setLocation(100,50);
        this.pnlWhite.setLayout(null);
        JPanel pnl = ov.displayOrderByDayPanel(pnlWhiteWidth, pnlWhiteHeight); 
        
        this.pnlWhite.add(pnl);
        return super.display();
    }
    
}
