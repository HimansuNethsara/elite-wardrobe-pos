/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
public class OrderManagement extends javax.swing.JFrame {

    private Home home;
    
     public static Logger logger = Logger.getLogger("elite");
     
     private String employeeType; 
     
    HashMap<String, InvoiceItem> invoiceItemMap = new HashMap<>();
    HashMap<String, String> paymentMethodMap = new HashMap<>();
    
    public HashMap<String, InvoiceItem> getInvoiceItemMap() {
        return invoiceItemMap;
    }
    
    //customer mobile 
    public JTextField getjTextField2(){
        return customerMobileField;
    }
    
    public JLabel getjLabe1l6(){
        return jLabel16;
    }
    
    //stock id
    public JTextField getjTextField3() {
        return jTextField3;
    }

    // Brand Name
    public JLabel getjLabel19() {
        return jLabel19;
    }
    
    // Brand ID
    public JLabel getjLabel33() {
        return jLabel33;
    }

    // Product Name
    public JLabel getjLabel22() {
        return jLabel22;
    }

    //Selling Price 
    public JTextField getjTextField6() {
        return jTextField6;
    }

    //Available QTY
    public JFormattedTextField getjFormattedTextField1() {
        return jFormattedTextField1;
    }
    
    //Selling Price 
    public JLabel getjLabel30() {
        return jLabel30;
    }
    
    //QTY
    public JFormattedTextField getjFormattedTextField2() {
        return jFormattedTextField2;
    }
    
    // Available Points
    public JTextField getjTextField4() {
        return jTextField4;
    }
    
    public OrderManagement(Home home, String employeeType) {
        initComponents();
        try {
            FileHandler fileHandler = new FileHandler("elite.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.home = home;
        this.employeeType = employeeType;
        
        employeeEmailLabel.setText(SignInWindown.getEmployeeEmail());
        generateInvoiceId();
        loadPaymentMethods();
        loadInvoiceItems();
        
        setButtonAccess();
        
    }

    private void generateInvoiceId() {
        String prefix = "INV";
        String date = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        long timestamp = System.currentTimeMillis() % 1000; // Last 3 digits of the timestamp
        int randomNum = (int) (Math.random() * 1000); // Random number between 0 and 999
        
        String invoiceId = prefix + date + String.format("%03d%03d", timestamp, randomNum);
        invoiceNumberField.setText(invoiceId);        
    }

    public void setButtonAccess() {
        if (employeeType.equalsIgnoreCase("cashier")) {
            employeeManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("stores")) {
            customerManagementButton.setEnabled(false);
            employeeManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("hr manager")) {
            customerManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);            
        } else if (employeeType.equalsIgnoreCase("sales associate")) {
            employeeManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
            grnManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);              
        }
        // Admin has access to all, so no need to disable any buttons
    }
    
    private void loadPaymentMethods() {

        try {

            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `payment_method`");

            Vector<String> vector = new Vector<>();

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                paymentMethodMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadInvoiceItems() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        double total = 0;

        for (InvoiceItem invoiceItem : invoiceItemMap.values()) {
            Vector<String> vector = new Vector<>();
            vector.add(invoiceItem.getStockID());
            vector.add(invoiceItem.getBrandID());
            vector.add(invoiceItem.getBrand());
            vector.add(invoiceItem.getName());
            vector.add(invoiceItem.getSize());
            vector.add(invoiceItem.getQty());
            vector.add(invoiceItem.getSellingPrice());

            double itemTotal = Double.parseDouble(invoiceItem.getQty()) * Double.parseDouble(invoiceItem.getSellingPrice());
            total += itemTotal;
            vector.add(String.valueOf(itemTotal));

            model.addRow(vector);
        }

        TotalField.setText(String.valueOf(total));
        
        //2
        calculate();
    }
    
    private double total = 0;
    private double discount = 0;
    private double payment = 0;
    private boolean withdrawPoints = false;
    private double balance = 0;
    private String paymentMethod = "Select";
    private double newPoints = 0;


    private double calculatePoints(double purchaseAmount) {
        double points = 0;

         if (purchaseAmount >= 20000 && purchaseAmount <= 49999) {
            points = 1; 
        } else if (purchaseAmount >= 50000 && purchaseAmount <= 249999) {
            points = 2; 
        } else if (purchaseAmount >= 250000 && purchaseAmount <= 499999) {
            points = 3; 
        } else if (purchaseAmount >= 500000) {
            points = 4; 
        }

        return points;
    }

    private void calculate() {

        // Parse payment
        if (paymentField.getText().isEmpty()) {
            payment = 0;
        } else {
            payment = Double.parseDouble(paymentField.getText());
        }

        // Parse total
        total = 0;
        discount = 0;

        for (InvoiceItem invoiceItem : invoiceItemMap.values()) {
            double itemPrice = Double.parseDouble(invoiceItem.getSellingPrice());
            double itemQty = Double.parseDouble(invoiceItem.getQty());
            double itemTotal = itemPrice * itemQty;

            total += itemTotal;

            String brandID = invoiceItem.getBrandID();
            String query = "SELECT discount_value, discount_type.name as discount_type_name FROM discount "
                         + "JOIN discount_type ON discount.discount_type_id = discount_type.id "
                         + "WHERE brand_id = '" + brandID + "'";

            try {
                ResultSet resultSet = MySQL21.executeSearch(query);
                if (resultSet.next()) {
                    double discountValue = resultSet.getDouble("discount_value");
                    String discountType = resultSet.getString("discount_type_name");

                    double itemDiscount = 0;

                    if (discountType.equalsIgnoreCase("percentage")) {
                        itemDiscount = itemTotal * (discountValue / 100);
                    } else if (discountType.equalsIgnoreCase("fixed")) {
                        itemDiscount = discountValue;
                    }

                    discount += itemDiscount;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Deduct total discount from total price
        total -= discount;
        discountField.setText(String.valueOf(discount));

        // Check if withdrawing points
        withdrawPoints = jCheckBox1.isSelected();

        // Get selected payment method
        paymentMethod = String.valueOf(jComboBox1.getSelectedItem());
        
        double customerPoints = 0;
        if (!jTextField4.getText().isEmpty()) {
            customerPoints = Double.parseDouble(jTextField4.getText());
        }

        if (total < 0) {
            // Handle error for negative total
        } else {
            // Handle points usage if selected
            if (withdrawPoints) {
                double pointsValue = customerPoints * 2000;
                double remainingAmount = total - payment; // Remaining amount to be covered by points
                
                double requiredPoints = remainingAmount / 2000;
                
                if (requiredPoints <= customerPoints) {
                    total -= requiredPoints * 2000; 
                    newPoints = requiredPoints; 
                } else {
                    total -= pointsValue; 
                    newPoints = customerPoints; 
                }

                // Ensure total doesn't go below zero
                if (total < 0) {
                    total = 0;
                }
            } else {
                // Calculate points based on total after discount
                newPoints = calculatePoints(total);  
            }
        }

        // Handle payment method (Cash vs. Card)
        if (paymentMethod.equals("Cash")) {
            paymentField.setEditable(true);
            balance = payment - total;

            if (balance < 0) {
                printInvoiceButton.setEnabled(false);
            } else {
                printInvoiceButton.setEnabled(true);
            }

        } else {
            // Card payment method
            payment = total;
            balance = 0;
            paymentField.setText(String.valueOf(payment));
            paymentField.setEditable(false);
            printInvoiceButton.setEnabled(true);
        }

        // Update balance field
        balanceField.setText(String.valueOf(balance));
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
        employeeEmailLabel = new javax.swing.JLabel();
        customerManagementButton = new javax.swing.JButton();
        employeeManagementButton = new javax.swing.JButton();
        grnManagementButton = new javax.swing.JButton();
        productManagementButton = new javax.swing.JButton();
        supplierManagementButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        invoiceNumberField = new javax.swing.JTextField();
        customerMobileField = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        TotalField = new javax.swing.JFormattedTextField();
        jLabel25 = new javax.swing.JLabel();
        discountField = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        paymentField = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        balanceField = new javax.swing.JFormattedTextField();
        printInvoiceButton = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        jMenuItem1.setText("Delete Employee");
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

        employeeEmailLabel.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        employeeEmailLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        employeeEmailLabel.setText("Employee Email Here");

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

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Order Management");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jButton6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(employeeEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(customerManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(grnManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierManagementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(employeeEmailLabel)
                .addGap(64, 64, 64)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(customerManagementButton)
                .addGap(18, 18, 18)
                .addComponent(employeeManagementButton)
                .addGap(18, 18, 18)
                .addComponent(grnManagementButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(productManagementButton)
                .addGap(18, 18, 18)
                .addComponent(supplierManagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel11.setText("Customer  :");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel13.setText("Stock :");

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel14.setText("Brand Name :");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel15.setText("Available Quantity :");

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel18.setText("Invoice No :");

        invoiceNumberField.setEditable(false);
        invoiceNumberField.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N

        customerMobileField.setEditable(false);
        customerMobileField.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N

        jTextField3.setEditable(false);
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

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton2.setText("Add to Invoice");
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20 (1).png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                "Stock ID", "BrandID", "Brand", "Name", "Size", "Quantity", "Selling Price", "Total"
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton1.setText("SELECT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton3.setText("SELECT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel19.setText("Brand Name Here");

        jLabel20.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel20.setText("Available Points");

        jLabel21.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel21.setText("Product Name :");

        jLabel22.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel22.setText("Product Name Here");

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel23.setText("Price");

        jLabel24.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Total");

        TotalField.setEditable(false);
        TotalField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        TotalField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalField.setText("0");
        TotalField.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        TotalField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TotalFieldKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Discount");

        discountField.setEditable(false);
        discountField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        discountField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        discountField.setText("0");
        discountField.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        discountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                discountFieldKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Payment Method");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Payment");

        paymentField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        paymentField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paymentField.setText("0");
        paymentField.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        paymentField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paymentFieldKeyReleased(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Withdraw Points");

        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Balance");

        balanceField.setEditable(false);
        balanceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        balanceField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        balanceField.setText("0");
        balanceField.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N

        printInvoiceButton.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        printInvoiceButton.setForeground(new java.awt.Color(255, 0, 0));
        printInvoiceButton.setText("Print Invoice");
        printInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printInvoiceButtonActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel16.setText("Customer Name");

        jFormattedTextField1.setEditable(false);
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel17.setText("Quantity :");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel30.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel30.setText("Product size here");

        jLabel31.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel31.setText("Product Size :");

        jLabel32.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel32.setText("Brand ID :");

        jLabel33.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel33.setText("Brand ID here");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(customerMobileField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(invoiceNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addGap(18, 18, 18)
                                    .addComponent(jFormattedTextField2))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel32))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField6)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(balanceField)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(printInvoiceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TotalField)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(discountField)
                                    .addComponent(paymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(invoiceNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13)
                                .addComponent(jButton3)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel33)
                                .addComponent(jLabel32))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(customerMobileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)
                                .addComponent(jButton1)
                                .addComponent(jLabel15)
                                .addComponent(jLabel19))
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel30)
                                            .addComponent(jLabel31))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel22)))))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TotalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(discountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(paymentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(balanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(printInvoiceButton)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
        );

        jTabbedPane1.addTab("Invoice ", jPanel2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1078, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Returns", jPanel4);

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel9.setText("ORDER MANAGEMENT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        if (evt.getKeyCode() == 10) {
            jTextField4.grabFocus();
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String stockID = jTextField3.getText();
        String brand = jLabel19.getText();
        String brandID = jLabel33.getText();
        String productName = jLabel22.getText();
        String qty = jFormattedTextField2.getText();
        String size = jLabel30.getText();
        String sellingPrice = jTextField6.getText();

        if (qty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        double availableQty = Double.parseDouble(jFormattedTextField1.getText());

        try {
            double parsedQty = Double.parseDouble(qty);
            if (parsedQty <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }

            if (parsedQty > availableQty) {
                JOptionPane.showMessageDialog(this, "Insufficient available quantity!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setBrand(brand);
        invoiceItem.setBrandID(brandID);
        invoiceItem.setSize(size);
        invoiceItem.setName(productName);
        invoiceItem.setQty(qty); 
        invoiceItem.setSellingPrice(sellingPrice);
        invoiceItem.setStockID(stockID);

        if (invoiceItemMap.get(stockID) == null) {
            invoiceItemMap.put(stockID, invoiceItem);
        } else {
            InvoiceItem found = invoiceItemMap.get(stockID);
            int option = JOptionPane.showConfirmDialog(this, "Do you want to Update the Quantity of Product: " + productName, "Message",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                found.setQty(String.valueOf(Double.parseDouble(found.getQty()) + Double.parseDouble(qty)));
            }
        }

    loadInvoiceItems();
    calculate();
    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (home != null) {
            home.setVisible(true);
        }
        this.dispose();
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        invoiceNumberField.setEditable(true);
        jTable1.clearSelection();
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
                      
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyReleased

    private void discountFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_discountFieldKeyReleased
        //1
        calculate();
    }//GEN-LAST:event_discountFieldKeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        //3
        calculate();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void paymentFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paymentFieldKeyReleased
        //4
        calculate();
    }//GEN-LAST:event_paymentFieldKeyReleased

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        //5
        calculate();
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void printInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printInvoiceButtonActionPerformed
        try {

            String invoiceID = invoiceNumberField.getText();
            String customerMobile = customerMobileField.getText();
            String employeeEmail = employeeEmailLabel.getText();
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String paidAmount = paymentField.getText();
            String paymentMethodID = paymentMethodMap.get(String.valueOf(jComboBox1.getSelectedItem()));
            String discount = String.valueOf(discountField.getText());
            
            //insert into invoice
            MySQL21.executeIUD("INSERT INTO invoice VALUES('"+invoiceID+"','"+customerMobile+"','"+employeeEmail+"',"
                    + "'"+dateTime+"','"+paidAmount+"','"+paymentMethodID+"','"+discount+"')");
            //insert into invoice
            
            for (InvoiceItem invoiceItem : invoiceItemMap.values()) {
                
                //insert into invoiceItem
                MySQL21.executeIUD("INSERT INTO invoice_item(stock_id,qty,invoice_id) "
                        + "VALUES('"+invoiceItem.getStockID()+"','"+invoiceItem.getQty()+"','"+invoiceID+"')");
                
                //insert into invoiceItem
                
                //stock update
                MySQL21.executeIUD("UPDATE stock SET qty = qty-'"+invoiceItem.getQty()+"' WHERE id='"+invoiceItem.getStockID()+"'");
                //stock update
                
            }

            double totalAmount = Double.parseDouble(TotalField.getText());
            double pointsValue = 0;
            double customerPoints = Double.parseDouble(jTextField4.getText());          
            
            //withdraw points
            if (withdrawPoints) {
                
                double remainingAmount = totalAmount - payment;
                
                // Calculate points value based on remaining amount
                double requiredPoints = remainingAmount / 2000;
                
                
                if (requiredPoints <= customerPoints) {
                    totalAmount -= requiredPoints * 2000;
                    
                    
                    MySQL21.executeIUD("UPDATE customer SET points = points - '" + requiredPoints + "' WHERE mobile='" + customerMobile + "'");

                    MySQL21.executeIUD("INSERT INTO loyalty_point_transaction (customer_mobile, transaction_date, points_earned, points_redeemed) "
                            + "VALUES ('" + customerMobile + "', '" + dateTime + "', 0, '" + requiredPoints + "')");
                }else{
                    pointsValue = customerPoints * 2000;
        
                    if (pointsValue <= remainingAmount) { 
                        totalAmount -= pointsValue;
                    } else {
                        totalAmount -= remainingAmount;
                    }
                    
                    MySQL21.executeIUD("UPDATE customer SET points = points - '" + customerPoints + "' WHERE mobile='" + customerMobile + "'");

                    MySQL21.executeIUD("INSERT INTO loyalty_point_transaction (customer_mobile, transaction_date, points_earned, points_redeemed) "
                            + "VALUES ('" + customerMobile + "', '" + dateTime + "', 0, '" + customerPoints + "')");
                }
                
            }else{
                double earnedPoints = calculatePoints(totalAmount);
                
                MySQL21.executeIUD("UPDATE customer SET points = points+'"+earnedPoints+"'WHERE mobile='"+customerMobile+"'");
                
                MySQL21.executeIUD("INSERT INTO loyalty_point_transaction (customer_mobile, transaction_date, points_earned, points_redeemed) "
                    + "VALUES ('" + customerMobile + "', '" + dateTime + "', '" + earnedPoints + "', 0)");
                
            }                
            
             //view or print Report
            String path = "src//reports//Elitewardrobe.jasper";
            
            HashMap<String,Object> params = new HashMap<>();
            params.put("Parameter6", TotalField.getText());
            params.put("Parameter7", discountField.getText());
            params.put("Parameter9", String.valueOf(jComboBox1.getSelectedItem()));
            params.put("Parameter8", paymentField.getText());
            params.put("Parameter10", balanceField.getText());
            
            params.put("Parameter1", invoiceNumberField.getText());
            params.put("Parameter4", customerMobileField.getText());
            params.put("Parameter3", employeeEmailLabel.getText());
            params.put("Parameter2", dateTime);
            
            JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
            
            JasperViewer.viewReport(jasperPrint, false);
            
            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_printInvoiceButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CustomerRegistration cusreg = new CustomerRegistration(home,employeeType);
        cusreg.setVisible(true);
        cusreg.setOrderManagement(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ProductManagement proman = new ProductManagement(home,employeeType);
        proman.setVisible(true);
        proman.setOrderManagement(this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TotalFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalFieldKeyReleased

    }//GEN-LAST:event_TotalFieldKeyReleased

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

    private void productManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productManagementButtonActionPerformed
        ProductManagement proman = new ProductManagement(home, employeeType);
        proman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_productManagementButtonActionPerformed

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

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField TotalField;
    private javax.swing.JFormattedTextField balanceField;
    private javax.swing.JButton customerManagementButton;
    private javax.swing.JTextField customerMobileField;
    private javax.swing.JFormattedTextField discountField;
    private javax.swing.JLabel employeeEmailLabel;
    private javax.swing.JButton employeeManagementButton;
    private javax.swing.JButton grnManagementButton;
    private javax.swing.JTextField invoiceNumberField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JFormattedTextField paymentField;
    private javax.swing.JButton printInvoiceButton;
    private javax.swing.JButton productManagementButton;
    private javax.swing.JButton supplierManagementButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        
    }
}
