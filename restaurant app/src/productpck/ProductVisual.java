/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productpck;

import categorypck.Category;
import categorypck.CategoryActions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import mainpck.MainActions;

/**
 *
 * @author vaio
 */
public class ProductVisual extends JScrollPane{
    
    private final int defautPnlHeight= 185;
    private final int defautPnlWidth= 995;
    private JPanel pnlProducts;
    private JScrollPane scrllpnlProducts;
    private JLabel lblImageProduct, lblTitle, lblCategory, lblName, lblPrice, lblDescription;
    private JTextField txtName, txtPrice;
    private JButton btnAddProduct;
    private TextArea txtDescription;
    private JComboBox cmbbxCategory;
    private String categorySelected;
    
    /**
     * default constructior of the class
     */
    public ProductVisual(){
        this.scrllpnlProducts = new JScrollPane(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrllpnlProducts.setBounds(10,210,this.defautPnlWidth, this.defautPnlHeight);
        this.scrllpnlProducts.setVisible(false);
        
        this.pnlProducts = new JPanel();
        this.pnlProducts.setBackground(new java.awt.Color(128, 184, 116));
        this.pnlProducts.setSize(this.defautPnlWidth, this.defautPnlHeight);
        this.setLayout(null);
        this.pnlProducts.setVisible(true);
        
        this.scrllpnlProducts.setViewportView(this.pnlProducts);
        this.scrllpnlProducts.setVisible(false);
    }

    // ---  Starts the functions for displaying the panel of products of main screen

    
    /**
     * updates the products panel that is in the main screen with the array given
     *
     * @param arrayProducts
     */
    public void updateProductsPanel(ArrayList<Product> arrayProducts){
        
        this.emptyPanelProducts();
        ListIterator iterator = arrayProducts.listIterator();
        Product product ;
        JLabel lbl;
        while(iterator.hasNext()){
            product = (Product) iterator.next();
            if(!product.isDisabled()){
                lbl = this.insertProductInPanel(product);
                if(lbl != null){
                    System.out.println("nombre prod "+product.getName());
                    this.pnlProducts.add(lbl);
                }
            }
        }
        
        this.pnlProducts.validate();
        this.pnlProducts.repaint();
        this.scrllpnlProducts.validate();
        this.scrllpnlProducts.repaint();
        this.scrllpnlProducts.setVisible(true);
    }

    /**
     * returns the scrollPane with the products
     *
     * @return JScrollPane
     */
    public JScrollPane getScrllPnlProducts(){
        return this.scrllpnlProducts;
    }
    
    /**
     * empty the panel products
     *
     */
    private void emptyPanelProducts(){
        Component[] com = this.pnlProducts.getComponents();
        //Inside you action event where you want to disable everything
        //Do the following
        for (int a = 0; a < com.length; a++) {
             this.pnlProducts.remove(com[a]);
        }
    }

    /**
     * inserts a products in the products panel
     *
     * @param elem
     * @return JLabel
     */
    public JLabel insertProductInPanel(Product elem){
        
        ImageIcon image = loadImage(elem.getPhotoPath(),150, 150);
        if(image  != null){
            JLabel imagelabel2 = new JLabel();
            imagelabel2.setIcon(image);
            imagelabel2.setSize(150, 150);
            imagelabel2.setVisible(true);
            imagelabel2.setBorder(new EmptyBorder(10,0,0,0));
            imagelabel2.setBackground(new java.awt.Color(255, 255, 255, 120));
            imagelabel2.setOpaque(true);

            final int product_id = elem.getId();
           
            imagelabel2.addMouseListener(new MouseAdapter()  
            {  
                @Override
                public void mouseClicked(MouseEvent e)  
                {  
                    MainActions ma = MainActions.getInstance();
                    ma.showInternalFrameProduct(product_id);
                    System.out.println("pulsado encima producto, salta internal");
                }  
            });  
            return imagelabel2;
        }
        return null;
    }
    
    /**
     * Adds a image in a ImageIcon
     *
     * @param pathImage
     * @param imageWidth
     * @param imageHeight
     * @return ImageIcon
     */
    private ImageIcon loadImage(String pathImage,int menuItemWidth, int menuItemHeight) {
        try {
            BufferedImage img = ImageIO.read(new File(pathImage));
            if(img == null){
                System.out.println("Error al cargar imagen "+pathImage);
                return null;
            }
            Image dimg = img.getScaledInstance(menuItemWidth, menuItemHeight, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(dimg);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }       
    }
    // ---  End the functions for displaying the panel of products of main screen
    // ---  Start the functions of IFDescription

    /**
     * this function returns a JPanel with all the info of the product. A big image, the name of the product, the
     * description, the price and the spinner an button ad. This funcion if the user clicks in the adds button the product
     * to the order
     *
     * @param product
     * @param spnnrDefValue
     * @return JPanel
     */
    public JPanel displayDescription(Product product, int spnnrDefValue){
        
        JPanel pnlWhite = new JPanel();
        pnlWhite.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.PAGE_START;
        
        this.insertImage(product.getPhotoPath(), c, pnlWhite);
        this.insertNameLabel(product.getName(), c, pnlWhite);
        this.insertDescriptionLabel(product.getDescription(), c, pnlWhite);
        this.insertPriceElems(c, product.getPrice(), pnlWhite);
        this.insertAddElems(c, spnnrDefValue, product, pnlWhite);
        
        return pnlWhite;
    }

    /**
     * this function inserts in the main JPanel the button "add product to order" and a spinner for selecting
     * the quantity of elements you want to add, also has a click events that when is fired gets the product id 
     * and the number of the spinner and adds them to the order array
     *
     * @param c
     * @param spnnrDefValue
     * @param product
     * @param pnlWhite
     */
    private void insertAddElems(GridBagConstraints c, int spnnrDefValue, Product product, JPanel pnlWhite) {
        //Add the button for adding the product to the order
        JButton btnAñadir = new JButton("Añadir al pedido");
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weighty = 0.0;
        c.ipadx = 0;
        c.ipady = 0;
        pnlWhite.add(btnAñadir, c);
        
        //Add the spinner for selecting the number of products
        SpinnerModel modeltau = new SpinnerNumberModel(spnnrDefValue, 1, 20, 1);
        final JSpinner spnnerNumber = new JSpinner(modeltau);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 4;
        c.weighty = 0.0;
        c.ipadx = 0; 
        c.ipady = 0; 
        pnlWhite.add(spnnerNumber, c);
        
        final Product p = product;
        
         btnAñadir.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e)  
            {  
                MainActions ma = MainActions.getInstance();
                int value = (Integer) spnnerNumber.getValue();
                int product_id = p.getId();
                ma.addProuctToOrder(product_id,value);
                System.out.println("pulsado añadir "+value);
                ma.removeInternalFrame();
            }  
        });
    }

    /**
     * this function adds the label "price" to the main panel and also inserts another label with the number
     * of the price of the product
     *
     * @param c
     * @param price
     * @param pnlWhite
     */
    private void insertPriceElems(GridBagConstraints c, float price, JPanel pnlWhite) {
        //Add a label with the text "price"
        JLabel lblProductTextPrice = new JLabel("Precio:", SwingConstants.CENTER);
        lblProductTextPrice.setFont(new Font("Serif", Font.PLAIN, 23));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.ipady = 50;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        pnlWhite.add(lblProductTextPrice, c);
        
        //Add a label with the price of the product
        JLabel lblProductNumberPrice = new JLabel(Float.toString(price)+"€", SwingConstants.CENTER);
        lblProductNumberPrice.setOpaque(true);
        lblProductNumberPrice.setFont(new Font("Serif", Font.PLAIN, 23));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.ipady = 50;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 3;
        pnlWhite.add(lblProductNumberPrice, c);
    }

   
    /**
     * this function inserts the description of the product in a textarea
     *
     * @param c
     * @param description
     * @param pnlWhite
     */
    private void insertDescriptionLabel(String description, GridBagConstraints c, JPanel pnlWhite) {
        //Add a label with the description of the product
        JTextArea lblProductDescription = new JTextArea(3,3);
        lblProductDescription.setText(description);
        lblProductDescription.setLineWrap(true);
        lblProductDescription.setWrapStyleWord(true);
        lblProductDescription.setBackground(new java.awt.Color(255, 255, 255, 120));
        lblProductDescription.setFont(new Font("Serif", Font.PLAIN, 20));
        lblProductDescription.setVisible(true);
        c.insets=new Insets(20,20,20,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 12;
        c.ipady = 12;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1.0;
        c.weightx = 1.0;
        pnlWhite.add(lblProductDescription, c);
    }

    /**
     * this function inserts the label with the name of the product in a label
     *
     * @param c
     * @param name
     * @param pnlWhite
     */
    private void insertNameLabel(String name, GridBagConstraints c, JPanel pnlWhite) {
        //Add a label with the name of the product
        JLabel lblProductName = new JLabel(name, SwingConstants.CENTER);
        lblProductName.setOpaque(true);
        lblProductName.setFont(new Font("Serif", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.ipady = 50;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        pnlWhite.add(lblProductName, c);
    }

    /**
     * this function inserts the label with the image of the product
     *
     * @param c
     * @param photo_path
     * @param pnlWhite
     */
    private void insertImage(String photo_path , GridBagConstraints c, JPanel pnlWhite) {
        //Add the image of the product, if it don't have an image it put a default image
        ImageIcon image = loadImage(photo_path,200, 200);
        if(image  == null)
            image = loadImage("img/imagepackage/varoius/no_photo_small.png",300, 300);
        
        if(image  != null){
            JLabel imagelabel2 = new JLabel("", SwingConstants.CENTER);
            imagelabel2.setIcon(image);
            imagelabel2.setSize(200, 200);
            imagelabel2.setVisible(true);
            imagelabel2.setBackground(new java.awt.Color(0, 0, 0, 120));
            imagelabel2.setOpaque(true);
            
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridheight = 1;
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = 0;
            c.ipady = 30;
            pnlWhite.add(imagelabel2, c);
        }
    }
    // ---  End the functions of IFDescription
    // --- Start functions displayListProducts

    /**
     * this functions returns a panel with a title, a small text and a list labels with the image of each product
     * given in an arrayList
     *
     * @param disabeldOption
     * @param products
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    public JPanel displayListProduct(Boolean disabeldOption, ArrayList<Product> products, int pnlWhiteWidth, int pnlWhiteHeight ){
        
        JLabel lbl;
        
        JPanel pnl = new JPanel();
        pnl.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;

        c.insets=new Insets(10,10,10,10);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.ipadx = 0;
        c.ipady = 0;
        
        JPanel pnlProducts = new JPanel();
        Product product;
        ListIterator iterator = products.listIterator();
        int numElems=0;
        while(iterator.hasNext()){
            product = (Product) iterator.next();
            lbl = this.insertProductInPanel(product, disabeldOption);
            if(lbl != null){
                System.out.println("nombre prod "+product.getName());
                if(numElems > 3){
                    numElems=0;
                    c.gridy ++;
                }
                c.gridx=numElems;
                numElems++;
                pnl.add(lbl,c);
            }
        }
        JLabel title = new JLabel("Selecciona producto: ");
        title.setFont(new Font("Serif", Font.PLAIN, 32));
        title.setLocation(300, 50);
        title.setSize(400,35);
        
        JLabel alert = new JLabel("(*los elemento marcados en rojo están deshabilitados)");
        alert.setFont(new Font("Serif", Font.PLAIN, 12));
        alert.setLocation(300, 90);
        alert.setSize(400,20);
        
        JScrollPane scrllPane= new JScrollPane(pnl);
        scrllPane.setLocation(30, 120);
        scrllPane.setSize(pnlWhiteWidth-50,pnlWhiteHeight-150);
        
        JPanel pnlWhite = new JPanel();
        pnlWhite.setLayout(null);
        pnlWhite.add(title);
        pnlWhite.add(alert);
        pnlWhite.add(scrllPane);        
        return pnlWhite;
    }
    
    /**
     * returns a label that contaisn the iamge of a product and also adds to the label a event listener
     *
     * @params elem
     * @params disabledOption
     * @return JLabel
     */
    private JLabel insertProductInPanel(Product elem, Boolean disabledOption){
        
        ImageIcon image = loadImage(elem.getPhotoPath(),150, 150);
        if(image  != null){
            this.lblImageProduct = new JLabel();
            this.lblImageProduct.setIcon(image);
            this.lblImageProduct.setSize(150, 150);
            this.lblImageProduct.setVisible(true);
            this.lblImageProduct.setBackground(new java.awt.Color(255, 255, 255, 120));
            this.lblImageProduct.setOpaque(true);
            if(elem.isDisabled())
                this.lblImageProduct.setBorder(BorderFactory.createLineBorder(Color.RED, 5));

            

            final Product product_clicked = elem;
            final Boolean disabled_option = disabledOption;
           
            this.lblImageProduct.addMouseListener(new MouseAdapter()  
            {  
                @Override
                public void mouseClicked(MouseEvent e)  
                {  
                    onProductClicked(product_clicked, disabled_option);
                }  
            });  
            return this.lblImageProduct;
        }
        return null;
    }
    
    /**
     * launch a yesno dialog for asking superuser if he/she wants to delete a product
     *
     * @params name_product
     * @params category_id
     */
    private void yesnoDialog(String name_product, int category_id){
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Deseas borrar el producto '"+name_product+"' de la base de datos?","Atencion",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            ProductActions pa = ProductActions.getInstance();
            pa.updateProductStatus(category_id, false);
        }
    }
    
    /**
     * when a user clicks on a label of the function "displayListProduct" flow comes here and disbale 
     * or remove the product depending the value of show_disabled_option
     *
     * @params product_clicked
     * @params show_disabled_option
     */
    private void onProductClicked(Product product_clicked, Boolean show_disabled_option){
        MainActions ma = MainActions.getInstance();
        ma.removeInternalFrame();
  
        ProductActions pa = ProductActions.getInstance();
        if(product_clicked.getId() > 0){
            if(show_disabled_option){
                pa.updateProductStatus(product_clicked.getId(), show_disabled_option);
            }
            else{
                this.yesnoDialog(product_clicked.getName(), product_clicked.getId());
            }
            ma.removeInternalFrame();
        }
    } 
    // --- End functions displayListProducts
    // --- Start functions displayAddCatgeory

    /**
     * returns a panel with a form for create a new product and the eventListener that lauch 
     * the internal process of creation of teh product
     *
     * @return JPanel
     */
    public JPanel displayAddProduct(){

        JPanel pnl = new JPanel();
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        pnl.setBorder(loweredetched);
        pnl.setBackground(new java.awt.Color(0, 0, 0, 10));
        pnl.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        
        this.lblTitle = new JLabel("Crea producto");
        this.lblTitle.setFont(new Font("Serif", Font.PLAIN, 32));
        c.insets=new Insets(10,230,40,100);
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
                
        this.addTypePorductElements(c, pnl);
        this.addNameElements(c, pnl);
        this.addDescriptionElements(c, pnl);
        this.addPriceElements(c, pnl);

        this.btnAddProduct = new JButton("Añadir producto");
        c.fill = GridBagConstraints.NONE;
        c.insets=new Insets(30,220,30,20);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        pnl.add(this.btnAddProduct,c);
        this.btnAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    clickAddProduct();
                }
            });

        return pnl;
    
    }

    /**
     * add the price label and textfield
     *
     * @params c
     * @params pnl
     */
    private void addPriceElements(GridBagConstraints c, JPanel pnl) {
        this.lblPrice = new JLabel("Precio:");
        c.insets=new Insets(10,40,1,20);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 4;
        pnl.add(this.lblPrice,c);
        
        this.txtPrice = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(10,0,1,20);
        c.gridx = 1;
        c.gridy = 4;
        pnl.add(this.txtPrice,c);
    }

    /**
     * add the description label and textfield
     *
     * @params c
     * @params pnl
     * @throws HeadlessException
     */
    private void addDescriptionElements(GridBagConstraints c, JPanel pnl) throws HeadlessException {
        this.lblDescription = new JLabel("Descripción:");
        c.insets=new Insets(10,40,1,20);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 3;
        pnl.add(this.lblDescription,c);
        
        this.txtDescription = new TextArea(5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(10,0,1,20);
        c.gridx = 1;
        c.gridy = 3;
        pnl.add(this.txtDescription,c);
    }

    /**
     * add the name label and textfield
     *
     * @params c
     * @params pnl
     */
    private void addNameElements(GridBagConstraints c, JPanel pnl) {
        this.lblName = new JLabel("Nombre:");
        c.insets=new Insets(10,40,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pnl.add(this.lblName,c);
        
        this.txtName = new JTextField();
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        pnl.add(this.txtName,c);
    }

    /**
     * add the type product label and textfield
     *
     * @params c
     * @params pnl
     */
    private void addTypePorductElements(GridBagConstraints c, JPanel pnl) {
        this.lblCategory = new JLabel("Elije porducto:");
        c.insets=new Insets(10,40,1,20);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        pnl.add(this.lblCategory,c);
        
        CategoryActions ca = CategoryActions.getInstance();
        ArrayList<Category> categories = ca.getCategories();
        String[] categoriesNames = new String[categories.size()];
        Iterator iteratorCategory = categories.iterator();
        int i =0;
        Category category;
        while(iteratorCategory.hasNext()){
            category= (Category) iteratorCategory.next();
            categoriesNames[i]=category.getName();
            i++;
        }
        
        this.cmbbxCategory = new JComboBox(categoriesNames);
        this.cmbbxCategory.setSelectedIndex(1);
        this.categorySelected=categoriesNames[1];
        c.insets=new Insets(10,0,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pnl.add(this.cmbbxCategory,c);
        
        this.cmbbxCategory.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                getValueComboBoxCategory(e);
            }
        });
    }

    /**
     * when the combobox of the function "displayAddProduct" changes tthis function updates 
     * the value of the internal variable of the category selected
     *
     * @params e
     */
    private void getValueComboBoxCategory(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        this.categorySelected = (String)cb.getSelectedItem();
    }
        
    /**
     * when the superuser clicks on the buttn of add a category flow comes here, checks 
     * the textFields are correct and launch the process of creation
     */
    private void clickAddProduct(){
        ProductActions pa = ProductActions.getInstance();

        if(this.getTxtName().isEmpty() || this.getTxtCategory().isEmpty() || this.getTxtDescription().isEmpty() || this.getTxtPrice() <= 0){
            this.errorMSG("Campos incorrectos, por favor revisa los campos");
            return;
        }
        Boolean result = pa.addProduct(this.getTxtCategory(), this.getTxtName(), this.getTxtDescription(), this.getTxtPrice());
        if(!result)
            this.errorMSG("Campos incorrectos, por favor revisa los campos");
        else{
            MainActions ma = MainActions.getInstance();
            ma.removeInternalFrame();
        }
    }
    // --- End functions displayAddCatgeory
    // --- Start functions displayIFReceipt

    /**
     * returns a scrollpane with a receipt with the informnation of the order, the list of all the 
     * products, the total price the VAT info, the button for pay...
     *
     * @param orderList
     * @param discount
     * @return JScrollPane
     */
    public JScrollPane displayReceipt(Map<Integer, Integer> orderList, float discount){
        
        JPanel pnlReceipt = new JPanel();
        pnlReceipt.setLayout(new GridBagLayout());
        float price;
        
        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        
        this.addLogo(c, pnlReceipt);
        this.addEnterpriseData(c, pnlReceipt);
        this.addDate(c, pnlReceipt);
        price=this.addTableProducts(orderList, c, pnlReceipt);

        price=(price*(100-discount))/100;
        price = (float) (Math.round(price*100.0)/100.0);

        this.addPriceElems(price,c, pnlReceipt);
        this.addTableVAT(price, c, pnlReceipt);
        this.addFooter(c, pnlReceipt);
        this.addBtnPay(c, pnlReceipt);
        
        JScrollPane scrllPane = new JScrollPane(pnlReceipt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrllPane.setBorder(null);
        scrllPane.setPreferredSize(new Dimension(400,590));
        
        return scrllPane;
    }

    /**
     * adds to the receiptPanel the button pay and the listener for continue the process
     *
     * @param C
     * @param pnlReceipt
     */
    private void addBtnPay(GridBagConstraints c, JPanel pnlReceipt) {
        JButton btnPay = new JButton("Pagar");
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.ipadx = 0;
        c.ipady = 0;
        pnlReceipt.add(btnPay, c);
        
         btnPay.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e)  
            {  
                MainActions ma = MainActions.getInstance();
                System.out.println("Reiniciando el programa... ");
                ma.removeInternalFrame();
                ma.saveOrder();
                ma.launchReload();
                ma.logOutUser();
            }  
        });
    }

    /**
     * adds to the receiptPanel the footer 
     *
     * @param c
     * @param pnlReceipt
     */
    private void addFooter(GridBagConstraints c, JPanel pnlReceipt) {
        JTextArea txtareaFooterReceipt = new JTextArea();
        String textFooter="IVA incluido"+"\n"+"Gracias por su visita"+"\n"+"Disfrute de su comida"+"\n"+
                "Recuerde que en Healthy Burger siemrpe estamos haciendo promociones y ofertas, para estár el dia sobre "+
                "todas las novedades de  Healthy Burger no olvide en seguirnos en Twitter @healthyburger, en Facebook "+
                "www.facebook.com/healthyburger así como en nuestra pagina web www.healthyburger.es"+"\n"+
                "En Healthy Burger nos preocupamos tanto por la salud de nuestros clientes como por la comida "+
                "que les servimos e intentamso siempre ofrecerles la maxima calidad tanto para usted como "+
                "para su familia, por favor si tiene cualquier sugerencia no dude en comentarsela al "+
                "personal de Healthy Burger o a traves del buzón de sugerencias online ";
        txtareaFooterReceipt.setText(textFooter);
        txtareaFooterReceipt.setLineWrap(true);
        txtareaFooterReceipt.setWrapStyleWord(true);
        txtareaFooterReceipt.setFont(new Font("Serif", Font.PLAIN, 10));
        txtareaFooterReceipt.setVisible(true);
        c.insets=new Insets(40,20,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.ipadx = 0;
        c.ipady = 0;
        pnlReceipt.add(txtareaFooterReceipt, c);
    }

    /**
     * adds to the receiptPanel the table with the vat info
     *
     * @param c
     * @param pnlReceipt
     * @params discountedPrice
     */
    private void addTableVAT(float discountedPrice, GridBagConstraints c, JPanel pnlReceipt) {
        
        DecimalFormat df = new DecimalFormat("0.00");
        float priceWithoutVAT = (21*discountedPrice)/100;
        
        JTable tblVAT = new JTable();
        tblVAT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableModel tabla = new DefaultTableModel(new String[]{"Desglose IVA","Base Imp", "Total IVA"}, 0);
        Vector filaTabla = new Vector();
        filaTabla.add("21%");
        filaTabla.add(df.format(discountedPrice - priceWithoutVAT));
        filaTabla.add(df.format(priceWithoutVAT));

        tabla.addRow(filaTabla);
        tblVAT.setModel(tabla);
        
        JPanel pnlTableVAT = new JPanel();
        JTableHeader headerVAT = tblVAT.getTableHeader();
        pnlTableVAT.setLayout(new BorderLayout());
        pnlTableVAT.add(headerVAT, BorderLayout.NORTH);
        pnlTableVAT.add(tblVAT, BorderLayout.CENTER);
        
        c.insets=new Insets(1,1,1,1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        c.weighty = 1.0;
        c.weightx = 1.0;
        pnlReceipt.add(pnlTableVAT, c);
    }

    /**
     * adds to the receiptPanel the panel of the price info
     *
     * @param c
     * @param pnlReceipt
     * @params discountedPrice
     */
    private void addPriceElems(float discountedPrice, GridBagConstraints c, JPanel pnlReceipt) {
        //Add the button for adding the product to the order
        JLabel lblTextPrice = new JLabel("Total a pagar:");
        lblTextPrice.setFont(new Font("Serif", Font.PLAIN, 18));
        c.insets=new Insets(20,10,20,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 1.0;
        c.weightx = 1.0;
        pnlReceipt.add(lblTextPrice, c);
        
        //Add the button for adding the product to the order
        JLabel lblPrice = new JLabel(discountedPrice+"€");
        lblPrice.setFont(new Font("Serif", Font.PLAIN, 25));
        c.insets=new Insets(20,10,20,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 4;
        c.weighty = 1.0;
        c.weightx = 1.0;
        pnlReceipt.add(lblPrice, c);
    }

    /**
     * adds to the receiptPanel the table with all teh products of the order
     *
     * @param orderList
     * @param pnlReceipt
     * @params c
     * @return float
     */
    private float addTableProducts(Map<Integer, Integer> orderList, GridBagConstraints c, JPanel pnlReceipt) {
        
        ProductActions pa = new ProductActions();
        Product product;

        JTable tblOrder = new JTable();
        tblOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableModel tabla = new DefaultTableModel(new String[]{"N","Unid", "Nommbre del producto", "Precio"}, 0);
        
        int i =0;
        float price =0;
        Vector filaTabla;
        
        Iterator<Map.Entry<Integer, Integer>> it = orderList.entrySet().iterator();
        Map.Entry<Integer, Integer> entry;
        while(it.hasNext()) {
            entry = it.next();
            product = pa.getProductById(entry.getKey());
            if(product != null){
                filaTabla = new Vector();
                filaTabla.add(i+1);
                filaTabla.add(entry.getValue());
                filaTabla.add(product.getName());
                filaTabla.add((product.getPrice())*entry.getValue());
                price = price +(product.getPrice())*entry.getValue();
                tabla.addRow(filaTabla);
                i++;
            }
        }
        tblOrder.setModel(tabla);
        
        this.adjustTableWidth(tblOrder);
        
        JPanel pnlTable = new JPanel();
        JTableHeader header = tblOrder.getTableHeader();
        pnlTable.setLayout(new BorderLayout());
        pnlTable.add(header, BorderLayout.NORTH);
        pnlTable.add(tblOrder, BorderLayout.CENTER);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 1.0;
        c.weightx = 1.0;
        pnlReceipt.add(pnlTable, c);
        
        return price;
    }

    /**
     * adds to the receiptPanel the date of the order
     *
     * @param orderList
     * @param pnlReceipt
     */
    private void addDate(GridBagConstraints c, JPanel pnlReceipt) {
        //Add a label with the description of the product
        JTextArea txtareaDate = new JTextArea();
        txtareaDate.setText("Fecha : 16/05/2012 10:30:25"+"\n"+"Factura simplificada"+"\n"+"Le atendio: Máquina autoventa"+"\n");
        txtareaDate.setLineWrap(true);
        txtareaDate.setWrapStyleWord(true);
        txtareaDate.setFont(new Font("Serif", Font.PLAIN, 12));
        txtareaDate.setVisible(true);
        c.insets=new Insets(10,1,10,1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1.0;
        c.weightx = 0.5;
        pnlReceipt.add(txtareaDate, c);
    }

    /**
     * adds to the receiptPanel the info of the enterprise
     *
     * @param c
     * @param pnlReceipt
     */
    private void addEnterpriseData(GridBagConstraints c, JPanel pnlReceipt) {
        //Add a label with the name of the product
        
        JTextArea txtareaEnterpriseData = new JTextArea();
        txtareaEnterpriseData.setText("Healthy burger"+"\n"+"Calle Pio Braoja nº23 Almansa"+"\n"+"Albacete"+"\n"+"Codigo postal 280180"+"\n"+"Spain"+"\n");
        txtareaEnterpriseData.setLineWrap(true);
        txtareaEnterpriseData.setWrapStyleWord(true);
        txtareaEnterpriseData.setFont(new Font("Serif", Font.PLAIN, 12));
        txtareaEnterpriseData.setVisible(true);
        c.insets=new Insets(40,20,1,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pnlReceipt.add(txtareaEnterpriseData, c);
    }

    /**
     * adds to the receiptPanel the logo of the enterprise
     *
     * @param c
     * @param pnlReceipt
     */
    private void addLogo(GridBagConstraints c, JPanel pnlReceipt) {
        //Add the image of the product, if it don't have an image it put a default image
        ImageIcon image = loadImage("img/imagepackage/logo/minilogo_small.png",150, 100);
        if(image  != null){
            
            JLabel imagelabel2 = new JLabel("", SwingConstants.CENTER);
            imagelabel2.setIcon(image);
            imagelabel2.setSize(200, 200);
            imagelabel2.setVisible(true);
            imagelabel2.setBackground(new java.awt.Color(0, 0, 0, 120));
            
            c.insets=new Insets(20,20,20,20);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weighty = 0.0;
            c.ipadx = 0;
            c.ipady = 30;
            
            pnlReceipt.add(imagelabel2, c);
        }
    }
    
    /**
     * this function adjust the width of the table according to the wifth of the name of the products
     *
     * @param tabla
     */
    private void adjustTableWidth(JTable tabla){
        for (int column = 0; column < tabla.getColumnCount(); column++){
            TableColumn tableColumn = tabla.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < tabla.getRowCount(); row++){
                TableCellRenderer cellRenderer = tabla.getCellRenderer(row, column);
                Component c = tabla.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + tabla.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setMinWidth( preferredWidth );
        }
    }
    // --- End functions displayIFReceipt
    
    /**
     * launch an error message
     *
     * @param s
     */
    public void errorMSG(String s){
        JOptionPane.showMessageDialog(null, s, "Alert", JOptionPane.ERROR_MESSAGE);  
    }
    
    /**
     * get the value of the textfield category
     *
     * @return String
     */
    public String getTxtCategory(){
        return this.categorySelected;
    }
        
    /**
     * get the value of the textfield name
     *
     * @return String
     */
    public String getTxtName(){
        return this.txtName.getText();
    }
            
    /**
     * get the value of the textfield description
     * 
     * @return String
     */
    public String getTxtDescription(){
        return this.txtDescription.getText();
    }
        
    /**
     * get the value of the price
     *
     * @return float
     */
    public float getTxtPrice(){
        float price =0;
        try{
            price = Float.parseFloat(this.txtPrice.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Error al convertir a float el precio");
       }
        return price;
    }
}
