/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderpck;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import mainpck.MainActions;
import productpck.Product;
import productpck.ProductActions;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author vaio
 */
public class OrderVisual {
    
    private JPanel pnlOrderSummary, pnlPrice, pnlOrder;
    private JTable tblOrder, tblAllOrders, tblDayOrders;
    private JButton btnPay;
    private JScrollPane scrllpnlTable, scrllPaneAllOrders, scrllPaneDayOrders;
    private JLabel lblPrice, lblLabelPrice;
    private JXDatePicker datePicker;
    
    /**
     * default constructor
     */
    public OrderVisual(){
        this.pnlOrder = new JPanel();
        this.pnlOrder.setBounds(10,400,990, 300);
        this.pnlOrder.setVisible(true);
        this.pnlOrder.setLayout(null);
    }
    
    // -- Start order panel main screen

    /**
     * displays the main panel of the order that consists in the table with the description of all 
     * products and the left panel of the table with the total price and the button for pay
     * this function also adds the listeners for removing os diplays the receipt
     *
     * @return JPanel
     */
        public JPanel displayOrderPanel(){
        
        this.tblOrder = new JTable(20, 8);
        this.tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},new String [] {"Borrar", "id", "Nombre del Producto", "Dto", "Cant", "Precio", "Subtotal"}));
        this.tblOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.tblOrder.getColumn("id").setMinWidth(000);
        this.tblOrder.getColumn("id").setMaxWidth(000);
        
        this.scrllpnlTable= new JScrollPane(this.tblOrder);
        this.scrllpnlTable.setBounds( 10,10,600,280 );
        this.pnlOrder.add(scrllpnlTable);
        
        this.tblOrder.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e){  
                
                MainActions ma = MainActions.getInstance();
                int row = tblOrder.rowAtPoint(e.getPoint());
                int column = tblOrder.columnAtPoint(e.getPoint());
                if(column == 0){
                    int prodcut_id = Integer.valueOf((String) tblOrder.getValueAt(row,1));
                    ma.removeProuctFromOrder(prodcut_id);
                }
            }
        });
        
        
        this.btnPay = new JButton("Realizar el pago");
        this.btnPay.setBounds(70,190,200, 50);
        this.btnPay.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    MainActions ma = MainActions.getInstance();
                    ma.showInternalFrameReceipt();
                    System.out.println("pulsado encima pagar, salta internal");
                }
        });
        
        this.lblPrice = new JLabel();
        this.lblPrice.setFont(new Font("Serif", Font.PLAIN, 32));
        
        this.lblLabelPrice = new JLabel("Total:");
        this.lblLabelPrice.setBounds(20,70,100, 50);
        this.lblLabelPrice.setFont(new Font("Serif", Font.PLAIN, 28));
        
        this.pnlPrice = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.pnlPrice.setBackground(new java.awt.Color(255, 255, 255));
        this.pnlPrice.setBounds(140,60,160, 70);
        this.pnlPrice.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        this.pnlPrice.add(this.lblPrice, gbc);
        

        this.pnlOrderSummary = new JPanel();
                
        this.pnlOrderSummary.setBackground(new java.awt.Color(128, 184, 116));
        this.pnlOrderSummary.setLayout(null);
        
        this.pnlOrderSummary.add(this.btnPay);
        this.pnlOrderSummary.add(this.pnlPrice);
        this.pnlOrderSummary.add(this.lblLabelPrice);
        
        this.pnlOrderSummary.setBounds( 650,10,340,280 );
        
        this.pnlOrder.add(this.pnlOrderSummary);
        
        return this.pnlOrder;
    }

    /**
     * set the table infop to the order table of main screen
     *
     * @param tabla
     */
        public void setTableModel(DefaultTableModel tabla){
        this.tblOrder.setModel(tabla);
    }

    /**
     * return the main table of orders
     *
     * @return
     */
    public JTable getTableOrder(){
        return this.tblOrder;
    }
    
    //   

    /**
     * hide the column id that is the id of the product, this info is just neede for internal things 
     *
     */
    public void hideColumnId(){
        this.tblOrder.getColumn("id").setMinWidth(000);
        this.tblOrder.getColumn("id").setMaxWidth(000);
    }

    /**
     * updates the price of the order depending on de products
     *
     * @param price
     */
        public void updatePrice(float price){
        if(price > 0)
            this.lblPrice.setText(price+"€");
        else
            this.lblPrice.setText("");
    }
    
    // --- End order panel main screen
    // --- Start IForder removeOrder functions

    /**
     * returns a panel thats contains a list of orders given
     *
     * @param orderList
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    public JPanel displayOrdersPnl(ArrayList<Order> orderList, int pnlWhiteWidth, int pnlWhiteHeight){

        ProductActions pa = ProductActions.getInstance();
        Order order;
        Vector filaTabla;

        String[]  headerTable = new String[]{"Borrar","id", "Orden", "Fecha"};
        DefaultTableModel tabla = new DefaultTableModel(headerTable, 0);
        int paramsTable = headerTable.length;
        if(!orderList.isEmpty()){
            Iterator orderIterator = orderList.iterator();
            while (orderIterator.hasNext()) {
                order= (Order) orderIterator.next();
                filaTabla = this.insertInTable(order, paramsTable);
                tabla.addRow(filaTabla);
            }
        }

       return this.setTable(tabla, pnlWhiteWidth, pnlWhiteHeight);
    }

    /**
     * returns a vector with the info of an order ready to be inserted in a table
     *
     * @param order
     * @param paramsTable
     * @return Vector
     */
    private Vector insertInTable(Order order, int paramsTable) {
        ProductActions pa = ProductActions.getInstance();
        Product product;
        Vector filaTabla = new Vector();
        String OrderText = "";
        Iterator<Map.Entry<Integer, Integer>> it = order.getOrder().entrySet().iterator();
        Map.Entry<Integer, Integer> entry;
        while(it.hasNext()) {
            entry = it.next();
            product = pa.getProductById(entry.getKey());
            OrderText = OrderText + product.getName() + " Cant: "+ entry.getValue() + ", ";
        }
        if(OrderText.length() > 0)
            OrderText = OrderText.substring(0, OrderText.lastIndexOf(','));
        
        if(paramsTable == 4){
            filaTabla.add("Borrar");
            filaTabla.add(order.getId());
        }
        filaTabla.add(OrderText);
        filaTabla.add(order.getTimestamp());
        
        return filaTabla;
    }
    
    /**
     * adds the option of deleting a order that is listed in the table of all orders
     */
    private void addTableOrdersListener(){
        this.tblAllOrders.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e){  
                
                OrderActions oa = OrderActions.getInstance();
                int row = tblAllOrders.rowAtPoint(e.getPoint());
                int column = tblAllOrders.columnAtPoint(e.getPoint());
                if(column == 0){
                    int order_id = (Integer) tblAllOrders.getValueAt(row,1);
                    yesnoDialog(order_id);
                }
            }
        });
    }
    
    /**
     * launches a yesNo dialog asking the superuser if he/she wants to delete an order from database
     *
     * @param order_id
     */
    private void yesnoDialog(int order_id){
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Realmente deseas eliminar esa orden?","Atencion",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            OrderActions oa = OrderActions.getInstance();
            oa.removeOrder(order_id);
            MainActions ma = MainActions.getInstance();
            ma.clickUserMenu(5);
        }
    }

    /**
     * launch an error message to the superuser
     *
     * @param s
     */
    public void errorMSG(String s){
        JOptionPane.showMessageDialog(null, s, "Alert", JOptionPane.ERROR_MESSAGE);  
    }
       
    /**
     * this fucntion try to adapt the width and height of the table of the products to the cointaining panel 
     * for optimizing teh space and not letting empty spaces (if the height of all the products of the 
     * table is 100 px and the panel has 400px height this functions
     * creates a scrollpanel that contains the table that has 100px of height and not 400px)
     *
     * @param table
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    private JPanel setTable(DefaultTableModel table, int pnlWhiteWidth, int pnlWhiteHeight){
        this.tblAllOrders = new JTable();
        this.tblAllOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},new String [] {"Borrar", "id", "Orden", "Fecha"}));
        this.tblAllOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.tblAllOrders.getColumn("id").setMinWidth(000);
        this.tblAllOrders.getColumn("id").setMaxWidth(000);
        this.addTableOrdersListener();
        
        int widthTable=600;
        if(table != null){
            this.tblAllOrders.setModel(table);
            widthTable = this.adjustTableWidth(this.tblAllOrders);
        }
        this.scrllPaneAllOrders= new JScrollPane(this.tblAllOrders);
        this.scrllPaneAllOrders.getViewport().setBackground(Color.WHITE);
        int heightTable = this.tblAllOrders.getRowHeight()* (this.tblAllOrders.getRowCount()+1)+25;
        
        JPanel pnl = new JPanel();
        pnl.setLocation(10,10);
        pnl.setBackground(Color.WHITE);
        
        int widthScrollPnl =pnlWhiteWidth-50;
        int heightScrollPnl = pnlWhiteHeight-60;
        
        if(pnlWhiteWidth - 50 >  widthTable)
            widthScrollPnl=widthTable;
        if(pnlWhiteHeight -  60 > heightTable)
            heightScrollPnl = heightTable;

        this.scrllPaneAllOrders.setPreferredSize(new Dimension(widthScrollPnl, heightScrollPnl));
        pnl.setSize(pnlWhiteWidth - 10,pnlWhiteHeight -10);
        
        JLabel title = new JLabel("Listado de ordenes en la base de datos: ");
        title.setFont(new Font("Serif", Font.PLAIN, 32));
        pnl.add(title);
        pnl.add(this.scrllPaneAllOrders);
        
        return pnl;
    }

    /**
     * fix the width of the columns of the table depending on the width of the info that contains and also
     * return the total width of the table
     *
     * @param table
     * @return int
     */
        public int adjustTableWidth(JTable table){
        int widthTable =0;
        for (int column = 0; column < table.getColumnCount(); column++){
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++){
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setMinWidth( preferredWidth );
            widthTable = widthTable + preferredWidth;
        }
        return widthTable;
    }
    // --- End IForder removeOrder functions
    // --- Start IForder displayOrderByDay functions

    /**
     * returns a panel with a title, a calendar and a scrollpane with a table that contaisn the orders of a
     * selcted day in the scrollpane
     *
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    public JPanel displayOrderByDayPanel(int pnlWhiteWidth, int pnlWhiteHeight){
        
        this.tblDayOrders = new JTable();
        String[]  headerTable = new String[]{"Orden", "Fecha"};
        DefaultTableModel table = new DefaultTableModel(headerTable, 0);
        this.tblDayOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.tblDayOrders.setModel(table);
        this.adjustTableWidth(this.tblDayOrders);

        
        this.scrllPaneDayOrders= new JScrollPane(this.tblDayOrders); 
        this.scrllPaneDayOrders.getViewport().setBackground(Color.WHITE);
        this.scrllPaneDayOrders.setPreferredSize(new Dimension(pnlWhiteWidth-100, pnlWhiteHeight-100));

        
        JPanel pnl = new JPanel();
        pnl.setLocation(10,10);
        pnl.setBackground(Color.WHITE);
        pnl.setSize(pnlWhiteWidth - 10,pnlWhiteHeight -10);
        
        JLabel title = new JLabel("Listado de ordenes por fecha: ");
        title.setFont(new Font("Serif", Font.PLAIN, 32));
        
        this.datePicker = new JXDatePicker();
        this.datePicker.setDate(Calendar.getInstance().getTime());
        this.datePicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OrderActions oa = OrderActions.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
                String date = dateFormatter.format(datePicker.getDate());
                if(!date.equals(""))
                    oa.updateTableOrdersDay(date);
            }
        });
       
        pnl.add(title);
        pnl.add(this.datePicker);
        pnl.add(this.scrllPaneDayOrders);
        
        return pnl;
    }

    /**
     * when a user selects a day of the calendar, this funcion receives an orderlist and updates the table
     * with all the orders received
     *
     * @param orderList
     */
    public void updateTableOrdersDay(ArrayList<Order> orderList){
        
        Order order;
        Vector filaTabla;
        String[]  headerTable = new String[]{"Orden", "Fecha"};
        DefaultTableModel table = new DefaultTableModel(headerTable, 0);
        if(orderList != null){
            if(orderList.isEmpty()){
                int paramsTable = headerTable.length;
                Iterator orderIterator = orderList.iterator();
                while (orderIterator.hasNext()) {
                    order= (Order) orderIterator.next();
                    filaTabla = this.insertInTable(order, paramsTable);
                    table.addRow(filaTabla);
                }
            }    
        }
        this.tblDayOrders.setModel(table);
        this.adjustTableWidth(this.tblDayOrders);
        if(orderList.isEmpty()){
                this.errorMSG("No hay ordenes para al fecha seleccionada");
        }
    }    
    // --- End IForder displayOrderByDay functions
}
