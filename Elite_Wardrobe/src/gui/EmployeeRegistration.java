/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import model.MySQL21;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dracula
 */
public class EmployeeRegistration extends javax.swing.JFrame {

    private Home home;
    
    private String employeeType;
    
    private static HashMap<String,String> employeeTypeMap = new HashMap<>();
    private static HashMap<String,String> employeeGenderMap = new HashMap<>();
    
     public static Logger logger = Logger.getLogger("elite");
    
    public EmployeeRegistration(Home home, String employeeType) {
        initComponents();
        try {
            FileHandler fileHandler = new FileHandler("elite.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadTypes();
        loadGender();
        loadEmployees(jTextField2.getText());
        
        this.home = home;
        this.employeeType = employeeType;
        setButtonAccess();
    }

    private void loadTypes(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `employee_type`");
            
            Vector<String> vector = new Vector<>();
            vector.add("Select");
            
            while(resultSet.next()){
                vector.add(resultSet.getString("name"));
                employeeTypeMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);
            
        } catch (Exception e) {
           logger.log(Level.SEVERE, "Failed to load types data from database.");
        }
        
    }
    
    private void loadGender(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `gender`");
            
            Vector<String> vector = new Vector<>();
            vector.add("Select");
            
            while(resultSet.next()){
                vector.add(resultSet.getString("name"));
                employeeGenderMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load gender data from database.");
        }
        
    }
    
    public void loadEmployees(String first_name){
    
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `employee` "
                    + "INNER JOIN `employee_type` ON `employee`.`employee_type_id` = `employee_type`.`id`"
                    + "INNER JOIN `gender` ON `employee`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `employee_details` ON `employee`.`email` = `employee_details`.`employee_email`"
                    + "WHERE `first_name` LIKE '"+first_name+"%'");
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.setRowCount(0);
            
            while(resultSet.next()){
                
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("nic"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("password"));
                vector.add(resultSet.getString("employee_details.birthday"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("employee_type.name"));
                
                model.addRow(vector);
                
            }
            
        } catch (Exception e) {
             logger.log(Level.SEVERE, "Failed to load employee data from database.");
        }
        
    }
    
    public void setButtonAccess() {
        if (employeeType.equalsIgnoreCase("cashier")) {
            productManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("stores")) {
            customerManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("hr manager")) {
            customerManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);   
            
            employeeReportButton.setEnabled(false);
            
        } else if (employeeType.equalsIgnoreCase("sales associate")) {
            productManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);              
        }
        // Admin has access to all, so no need to disable any buttons
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
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        grnManagementButton = new javax.swing.JButton();
        orderManagementButton = new javax.swing.JButton();
        productManagementButton = new javax.swing.JButton();
        supplierManagementButton = new javax.swing.JButton();
        customerManagementButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        employeeReportButton = new javax.swing.JButton();

        jMenuItem1.setText("Delete Employee");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Employee Report");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Registration");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Employee Management");

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

        productManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        productManagementButton.setText("Product & Stock");
        productManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productManagementButtonActionPerformed(evt);
            }
        });

        supplierManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        supplierManagementButton.setText("Supplier Management");
        supplierManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierManagementButtonActionPerformed(evt);
            }
        });

        customerManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        customerManagementButton.setText("Customer Management");
        customerManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerManagementButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(supplierManagementButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(orderManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(grnManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerManagementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(customerManagementButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(grnManagementButton)
                .addGap(18, 18, 18)
                .addComponent(orderManagementButton)
                .addGap(18, 18, 18)
                .addComponent(productManagementButton)
                .addGap(18, 18, 18)
                .addComponent(supplierManagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel9.setText("EMPLOYEE MANAGEMENT");

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel10.setText("Email");

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel11.setText("First Name :");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel12.setText("Last Name :");

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel13.setText("NIC :");

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel14.setText("Mobile :");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel15.setText("Password :");

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel16.setText("Gender");

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel17.setText("Type");

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel18.setText("Birthday :");

        jTextField1.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jPasswordField1.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox1KeyReleased(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox2KeyReleased(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton2.setText("Add Account");
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-update-20.png"))); // NOI18N
        jButton3.setText("Update ");
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-update-20 (1).png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "First Name", "Last Name", "NIC", "Mobile", "Password", "Birthday", "Gender", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );

        employeeReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-pdf-30.png"))); // NOI18N
        employeeReportButton.setText("Print Employees Report");
        employeeReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeReportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(employeeReportButton)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(148, 148, 148)
                                .addComponent(jButton2))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employeeReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyReleased
        
    }//GEN-LAST:event_jComboBox2KeyReleased

    private void jComboBox1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyReleased
        if (evt.getKeyCode() == 10) {
            jComboBox2.grabFocus();
        }
    }//GEN-LAST:event_jComboBox1KeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        if (evt.getKeyCode() == 10) {
            jPasswordField1.grabFocus();
        }
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        if (evt.getKeyCode() == 10) {
            jTextField5.grabFocus();
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        if (evt.getKeyCode() == 10) {
            jTextField4.grabFocus();
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        loadEmployees(jTextField2.getText());
        
        if (evt.getKeyCode() == 10) {
            jTextField3.grabFocus();
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (evt.getKeyCode() == 10) {
            jTextField2.grabFocus();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        AddEmployee addEmployee = new AddEmployee(this);
        addEmployee.setVisible(true);
        
        loadEmployees(jTextField2.getText());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (home != null) {
            home.setVisible(true);
        }
        this.dispose();
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTextField1.setEditable(true);
        jTable1.clearSelection();
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        int row = jTable1.rowAtPoint(evt.getPoint()); // Get the row at the mouse pointer
        if (row >= 0 && row < jTable1.getRowCount()) {
            jTable1.setRowSelectionInterval(row, row); // Select the row at the mouse pointer

            String email = String.valueOf(jTable1.getValueAt(row, 0));
            jTextField1.setText(email);
            jTextField1.setEditable(false);

            String firstName = String.valueOf(jTable1.getValueAt(row, 1));
            jTextField2.setText(firstName);

            String lastName = String.valueOf(jTable1.getValueAt(row, 2));
            jTextField3.setText(lastName);

            String nic = String.valueOf(jTable1.getValueAt(row, 3));
            jTextField4.setText(nic);

            String mobile = String.valueOf(jTable1.getValueAt(row, 4));
            jTextField5.setText(mobile);

            String password = String.valueOf(jTable1.getValueAt(row, 5));
            jPasswordField1.setText(password);

            String birthday = String.valueOf(jTable1.getValueAt(row, 6));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date birthDate = dateFormat.parse(birthday);
                jDateChooser1.setDate(birthDate);
            } catch (ParseException e) {
                logger.log(Level.SEVERE, "Date parsing failed for input:");
            }   

            String gender = String.valueOf(jTable1.getValueAt(row, 7));
            jComboBox1.setSelectedItem(gender);

            String type = String.valueOf(jTable1.getValueAt(row, 8));
            jComboBox2.setSelectedItem(type); 
        }

        //Menu item
        if (evt.getButton() == MouseEvent.BUTTON3 && row >= 0 && row < jTable1.getRowCount()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        
        //Address View
        if (evt.getClickCount() == 2) {
            
            int row1 =  jTable1.getSelectedRow();
            String email = String.valueOf(jTable1.getValueAt(row1, 0));
            
            AddressView addressView = new AddressView(this,true,email);
            addressView.setVisible(true);
        }
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        try {

            String firstName = jTextField2.getText();
            String lastName = jTextField3.getText();
            String nic = jTextField4.getText();
            String mobile = jTextField5.getText();
            Date birthday = jDateChooser1.getDate();
            String password = String.valueOf(jPasswordField1.getPassword());
            String gender =String.valueOf(jComboBox1.getSelectedItem());
            String type =String.valueOf(jComboBox2.getSelectedItem());
            
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            String email = jTextField1.getText();
            
            if (firstName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your first name", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your last name", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your nic", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your mobile", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (!mobile.matches("^07[01245678]{1}[0-9]{7}$")) {
                JOptionPane.showMessageDialog(this, "Please enter valid mobile number", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (birthday == null) {
                 JOptionPane.showMessageDialog(this, "Please select a valid birthday", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your password", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-]).{6,}$")) {
                JOptionPane.showMessageDialog(this, "Please enter minimum eight characters, at least one letter, one number and one special character ", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (gender.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a gender", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (type.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a type", "Warning", JOptionPane.WARNING_MESSAGE);
            }else{
                
                ResultSet resultSet =  MySQL21.executeSearch("SELECT * FROM `employee` WHERE `nic` = '"+nic+"' OR `mobile` = '"+mobile+"'");
                
                boolean canUpdate = false;
                
                if (resultSet.next()) {
                    
                    if (!resultSet.getString("email").equals(email)) {
                        JOptionPane.showMessageDialog(this, "This Mobile number or NIC already used", "Warning", JOptionPane.WARNING_MESSAGE);
                    }else{
                        canUpdate = true;
                    }
                                        
                }else{
                    canUpdate = true;
                    
                }
                
                if (canUpdate) {
                    
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this employee?", "Confirm Update", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) {
                        return; // If the user selects "No", stop the update process.
                    }

                    
                    int rowsAffected = MySQL21.executeIUD("UPDATE `employee` SET `password` = '"+password+"', `first_name` = '"+firstName+"', `last_name` = '"+lastName+"', "
                            + "`nic` = '"+nic+"', `mobile` = '"+mobile+"',`employee_type_id` = '"+employeeTypeMap.get(type)+"', "
                            + "`gender_id` = '"+employeeGenderMap.get(gender)+"'"
                            + " WHERE `email` = '"+email+"'");
                    
                    if (rowsAffected > 0) {
                        
                        MySQL21.executeIUD("UPDATE `employee_details` SET `birthday` = '" + sdf.format(birthday) + "' WHERE `employee_email` = '" + email + "'");
                        JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                    
                    loadEmployees(jTextField2.getText());
                    reset();
                    
                }
            
            }
            
        } catch (Exception e) {
            logger.log(Level.WARNING,"Error updating employee!");
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
                      
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            
            String email = String.valueOf(jTable1.getValueAt(row, 0));

            
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", 
                                                         "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                try {
                    
                    MySQL21.executeIUD("DELETE FROM `employee_details` WHERE `employee_email` = '" + email + "'");
                    MySQL21.executeIUD("DELETE FROM `employee` WHERE `email` = '" + email + "'");
                    
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.removeRow(row);

                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadEmployees(jTextField2.getText());
                    reset();

                } catch (Exception e) {
                    logger.log(Level.WARNING,"Error deleting employee!");
                    JOptionPane.showMessageDialog(this, "Please select an employee to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void employeeReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeReportButtonActionPerformed
       
        String path = "src//reports//elite-wardrobe(userreport).jasper";
        
        HashMap<String,Object> params = new HashMap<>();
        params.put("Parameter1", new Date());
        
        try {   
                    
        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
        
        JasperViewer.viewReport(jasperPrint,false);
        
        } catch (JRException e) {
            logger.log(Level.SEVERE, "Error occurred while generating the report.");
        }
        
    }//GEN-LAST:event_employeeReportButtonActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      
        int selectedRow = jTable1.getSelectedRow(); 
        if (selectedRow != -1) { 
            
            String email = String.valueOf(jTable1.getValueAt(selectedRow, 0));

            
            HashMap<String, Object> params = new HashMap<>();
            params.put("employee_email", email); 

            
            String path = "src//reports//elite_w(singleuserreport).jasper"; 

            try {
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite_wardrobe", "root", "123789jklHIM@");
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(path, params, connection);

                JasperViewer.viewReport(jasperPrint, false);


            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error occurred.", e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee from the table.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void supplierManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierManagementButtonActionPerformed
        SupplierManagement suppman = new SupplierManagement(home, employeeType);
        suppman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_supplierManagementButtonActionPerformed

    private void productManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productManagementButtonActionPerformed
        ProductManagement proman = new ProductManagement(home, employeeType);
        proman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_productManagementButtonActionPerformed

    private void orderManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderManagementButtonActionPerformed
        OrderManagement orderman = new OrderManagement(home, employeeType);
        orderman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_orderManagementButtonActionPerformed

    private void grnManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grnManagementButtonActionPerformed
        GRNManagement grnman = new GRNManagement(home, employeeType);
        grnman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_grnManagementButtonActionPerformed

    private void customerManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerManagementButtonActionPerformed
        CustomerRegistration custreg = new CustomerRegistration(home, employeeType);
        custreg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_customerManagementButtonActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        SignInWindown signin = new SignInWindown();
        signin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerManagementButton;
    private javax.swing.JButton employeeReportButton;
    private javax.swing.JButton grnManagementButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton orderManagementButton;
    private javax.swing.JButton productManagementButton;
    private javax.swing.JButton supplierManagementButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jPasswordField1.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jDateChooser1.setDate(null);
        
        jTextField1.setEditable(true);
    }
}
