/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorypck;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import mainpck.MainActions;

/**
 *
 * @author vaio
 */
public class CategoryVisual extends JPanel{
    
    private JPanel pnlCategories;
    private final int defautPnlHeight= 185;
    private final int defautPnlWidth= 995;
    private JScrollPane scrllpnlCategories;
    JLabel lblName, lblImgCategory, lblTitle;
    JTextField txtName;
    JButton btnAdd;
    
    /**
     * default constructor of the class
     *
     */
    public CategoryVisual(){

        this.scrllpnlCategories = new JScrollPane(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrllpnlCategories.setBounds(10,20,this.defautPnlWidth, this.defautPnlHeight);
        this.scrllpnlCategories.setVisible(false);
        
        this.pnlCategories = new JPanel();
        this.pnlCategories.setBackground(new java.awt.Color(255, 179, 125));
        this.pnlCategories.setSize(this.defautPnlWidth, this.defautPnlHeight);
        this.setLayout(null);
        this.pnlCategories.setVisible(true);
        
        this.scrllpnlCategories.setViewportView(this.pnlCategories);
        this.scrllpnlCategories.setVisible(false);
        
    }
    
    // --- Start functions display category panel of main screen

    /**
     * this function goes over the array of categories, inserts in a label the image of the category and inserts this label
     * in a scrollpane that is inserted in a panel
     *
     * @param arrayCategories
     * @return JScrollPane
     */
        public JScrollPane updateCategoriesPanel(ArrayList<Category> arrayCategories){
        
        ListIterator iterator = arrayCategories.listIterator();
        Category category ;
        JLabel lbl;
        this.emptyPanelCategories();
        while(iterator.hasNext()){
            category = (Category) iterator.next();
            System.out.println("nombre cat "+category.getName());
            if(!category.isDisabled()){
                lbl = this.insertCategoryInPanel(category);
                if(lbl != null){
                    this.pnlCategories.add(lbl);
                }
            }
        }
        
        this.pnlCategories.setOpaque(true);
        this.pnlCategories.validate();
        this.pnlCategories.repaint();
        this.scrllpnlCategories.validate();
        this.scrllpnlCategories.repaint();
        this.scrllpnlCategories.setVisible(true);
        return this.scrllpnlCategories;
    }
    
    /**
     * empty the horizontal panel of categories of the main screen
     *
     */
    private void emptyPanelCategories(){
        Component[] com = this.pnlCategories.getComponents();
        //Inside you action event where you want to disable everything
        //Do the following
        for (int a = 0; a < com.length; a++) {
             this.pnlCategories.remove(com[a]);
        }
    }
       
    /**
     * insert the image of a category in a label and returns this label, also add a click listener and when a user
     * cliks on the image this funcion shows the products related with this category in other JPanel
     *
     * @param elem
     * @return JLabel
     */
    private JLabel insertCategoryInPanel(Category elem){
        
        ImageIcon image = loadImage(elem.getPhotoPath(),150, 150);
        if(image  != null){
            JLabel imagelabel2 = new JLabel();
            imagelabel2.setIcon(image);
            imagelabel2.setSize(150, 150);
            imagelabel2.setVisible(true);
            imagelabel2.setBorder(new EmptyBorder(10,8,0,0));
            final int category_id = elem.getId();
           
            imagelabel2.addMouseListener(new MouseAdapter()  
            {  
                @Override
                public void mouseClicked(MouseEvent e)  
                {  
                    MainActions ma = MainActions.getInstance();
                    if(category_id > 0)
                        ma.displayCategoryProducts(category_id);
                }  
            });  
            return imagelabel2;
        }
        return null;
    }
    
    /**
     * loads an image (of a category) and returns it 
     *
     * @param pathImage
     * @param menuItemWidth
     * @param menuItemHeight
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
    
    // --- End functions display category panel of main screen
    // --- Starts functions IFCategory function displayListCategories
    
    /**
     * this function lists all the categories of the database for the superuser menu of deleting or 
     * disabling a category
     *
     * @param showDisabledOption
     * @param categories
     * @param pnlWhiteWidth
     * @param pnlWhiteHeight
     * @return JPanel
     */
    public JPanel displayListCategories(Boolean showDisabledOption, ArrayList<Category> categories, int pnlWhiteWidth, int pnlWhiteHeight){
        
        JPanel pnlWhite = new JPanel();
        
        JPanel pnl = this.addCategoriesToList(categories, showDisabledOption);
        this.addIFTitle(pnlWhite);
        this.addIFReminder(pnlWhite);
        pnlWhite.setLayout(null);
        
        JScrollPane scrllPane= new JScrollPane(pnl);
        scrllPane.setLocation(30, 120);
        scrllPane.setSize(pnlWhiteWidth-50,pnlWhiteHeight-150);
        pnlWhite.add(scrllPane);
        
        return pnlWhite;
    }

    /**
     * adds under the title of the panel the reminder text with the info related to the labels 
     * with red border 
     *
     * @param pnlWhite
     */
    private void addIFReminder(JPanel pnlWhite) {
        JLabel alert = new JLabel("(*los elemento marcados en rojo están deshabilitados)");
        alert.setFont(new Font("Serif", Font.PLAIN, 12));
        alert.setLocation(300, 90);
        alert.setSize(400,20);
        pnlWhite.add(alert);
    }

    //
    /**
     * adds a title to the panel
     *
     * @param pnlWhite
     */
    private void addIFTitle(JPanel pnlWhite) {
        JLabel title = new JLabel("Selecciona categoría: ");
        title.setFont(new Font("Serif", Font.PLAIN, 32));
        title.setLocation(300, 50);
        title.setSize(400,35);
        pnlWhite.add(title);
    }

    /**
     * this function goes over the array of categories and insert each category in the panel
     * it also checks that only you can put 3 lables in a line, when this happends the funcion
     * changes the gridy value of the layout of the panel (for making a jump line) and continues
     *
     * @param categories
     * @param showDisabledOption
     * @return JPanel
     */
    private JPanel addCategoriesToList(ArrayList<Category> categories, Boolean showDisabledOption) {
        
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
        
        ListIterator iterator = categories.listIterator();
        Category category ;
        int numElems=0;
        JLabel lbl;
        while(iterator.hasNext()){
            category = (Category) iterator.next();
            lbl = this.insertCategoryInPanel(category, showDisabledOption);
            if(lbl != null){
                if(numElems > 3){
                    numElems=0;
                    c.gridy ++;
                }
                c.gridx=numElems;
                numElems++;
                pnl.add(lbl,c);
            }
        }
        return pnl;
    }
       
    /**
     * launchs a dialog yes no (this happends before removing a category)
     *
     * @param name_product
     * @param category_id
     */
    private void yesnoDialog(String name_product, int category_id){
        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Deseas borrar la categoria '"+name_product+"' de la base de datos?","Atencion",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            CategoryActions ca = CategoryActions.getInstance();
            ca.updateCategoryDisableStatus(category_id, false);
        }
    }
    
    /**
     * this function insert the label with the image of the product to the panel and adds a 
     * click listener to this label
     *
     * @param showDisabledOption
     * @param elem
     * @return JLabel
     */
    private JLabel insertCategoryInPanel(Category elem, Boolean showDisabledOption){
        
        ImageIcon image = loadImage(elem.getPhotoPath(),150, 150);
        if(image  != null){
            this.lblImgCategory = new JLabel();
            this.lblImgCategory.setIcon(image);
            this.lblImgCategory.setSize(150, 150);
            this.lblImgCategory.setVisible(true);
            this.lblImgCategory.setBorder(new EmptyBorder(10,8,0,0));
            this.lblImgCategory.setOpaque(true);
            if(elem.isDisabled())
                this.lblImgCategory.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            
            
            final Category elem_clicked = elem;
            final Boolean show_disabled_option = showDisabledOption;
           
            this.lblImgCategory.addMouseListener(new MouseAdapter(){  
                @Override
                public void mouseClicked(MouseEvent e)  
                {  
                    onCategoryClicked(elem_clicked, show_disabled_option);
                }  
            });  
            return this.lblImgCategory;
        }
        return null;
    }

    /**
     * when a label with a category is clicked, this action launches this event. This function depending if the 
     * user wants to remove the category the aplication launch a yes no message if not the user
     * wants to disable a category so it calls to the disable action
     *
     * @param elem_clicked
     * @param show_disabled_option
     */
    private void onCategoryClicked(Category elem_clicked, Boolean show_disabled_option){
        MainActions ma = MainActions.getInstance();
        ma.removeInternalFrame();

        CategoryActions ca = CategoryActions.getInstance();
        if(elem_clicked.getId() > 0){
            if(show_disabled_option){
                ca.updateCategoryDisableStatus(elem_clicked.getId(), show_disabled_option);
            }
            else{
                this.yesnoDialog(elem_clicked.getName(), elem_clicked.getId());
            }
            ma.removeInternalFrame();
        }
    }
    
    // --- End functions IFCategory function displayListCategories
    // --- Starts functions IFCategory function displayAddCategory
    
    /**
     * displays a panel for the superuser for adding a new category
     *
     * @return JPanel
     */
        public JPanel displayAddCategoryPanel(){

        JPanel pnl = new JPanel();
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        pnl.setBorder(loweredetched);
        pnl.setBackground(new java.awt.Color(0, 0, 0, 10));
        pnl.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();        
        c.anchor=GridBagConstraints.FIRST_LINE_START;

        this.lblTitle = new JLabel("Añade categoría");
        this.lblTitle.setFont(new Font("Serif", Font.PLAIN, 32));
        c.insets=new Insets(10,60,30,130);
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

        this.lblName = new JLabel("Nombre de la categoría:");
        c.insets=new Insets(10,20,1,1);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pnl.add(this.lblName,c);

        this.txtName = new JTextField();
        c.insets=new Insets(10,0,1,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pnl.add(this.txtName,c);

        this.btnAdd = new JButton("Añadir");
        c.fill = GridBagConstraints.NONE;
        c.insets=new Insets(30,160,30,20);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        pnl.add(this.btnAdd,c);
        this.btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    clickAddCategory();
                }
            });

        return pnl;
    }
    
    /**
     * when a user clicks on the button of "add a category" this function is called, this function 
     * checks that the textField is not empty and then calls the funcion addCategory of CategoryActions
     * for adding this new category if there was a problem the function launch an errorMessage if not 
     * removes the internalFrame

     */
    private void clickAddCategory(){
        CategoryActions ca = CategoryActions.getInstance();

        if(this.getTxtName().isEmpty()){
            this.errorMSG("Campos incorrectos, por favor revisa los campos");
            return;
        }
        Boolean result = ca.addCategory(this.getTxtName());
        if(!result)
            this.errorMSG("Ha habido un problema a la hora de crear la categoría");
        else{
            MainActions ma = MainActions.getInstance();
            ma.removeInternalFrame();
        }
    }
    
    // --- End functions IFCategory function displayAddCategory

    /**
     * launches an error message
     * 
     */
    private void errorMSG(String s){
        JOptionPane.showMessageDialog(null, s, "Alert", JOptionPane.ERROR_MESSAGE);  
    }

    /**
     * get teh value of teh text field
     * @return String
     */
        public String getTxtName(){
        return this.txtName.getText();
    }
}
