/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpck;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import mainpck.MainActions;
import orderpck.Order;
import productpck.Product;
import productpck.ProductActions;

/**
 *
 * @author vaio
 */
public class UserVisual {
    
    JLabel lblName, lblPassword, lblEmail, lblSurname, lblIdNumber, lblId, lblTitle;
    JTextField txtName, txtSurname, txtEmail, txtIdNumber;
    JButton btnLogin, btnRegister;
    JScrollPane scrllPaneHistory, scrllPaneListUser;
    JTable tblHistory, tblListUser;
    JPasswordField txtPassword;

    // --- Start history panel functions

    /**
     * retuns a panel with the historical of orders of a user (the historical is given as a parameter)
     *
     * @param history
     * @param username
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    public JPanel displayHistoryPnl(ArrayList<Order> history, String username, int pnlWhiteWidth, int pnlWhiteHeight){

        ProductActions pa = ProductActions.getInstance();
        Order order;
        Vector filaTabla;

        DefaultTableModel tabla = new DefaultTableModel(new String[]{"Orden", "Fecha"}, 0);
        if(!history.isEmpty()){
            Iterator iteratorHistory = history.iterator();
            while (iteratorHistory.hasNext()) {
                order= (Order) iteratorHistory.next();
                filaTabla = this.insertInTable(order, pa);
                tabla.addRow(filaTabla);
            }
        }
       return this.setTable(tabla, username, pnlWhiteWidth, pnlWhiteHeight);
    }
    
    /**
     * inserts in a vector the info of the order given as a parameter
     *
     * @param order
     * @param pa
     * @return Vector
     */
    private Vector insertInTable(Order order, ProductActions pa) {
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
        
        filaTabla.add(OrderText);
        filaTabla.add(order.getTimestamp());
        
        return filaTabla;
    }
    
    /**
     *
     * launchs al error message
     * @param s
     */
    public void errorMSG(String s){
        JOptionPane.showMessageDialog(null, s, "Alert", JOptionPane.ERROR_MESSAGE);  
    }
       
    /**
     * inserts in the table the info of all teh records pre processed, also fix the width and height of
     * the scrollpanel that has inside the table of the orders depending the mesaures of the table
     * this is for optimizing the space and not having empty space inside the scrollpane
     *
     * @param table
     * @param username
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    private JPanel setTable(DefaultTableModel table, String username, int pnlWhiteWidth, int pnlWhiteHeight){
        this.tblHistory = new JTable();
        this.tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},new String [] {"Orden", "Fecha"}));
        this.tblHistory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        int widthTable=600;
        if(table != null){
            this.tblHistory.setModel(table);
            widthTable = this.adjustTableWidth(this.tblHistory);
        }
        this.scrllPaneHistory= new JScrollPane(this.tblHistory);
        this.scrllPaneHistory.getViewport().setBackground(Color.WHITE);
        int heightTable = this.tblHistory.getRowHeight()* (this.tblHistory.getRowCount()+1)+25;
        
        JPanel pnl = new JPanel();
        pnl.setLocation(10,10);
        pnl.setBackground(Color.WHITE);
        
        int widthScrollPnl =pnlWhiteWidth-50;
        int heightScrollPnl = pnlWhiteHeight-50;
        
        if(pnlWhiteWidth - 50 >  widthTable)
            widthScrollPnl=widthTable;
        if(pnlWhiteHeight -  50 > heightTable)
            heightScrollPnl = heightTable;

        this.scrllPaneHistory.setPreferredSize(new Dimension(widthScrollPnl, heightScrollPnl));
        pnl.setSize(pnlWhiteWidth - 10,pnlWhiteHeight -10);
        
        
        JLabel title = new JLabel("Historial de usuario: "+username);
        title.setFont(new Font("Serif", Font.PLAIN, 32));
        pnl.add(title);
        pnl.add(this.scrllPaneHistory);
        
        return pnl;
    }
    
    /**
     * adjusts the width of teh columns of the table depending on the width of the text they contains and 
     * also returns the value of the total width of the table
     *
     * @param table
     * @return int
     */
    private int adjustTableWidth(JTable table){
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
    
    // --- End history panel functions
    // --- Start IFLogin functions 

    /**
     * return a panel with a form for login a user and also the eventlistener for starting the login process>
     *
     * @return JPanel
     */
    public JPanel displayLoginUser(){

        JPanel pnl = new JPanel();
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        pnl.setBorder(loweredetched);
        pnl.setBackground(new java.awt.Color(0, 0, 0, 10));
        pnl.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        
        this.lblTitle = new JLabel("Acceso usuarios");
        this.lblTitle.setFont(new Font("Serif", Font.PLAIN, 32));
        c.insets=new Insets(1,50,40,40);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.ipadx = 0;
        c.ipady = 0;
        pnl.add(this.lblTitle,c);

        this.addEmailElementsLogin(c, pnl);
        this.addPasswordElementsLogin(c, pnl);

        this.btnLogin = new JButton("Acceso");
        c.fill = GridBagConstraints.NONE;
        c.insets=new Insets(30,120,30,1);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        pnl.add(this.btnLogin,c);
        this.btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    checkLoginParams();
                }
            });
        return pnl;
    }

    /**
     * add to the panel the email label and email textfield
     *
     * @params c
     * @params pnl
     */
    private void addEmailElementsLogin(GridBagConstraints c, JPanel pnl) {
        this.lblEmail = new JLabel("Email:");
        c.insets=new Insets(10,40,1,1);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        pnl.add(this.lblEmail,c);
        
        this.txtEmail = new JTextField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pnl.add(this.txtEmail,c);
    }

    /**
     * add to the panel the password label and password textfield
     *
     * @params c
     * @params pnl
     */
    private void addPasswordElementsLogin(GridBagConstraints c, JPanel pnl) {
        this.lblPassword = new JLabel("Password:");
        c.insets=new Insets(10,40,1,1);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 2;
        pnl.add(this.lblPassword,c);
        
        this.txtPassword = new JPasswordField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(10,0,1,20);
        c.gridx = 1;
        c.gridy = 2;
        pnl.add(this.txtPassword,c);
    }
    
    /**
     * checks the params introduced by the user are correct and start the login process
     *
     */
    private void checkLoginParams(){
    UserActions ua = UserActions.getInstance();

        if(this.getTxtEmail().isEmpty() || this.getTxtPassword().isEmpty()){
            this.errorMSG("Campos incorrectos, por favor revisa los campos");
            return;
        }
        boolean result = ua.processLoginUser(this.getTxtEmail(), this.getTxtPassword());
        if(!result)
            this.errorMSG("Campos incorrectos, por favor revisa los campos");    
    }
    //--- End function IFLogin
    //--- Start function IFRegister
    
    //

    /**
     * return a panel with a form for register a user and also the eventlistener for starting the register process
     *
     * @return JPanel
     */
    public JPanel displayRegisterUser(){
        
        JPanel pnl = new JPanel();
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        pnl.setBorder(loweredetched);
        pnl.setBackground(new java.awt.Color(0, 0, 0, 10));
        pnl.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        
        this.lblTitle = new JLabel("Regsitro de ususarios");
        this.lblTitle.setFont(new Font("Serif", Font.PLAIN, 32));
        c.insets=new Insets(1,90,40,40);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.ipadx = 0;
        c.ipady = 0;
        pnl.add(this.lblTitle,c);

        this.addNameElementsRegister(c, pnl);
        this.addSurnameElementsRegister(c, pnl);
        this.addEmailElementsRegister(c, pnl);
        this.addIDNumberElementsRegister(c, pnl);
        this.addPasswordElementsRegister(c, pnl);

        this.btnRegister = new JButton("Registro");
        c.fill = GridBagConstraints.NONE;
        c.insets=new Insets(30,190,30,20);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        pnl.add(this.btnRegister,c);
        this.btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkRegisterParams();
            }
        });
        return pnl;
    }

    /**
     * add the password label and textfield
     *
     * @param c
     * @param pnl
     */
    private void addPasswordElementsRegister(GridBagConstraints c, JPanel pnl) {
        this.lblPassword = new JLabel("Password:");
        c.insets=new Insets(10,40,1,0);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        pnl.add(this.lblPassword, c);
        
        this.txtPassword = new JPasswordField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 5;
        pnl.add(this.txtPassword,c);
    }

    /**
     * add the id_number label and textfield
     *
     * @param c
     * @param pnl
     */
    private void addIDNumberElementsRegister(GridBagConstraints c, JPanel pnl) {
        this.lblIdNumber = new JLabel("DNI:");
        c.insets=new Insets(10,40,1,0);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        pnl.add(this.lblIdNumber, c);
        
        this.txtIdNumber = new JTextField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 4;
        pnl.add(this.txtIdNumber,c);
    }

    /**
     * add the email label and textfield
     *
     * @param c
     * @param pnl
     */
    private void addEmailElementsRegister(GridBagConstraints c, JPanel pnl) {
        this.lblEmail = new JLabel("Email:");
        c.insets=new Insets(10,40,1,0);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        pnl.add(this.lblEmail, c);
        
        this.txtEmail = new JTextField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        pnl.add(this.txtEmail,c);
    }

    /**
     * add the surname label and textfield
     *
     * @param c
     * @param pnl
     */
    private void addSurnameElementsRegister(GridBagConstraints c, JPanel pnl) {
        this.lblSurname = new JLabel("Apellidos:");
        c.insets=new Insets(10,40,1,0);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        pnl.add(this.lblSurname, c);
        
        this.txtSurname = new JTextField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        pnl.add(this.txtSurname,c);
    }

    /**
     * add the name label and textfield
     *
     * @param c
     * @param pnl
     */
    private void addNameElementsRegister(GridBagConstraints c, JPanel pnl) {
        this.lblName = new JLabel("Nombre:");
        c.insets=new Insets(10,40,1,1);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pnl.add(this.lblName,c);
        
        this.txtName = new JTextField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        pnl.add(this.txtName,c);
    }
        
    /**
     * checks the params introduced by the user are correct and start the register process
     */
    private void checkRegisterParams(){
    UserActions ua = UserActions.getInstance();

    
        if(this.getTxtEmail().isEmpty() || this.getTxtPassword().isEmpty() ||
                this.getTxtName().isEmpty() || this.getTxtSurname().isEmpty() || this.getTxtIdNumber().isEmpty()){
            this.errorMSG("Campos incorrectos, por favor revisa los campos");
            return;
        }
        boolean result = ua.processRegisterUser(this.getTxtEmail(), this.getTxtPassword(), this.getTxtName(), this.getTxtSurname(), this.getTxtIdNumber());
        if(!result)
            this.errorMSG("Campos incorrectos, por favor revisa los campos");    
    }
    //--- End function IFRegister
    //--- Start function IFUser displayUserListPnl

    /**
     * returns a panel with a list of users given as a parameter
     *
     * @param userList
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    public JPanel displayUserListPnl(ArrayList<User> userList, int pnlWhiteWidth, int pnlWhiteHeight){

        ProductActions pa = ProductActions.getInstance();
        User user;
        Vector filaTabla;

        DefaultTableModel tabla = new DefaultTableModel(new String[]{"Borrar", "id", "Nombre", "Apellidos", "Email", "ID", "Dto"}, 0);
        if(!userList.isEmpty()){
            Iterator iteratorListUser = userList.iterator();
            while (iteratorListUser.hasNext()) {
                user= (User) iteratorListUser.next();
                filaTabla = this.insertInTable(user);
                tabla.addRow(filaTabla);
            }
        }
       return this.setTable(tabla, pnlWhiteWidth, pnlWhiteHeight);
    }
    
    /**
     * returns a vectro with the info of teh user given as a parameter
     *
     * @param user
     * @return Vector
     */
    private Vector insertInTable(User user) {
        Vector filaTabla = new Vector();
        
        filaTabla.add("Borrar");
        filaTabla.add(user.getUserId());
        filaTabla.add(user.getName());
        filaTabla.add(user.getSurname());
        filaTabla.add(user.getEmail());
        filaTabla.add(user.getIdNumber());
        filaTabla.add(user.getDiscount());
        
        return filaTabla;
    }
    
    /**
     * add the listener for removing a user to the table of list o users
     *
     */
    private void addTableOrdersListener(){
        this.tblListUser.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e){  
                int row = tblListUser.rowAtPoint(e.getPoint());
                int column = tblListUser.columnAtPoint(e.getPoint());
                if(column == 0){
                    int user_id=0;
                    try{
                        user_id = (Integer) tblListUser.getValueAt(row,1);
                    }catch(Exception excep){
                        System.out.println("Ha habido un error al obetenr el id del usuario en la lista de usuarios");
                    }
                    yesnoDialog(user_id);
                }
            }
        });
    }
    
    /**
     * launch a yesNo dialog befor removing a user, is the answer is yes the functions launch
     * the remove process
     *
     * @param user_id
     */
    private void yesnoDialog(int user_id){
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Realmente deseas eliminar este usuario?","Atencion",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            UserActions ua = UserActions.getInstance();
            ua.removeUser(user_id);
            MainActions ma = MainActions.getInstance();
            ma.clickUserMenu(4);
        }
    }
    
    /**
     * insert the info of teh list of users in the table and also dependeing on the measures of the
     * values inserted on the table fix the measures of the parent of teh table (the table is inside 
     * a scrollpane and the measures of the tabel affects to the measures of the scrollpane)
     *
     * @param table
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    private JPanel setTable(DefaultTableModel table, int pnlWhiteWidth, int pnlWhiteHeight){
        this.tblListUser = new JTable();
        this.tblListUser.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.addTableOrdersListener();
        
        int widthTable=700;
        if(table != null){
            this.tblListUser.setModel(table);
            this.tblListUser.getColumn("id").setMinWidth(000);
            this.tblListUser.getColumn("id").setMaxWidth(000);
            widthTable = this.adjustTableWidth(this.tblListUser);
        }
        this.scrllPaneListUser= new JScrollPane(this.tblListUser);
        this.scrllPaneListUser.getViewport().setBackground(Color.WHITE);
        int heightTable = this.tblListUser.getRowHeight()* (this.tblListUser.getRowCount()+1)+25;
        
        JPanel pnl = new JPanel();
        pnl.setLocation(10,10);
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        pnl.setBorder(loweredetched);
        pnl.setBackground(new java.awt.Color(0, 0, 0, 10));
        
        int widthScrollPnl =pnlWhiteWidth-50;
        int heightScrollPnl = pnlWhiteHeight-50;
        
        if(pnlWhiteWidth - 50 >  widthTable)
            widthScrollPnl=widthTable;
        if(pnlWhiteHeight - 50 > heightTable)
            heightScrollPnl = heightTable;

        this.scrllPaneListUser.setPreferredSize(new Dimension(widthScrollPnl, heightScrollPnl));
        pnl.setSize(pnlWhiteWidth - 30,pnlWhiteHeight -30);
        
        JLabel title = new JLabel("Listado de usuarios en la base de datos: ");
        title.setFont(new Font("Serif", Font.PLAIN, 32));
        pnl.add(title);
        pnl.add(this.scrllPaneListUser);
        
        return pnl;
    }
    
    //--- End function IFUser displayUserListPnl
    
    /**
     * get the value of the email textfield
     *
     * @return String
     */
        
    public String getTxtEmail(){
        return this.txtEmail.getText();
    }
        
    /**
     * get the value of the password textfield
     *
     * @return String
     */
    public String getTxtPassword(){
        return new String(this.txtPassword.getPassword());
    }
        
    /**
     * get the value of the name textfield
     *
     * @return String
     */
    public String getTxtName(){
        return this.txtName.getText();
    }
        
    /**
     * get the value of the surname textfield
     *
     * @return String
     */
    public String getTxtSurname(){
        return txtSurname.getText();
    }
        
    /**
     * get the value of the id number textfield
     *
     * @return String
     */
    public String getTxtIdNumber(){
        return this.txtIdNumber.getText();
    }
}
