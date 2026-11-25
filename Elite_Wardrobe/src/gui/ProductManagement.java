/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.InvoiceItem;
import model.MySQL21;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Dracula
 */
public class ProductManagement extends javax.swing.JFrame {

    private Home home;
    
    public static Logger logger = Logger.getLogger("elite");
     
    private String companyId;
    private String employeeType;
    
    private OrderManagement orderManagement;
    
    public void setOrderManagement(OrderManagement orderManagement){
        this.orderManagement = orderManagement;
    }
    
    private GRNManagement gRNManagement;
    
    public void setGRNManagement(GRNManagement gRNManagement){
        this.gRNManagement = gRNManagement;
    }
    
    private static HashMap<String,String> brandMap = new HashMap<>();    
    private static HashMap<String,String> categoryMap = new HashMap<>();    
    private static HashMap<String,String> sizeMap = new HashMap<>();    
    private static HashMap<String,String> colorMap = new HashMap<>();    
    private static HashMap<String,String> typeMap = new HashMap<>();    
    private static HashMap<String,String> materialMap = new HashMap<>();    
    private static HashMap<String,String> fashionseasonMap = new HashMap<>();    
    
    public ProductManagement(Home home, String employeeType) {
        initComponents();
        this.home = home;
        this.employeeType = employeeType;
        
        try {
            FileHandler fileHandler = new FileHandler("elite.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loadBrand();
        loadCategory();
        loadSize();
        loadColor();
        loadType();
        loadMaterial();
        loadSeason();
        loadProduct();
        loadStock();
        
        setButtonAccess();
        
    }

    public void setButtonAccess() {
        if (employeeType.equalsIgnoreCase("cashier")) {
            employeeManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("stores")) {
            customerManagementButton.setEnabled(false);
            employeeManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
            
            productReportButton.setEnabled(false);
            
        } else if (employeeType.equalsIgnoreCase("hr manager")) {
            customerManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);            
        } else if (employeeType.equalsIgnoreCase("sales associate")) {
            employeeManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);              
        }
        // Admin has access to all, so no need to disable any buttons
    }    
    
    private void loadBrand(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `brand`");
            
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable5.getModel();
            tableModel.setRowCount(0); 
            
            while(resultSet.next()){
                String brandID = resultSet.getString("id");
                String brandName = resultSet.getString("name");
                
                comboBoxData.add(brandName);
                brandMap.put(brandName, brandID);

                Vector<String> tableRow = new Vector<>();
                tableRow.add(brandID);  
                tableRow.add(brandName); 
                tableModel.addRow(tableRow);  
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxData);
            jComboBox5.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load brand data from database.");
        }
        
    }

    private void loadCategory() {
        try {
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `product_category`");

            // Load data into JComboBox
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");  

            // Prepare the table model for JTable
            DefaultTableModel tableModel = (DefaultTableModel) jTable3.getModel();
            tableModel.setRowCount(0);  

            while (resultSet.next()) {
                String categoryId = resultSet.getString("id");
                String categoryName = resultSet.getString("category_name");

                comboBoxData.add(categoryName);
                categoryMap.put(categoryName, categoryId);

                Vector<String> tableRow = new Vector<>();
                tableRow.add(categoryId);  
                tableRow.add(categoryName); 
                tableModel.addRow(tableRow);  
            }

            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(comboBoxData);
            jComboBox2.setModel(comboBoxModel);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load category data from database.", e);
            e.printStackTrace();
        }
    }

    
    private void loadSize(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `size`");
            
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable4.getModel();
            tableModel.setRowCount(0); 
            
            while(resultSet.next()){
                String sizeID = resultSet.getString("id");
                String sizeName = resultSet.getString("name");
                
                comboBoxData.add(sizeName);
                sizeMap.put(sizeName, sizeID);

                Vector<String> tableRow = new Vector<>();
                tableRow.add(sizeID);  
                tableRow.add(sizeName); 
                tableModel.addRow(tableRow);  
            }
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load size data from database.");
        }
        
    }
    
    private void loadColor(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `color`");
            
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable8.getModel();
            tableModel.setRowCount(0); 
            
            while(resultSet.next()){
                String colorID = resultSet.getString("id");
                String colorName = resultSet.getString("name");
                
                comboBoxData.add(colorName);
                colorMap.put(colorName, colorID);

                Vector<String> tableRow = new Vector<>();
                tableRow.add(colorID);  
                tableRow.add(colorName); 
                tableModel.addRow(tableRow);  
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxData);
            jComboBox6.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load color data from database.");
        }
        
    }
    
    private void loadMaterial(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `material`");
            
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable6.getModel();
            tableModel.setRowCount(0); 
            
            while(resultSet.next()){
                String materialID = resultSet.getString("id");
                String materialName = resultSet.getString("name");
                
                comboBoxData.add(materialName);
                materialMap.put(materialName, materialID);

                Vector<String> tableRow = new Vector<>();
                tableRow.add(materialID);  
                tableRow.add(materialName); 
                tableModel.addRow(tableRow);  
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxData);
            jComboBox7.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load material data from database.");
        }
        
    }
    
    private void loadType(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `clothing_gender`");
            
            Vector<String> vector = new Vector<>();
            vector.add("Select");
            
            while(resultSet.next()){
                vector.add(resultSet.getString("name"));
                typeMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox8.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load clothe type data from database.");
        }
        
    }
    
    private void loadSeason(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `fashion_season`");
            
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable7.getModel();
            tableModel.setRowCount(0); 
            
            while(resultSet.next()){
                String seasonID = resultSet.getString("id");
                String seasonName = resultSet.getString("name");
                
                comboBoxData.add(seasonName);
                fashionseasonMap.put(seasonName, seasonID);

                Vector<String> tableRow = new Vector<>();
                tableRow.add(seasonID);  
                tableRow.add(seasonName); 
                tableModel.addRow(tableRow);  
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxData);
            jComboBox4.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load fashion season data from database.");
        }
        
    }
    
    private void loadProduct(){
    
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `product` INNER JOIN `brand` "
                    + "ON `product`.`brand_id` = `brand`.`id` "
                    + "INNER JOIN `product_category` ON `product`.`product_category_id` = `product_category`.`id` "
                    + "INNER JOIN `fashion_season` ON `product`.`fashion_season_id` = `fashion_season`.`id` "
                    + "INNER JOIN `color` ON `product`.`color_id` = `color`.`id` "
                    + "INNER JOIN `material` ON `product`.`material_id` = `material`.`id` "
                    + "INNER JOIN `clothing_gender` ON `product`.`clothing_gender_id` = `clothing_gender`.`id`");
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.setRowCount(0);
            
            while (resultSet.next()) {                
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
                vector.add(resultSet.getString("brand.name"));
                vector.add(resultSet.getString("product_category.category_name"));
                vector.add(resultSet.getString("color.name"));
                vector.add(resultSet.getString("material.name"));
                vector.add(resultSet.getString("clothing_gender.name"));
                vector.add(resultSet.getString("fashion_season.name"));
                
                model.addRow(vector);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }    
    
    private void loadStock(){
    try {
        String query = "SELECT * FROM `stock` INNER JOIN `product` "
                + "ON `stock`.`product_id` = `product`.`id` "
                + "INNER JOIN `brand` ON `brand`.`id` = `product`.`brand_id` "
                + "INNER JOIN `size` ON `stock`.`size_id` = `size`.`id` ";
        
        int row = jTable1.getSelectedRow();
        
        if (row != -1) {
            String pid = String.valueOf(jTable1.getValueAt(row, 0)); // Product ID

            query += "WHERE `stock`.`product_id` = '" + pid + "' ";
        }
        
        // Additional filtering logic (price range, sorting)
        if (query.contains("WHERE")) {
            query += "AND ";
        } else {
            query += "WHERE ";
        }

        double min_price = 0;
        double max_price = 0;

        if (!jFormattedTextField1.getText().isEmpty()) {
            min_price = Double.parseDouble(jFormattedTextField1.getText());
        }

        if (!jFormattedTextField2.getText().isEmpty()) {
            max_price = Double.parseDouble(jFormattedTextField2.getText());
        }

        if (min_price > 0 && max_price == 0) {
            query += "`stock`.`price` > '" + min_price + "' ";
        } else if (min_price == 0 && max_price > 0) {
            query += "`stock`.`price` < '" + max_price + "' ";
        } else if (min_price > 0 && max_price > 0) {
            query += "`stock`.`price` > '" + min_price + "' AND `stock`.`price` < '" + max_price + "' ";
        }

        String sort = String.valueOf(jComboBox3.getSelectedItem());

        query += "ORDER BY ";
        query = query.replace("WHERE ORDER BY ", "ORDER BY ");
        query = query.replace("AND ORDER BY ", "ORDER BY ");

        if (sort.equals("Stock ID ASC")) {
            query += "`stock`.`id` ASC";
        } else if (sort.equals("Stock ID DESC")) {
            query += "`stock`.`id` DESC";
        } else if (sort.equals("Product ID ASC")) {
            query += "`product`.`id` ASC";
        } else if (sort.equals("Product ID DESC")) {
            query += "`product`.`id` DESC";
        } else if (sort.equals("Brand ASC")) {
            query += "`brand`.`name` ASC";
        } else if (sort.equals("Brand DESC")) {
            query += "`brand`.`name` DESC";
        } else if (sort.equals("Name ASC")) {
            query += "`product`.`name` ASC";
        } else if (sort.equals("Name DESC")) {
            query += "`product`.`name` DESC";
        } else if (sort.equals("Selling Price ASC")) {
            query += "`stock`.`price` ASC";
        } else if (sort.equals("Selling Price DESC")) {
            query += "`stock`.`price` DESC";
        } else if (sort.equals("Quantity ASC")) {
            query += "`stock`.`qty` ASC";
        } else if (sort.equals("Quantity DESC")) {
            query += "`stock`.`qty` DESC";
        }

        ResultSet resultSet = MySQL21.executeSearch(query);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        while (resultSet.next()) {
            Vector<String> vector = new Vector<>();
            vector.add(resultSet.getString("stock.id"));
            vector.add(resultSet.getString("product.id"));
            vector.add(resultSet.getString("brand.id"));
            vector.add(resultSet.getString("brand.name"));
            vector.add(resultSet.getString("product.name"));
            vector.add(resultSet.getString("stock.price"));
            vector.add(resultSet.getString("size.name"));
            vector.add(resultSet.getString("qty"));
            model.addRow(vector);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        customerManagementButton = new javax.swing.JButton();
        employeeManagementButton = new javax.swing.JButton();
        grnManagementButton = new javax.swing.JButton();
        orderManagementButton = new javax.swing.JButton();
        supplierManagementButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        stockReportButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        productReportButton = new javax.swing.JButton();

        jMenuItem1.setText("Delete Product");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Registration");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jButton6.setBackground(new java.awt.Color(51, 153, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-log-out-30.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton7.setText("Home");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        customerManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        customerManagementButton.setText("Customer Management");
        customerManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerManagementButtonActionPerformed(evt);
            }
        });

        employeeManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        employeeManagementButton.setText("Employee Management");
        employeeManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeManagementButtonActionPerformed(evt);
            }
        });

        grnManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        grnManagementButton.setText("GRN Management");
        grnManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grnManagementButtonActionPerformed(evt);
            }
        });

        orderManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        orderManagementButton.setText("Order Management");
        orderManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderManagementButtonActionPerformed(evt);
            }
        });

        supplierManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        supplierManagementButton.setText("Supplier Management");
        supplierManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierManagementButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Product & Stock");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton6)
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(customerManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(grnManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(orderManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierManagementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(customerManagementButton)
                .addGap(18, 18, 18)
                .addComponent(employeeManagementButton)
                .addGap(18, 18, 18)
                .addComponent(grnManagementButton)
                .addGap(18, 18, 18)
                .addComponent(orderManagementButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(supplierManagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Brand", "Category", "Color", "Material", "Type", "Fashion Season"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel14.setText("Product ID :");

        jTextField5.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel10.setText("Product  Name :");

        jTextField1.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel27.setText("Product Category");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-update-20.png"))); // NOI18N
        jButton3.setText("Update Product ");
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-update-20 (1).png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton3KeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel30.setText("Fashion Season :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel31.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel31.setText("Product Brand :");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel32.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel32.setText("Product Color :");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel33.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel33.setText("Product Material :");

        jLabel34.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel34.setText("Product Type :");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton10.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton10.setText("Add Product");
        jButton10.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-clear-20.png"))); // NOI18N
        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jComboBox6, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel33)
                                .addGap(19, 19, 19)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stock ID ASC", "Stock ID DESC", "Product ID ASC", "Product ID DESC", "Brand ASC", "Brand DESC", "Name ASC", "Name DESC", "Selling Price ASC", "Selling Price DESC", "Quantity ASC", "Quantity DESC" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel11.setText("Sort By:");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel12.setText("Selling Price :");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setText("0");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel15.setText("To");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setText("0");

        jButton5.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton5.setText("Find");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Product Id", "Brand ID", "Brand", "Product Name", "Selling Price", "Size", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        stockReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-pdf-30.png"))); // NOI18N
        stockReportButton.setText("Print Stock Report");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
                        .addComponent(stockReportButton)))
                .addGap(26, 26, 26))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(stockReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Add & Update", jPanel4);

        jLabel35.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel35.setText("Manage  Category");

        jTextField4.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);

        jButton2.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton2.setText("Add");
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel36.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel36.setText("Manage  Sizes");

        jTextField10.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField10KeyReleased(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton8.setText("Add");
        jButton8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel36)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel37.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel37.setText("Manage  Brands");

        jTextField11.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton11.setText("Add");
        jButton11.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brands"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel38.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel38.setText("Manage  Materials");

        jTextField12.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField12KeyReleased(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton13.setText("Add");
        jButton13.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Material"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(jTable6);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton13))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addGap(9, 9, 9)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel39.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel39.setText("Manage  Fashion Seasons");

        jTextField13.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton15.setText("Add");
        jButton15.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Seasons"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable7.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable7);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15))
                    .addComponent(jLabel39)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jLabel40.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel40.setText("Manage  Colors");

        jTextField14.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton18.setText("Add");
        jButton18.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Color"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable8.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jTable8);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jButton18))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jTabbedPane1.addTab("Add contents", jPanel5);

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel9.setText("PRODUCT MANAGEMENT");

        productReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-pdf-30.png"))); // NOI18N
        productReportButton.setText("Print Product Report");
        productReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productReportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
                .addComponent(productReportButton)
                .addGap(37, 37, 37))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(productReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (home != null) {
            home.setVisible(true);
        }
        this.dispose();
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         String id = jTextField5.getText();
        String name = jTextField1.getText();
        String brand = String.valueOf(jComboBox5.getSelectedItem());
        String category = String.valueOf(jComboBox2.getSelectedItem());
        String color = String.valueOf(jComboBox6.getSelectedItem());
        String material = String.valueOf(jComboBox7.getSelectedItem());
        String type = String.valueOf(jComboBox8.getSelectedItem());
        String fseason = String.valueOf(jComboBox4.getSelectedItem());

        if (brand.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a brand", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (category.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a category", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (color.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a color", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (material.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a material", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (type.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a type", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (fseason.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a fashion season", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                // Fetch the current values
                ResultSet currentResultSet = MySQL21.executeSearch("SELECT * FROM `product` WHERE `id` = '" + id + "'");
                if (currentResultSet.next()) {
                    String currentName = currentResultSet.getString("name");
                    String currentBrandId = currentResultSet.getString("brand_id");
                    String currentCategoryId = currentResultSet.getString("product_category_id");
                    String currentColorId = currentResultSet.getString("color_id");
                    String currentMaterialId = currentResultSet.getString("material_id");
                    String currentTypeId = currentResultSet.getString("clothing_gender_id");
                    String currentFseasonId = currentResultSet.getString("fashion_season_id");

                    // Check if any value has changed
                    boolean hasChanges = !name.equals(currentName) ||
                                         !brandMap.get(brand).equals(currentBrandId) ||
                                         !categoryMap.get(category).equals(currentCategoryId) ||
                                         !colorMap.get(color).equals(currentColorId) ||
                                         !materialMap.get(material).equals(currentMaterialId) ||
                                         !typeMap.get(type).equals(currentTypeId) ||
                                         !fashionseasonMap.get(fseason).equals(currentFseasonId);

                    if (hasChanges) {
                        MySQL21.executeIUD("UPDATE `product` SET `brand_id` = '" + brandMap.get(brand) + "', "
                                + "`product_category_id` = '" + categoryMap.get(category) + "', "
                                + "`color_id` = '" + colorMap.get(color) + "', "
                                + "`material_id` = '" + materialMap.get(material) + "', `clothing_gender_id` = '" + typeMap.get(type) + "', "
                                + "`fashion_season_id` = '" + fashionseasonMap.get(fseason) + "', `name` = '" + name + "' "
                                + "WHERE `id` = '" + id + "'");
                        loadProduct();
                        reset();
                        JOptionPane.showMessageDialog(this, "Product Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "No changes detected", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jButton3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyReleased
        

    }//GEN-LAST:event_jButton3KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();

            if (row != -1) { // Ensure a row is selected
            // Set fields with table values
            jTextField5.setText(String.valueOf(jTable1.getValueAt(row, 0)));
            jTextField5.setEditable(false);

            jTextField1.setText(String.valueOf(jTable1.getValueAt(row, 1)));
            jComboBox5.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 2)));
            jComboBox2.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 3)));
            jComboBox6.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 4)));
            jComboBox7.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 5)));
            jComboBox8.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 6)));
            jComboBox4.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 7)));

            // Disable the Add button since you're editing an existing row
            jButton10.setEnabled(false); 
        }
            
        loadStock();
        
        if (evt.getClickCount() == 2) {
            if (gRNManagement != null) {
                gRNManagement.getjTextField4().setText(String.valueOf(jTable1.getValueAt(row, 0)));
                gRNManagement.getjLabel18().setText(String.valueOf(jTable1.getValueAt(row, 1)));
                gRNManagement.getjLabel14().setText(String.valueOf(jTable1.getValueAt(row, 2)));
                this.dispose();
                
            }
        }
            
         //Menu item
        if (evt.getButton() == MouseEvent.BUTTON3 && row >= 0 && row < jTable1.getRowCount()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jTextField10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12KeyReleased

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String id = jTextField5.getText();
        String name = jTextField1.getText();
        String brand =String.valueOf(jComboBox5.getSelectedItem());
        String category =String.valueOf(jComboBox2.getSelectedItem());
        String color =String.valueOf(jComboBox6.getSelectedItem());
        String material =String.valueOf(jComboBox7.getSelectedItem());
        String type =String.valueOf(jComboBox8.getSelectedItem());
        String fseason =String.valueOf(jComboBox4.getSelectedItem());
        
            if (id.isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Please product id", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter product name", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (brand.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a product brand", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (category.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a product category", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (color.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a product color", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (material.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a product material", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (type.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a product type", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (fseason.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a fashion season", "Warning", JOptionPane.WARNING_MESSAGE);
            }else{
                
                try {
                    // Check if product with the same ID already exists
                    ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `product` WHERE `id` = '" + id + "'");

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(this, "Product with this ID already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Insert new product into the database
                        MySQL21.executeIUD("INSERT INTO `product`(`id`, `name`, `brand_id`, `product_category_id`, "
                                + "`color_id`, `material_id`, `clothing_gender_id`, `fashion_season_id`) VALUES ('"
                                + id + "','" + name + "','" + brandMap.get(brand) + "','" + categoryMap.get(category) + "','" + colorMap.get(color) + "','" + materialMap.get(material) + "','"
                                + typeMap.get(type) + "','" + fashionseasonMap.get(fseason) + "')");

                        loadProduct();
                        reset(); 
                        JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
            }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       String category = jTextField4.getText();

        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter category name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `product_category` WHERE `category_name`='" + category + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product Category already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL21.executeIUD("INSERT INTO `product_category`(`category_name`) VALUES('" + category + "')");
                    JOptionPane.showMessageDialog(this, "New category added", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadCategory();  
                    jTextField4.setText("");  
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
                
        String size = jTextField10.getText();

        if (size.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product size", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `size` WHERE `name`='" + size + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product size already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL21.executeIUD("INSERT INTO `size`(`name`) VALUES('" + size + "')");
                    JOptionPane.showMessageDialog(this, "New product size added", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadSize();  
                    jTextField10.setText("");  
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String brand = jTextField11.getText();

        if (brand.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product brand", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `brand` WHERE `name`='" + brand + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product brand already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL21.executeIUD("INSERT INTO `brand`(`name`) VALUES('" + brand + "')");
                    JOptionPane.showMessageDialog(this, "New product brand added", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadBrand();  
                    jTextField11.setText("");  
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
       String color = jTextField14.getText();

        if (color.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product color", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `color` WHERE `name`='" + color + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product color already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL21.executeIUD("INSERT INTO `color`(`name`) VALUES('" + color + "')");
                    JOptionPane.showMessageDialog(this, "New product color added", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadColor();  
                    jTextField14.setText("");  
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String material = jTextField12.getText();

        if (material.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product material", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `material` WHERE `name`='" + material + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product material already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL21.executeIUD("INSERT INTO `material`(`name`) VALUES('" + material + "')");
                    JOptionPane.showMessageDialog(this, "New product material added", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadMaterial();  
                    jTextField12.setText("");  
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        String season = jTextField13.getText();

        if (season.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter fashion season", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `fashion_season` WHERE `name`='" + season + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Fashion season already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL21.executeIUD("INSERT INTO `fashion_season`(`name`) VALUES('" + season + "')");
                    JOptionPane.showMessageDialog(this, "New fashion season added", "Success", JOptionPane.INFORMATION_MESSAGE);

                    loadSeason();  
                    jTextField13.setText("");  
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        
        int row = jTable2.getSelectedRow();
        
        if (evt.getClickCount() == 2) {
            if (orderManagement != null) {
                String stockID = String.valueOf(jTable2.getValueAt(row, 0));
                String brandID = String.valueOf(jTable2.getValueAt(row, 2)); 
                String brandName = String.valueOf(jTable2.getValueAt(row, 3)); 
                String productName = String.valueOf(jTable2.getValueAt(row, 4));
                String size = String.valueOf(jTable2.getValueAt(row, 6));
                String availableQty = String.valueOf(jTable2.getValueAt(row, 7)); // Changed this to reflect available qty
                String sellingPrice = String.valueOf(jTable2.getValueAt(row, 5));

                // Set only the necessary fields
                orderManagement.getjTextField3().setText(stockID);
                orderManagement.getjLabel33().setText(brandID); 
                orderManagement.getjLabel19().setText(brandName); 
                orderManagement.getjLabel22().setText(productName);
                orderManagement.getjTextField6().setText(sellingPrice);
                orderManagement.getjLabel30().setText(size);
                orderManagement.getjFormattedTextField1().setText(availableQty); // Just display available qty for reference
                orderManagement.getjFormattedTextField2().setText(""); // Clear the quantity input field
                orderManagement.getjFormattedTextField2().grabFocus(); // Set focus to quantity input
                this.dispose();
            }
        }

        
    }//GEN-LAST:event_jTable2MouseClicked

    private void productReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productReportButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productReportButtonActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        loadStock();
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        reset();
        loadStock();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadStock();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void customerManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerManagementButtonActionPerformed
        CustomerRegistration custreg = new CustomerRegistration(home, employeeType);
        custreg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_customerManagementButtonActionPerformed

    private void employeeManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeManagementButtonActionPerformed

        EmployeeRegistration employreg = new EmployeeRegistration(home, employeeType);
        employreg.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_employeeManagementButtonActionPerformed

    private void grnManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grnManagementButtonActionPerformed
        GRNManagement grnman = new GRNManagement(home, employeeType);
        grnman.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_grnManagementButtonActionPerformed

    private void orderManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderManagementButtonActionPerformed
        OrderManagement orderman = new OrderManagement(home, employeeType);
        orderman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_orderManagementButtonActionPerformed

    private void supplierManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierManagementButtonActionPerformed
        SupplierManagement suppman = new SupplierManagement(home, employeeType);
        suppman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_supplierManagementButtonActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        SignInWindown signin = new SignInWindown();
        signin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int row = jTable1.getSelectedRow();
        if (row != -1) {

            String productId = String.valueOf(jTable1.getValueAt(row, 0)); // Assuming the first column has the product ID

            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?",
                "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                try {
                    MySQL21.executeIUD("DELETE FROM `product` WHERE `id` = '" + productId + "'");

                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.removeRow(row);

                    JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProduct();
                    reset();

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting product. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerManagementButton;
    private javax.swing.JButton employeeManagementButton;
    private javax.swing.JButton grnManagementButton;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton orderManagementButton;
    private javax.swing.JButton productReportButton;
    private javax.swing.JButton stockReportButton;
    private javax.swing.JButton supplierManagementButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        jTextField5.setText("");
        jTextField1.setText(""); 
        jComboBox5.setSelectedIndex(0); 
        jComboBox2.setSelectedIndex(0); 
        jComboBox6.setSelectedIndex(0); 
        jComboBox7.setSelectedIndex(0); 
        jComboBox8.setSelectedIndex(0); 
        jComboBox4.setSelectedIndex(0); 
        jTextField5.grabFocus();
        jTable1.clearSelection();
        
        jButton10.setEnabled(true);
        jTextField5.setEditable(true);
    }

    

    
}
