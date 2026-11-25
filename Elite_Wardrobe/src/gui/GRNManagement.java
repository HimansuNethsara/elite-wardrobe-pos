package gui;

import static gui.CustomerRegistration.logger;
import static gui.ProductManagement.logger;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.GRNItem;
import model.MySQL21;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;
import raven.toast.Notifications.Type;

/**
 *
 * @author Dracula
 */
public class GRNManagement extends javax.swing.JFrame {

    private Home home;
    
    private String employeeType;
    
     public static Logger logger = Logger.getLogger("elite");
     
     HashMap<String, GRNItem> grnItemMap = new HashMap<>();
    private static HashMap<String,String> sizeMap = new HashMap<>(); 
    
    public GRNManagement(Home home, String employeeType) {
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
        
        generateGRNId();
        jLabel19.setText(SignInWindown.getEmployeeEmail());
        loadSize();
        setButtonAccess();
    }

    private void generateGRNId() {
        String prefix = "GRN";
        String date = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        long timestamp = System.currentTimeMillis() % 1000; // Last 3 digits of the timestamp
        int randomNum = (int) (Math.random() * 1000); // Random number between 0 and 999
        String grnId = prefix + date + String.format("%03d%03d", timestamp, randomNum);
        jTextField1.setText(grnId);
    }

    //mobile   
    public JTextField getjTextField2(){
        return jTextField2;
    }
    //name
    public JLabel getjLabel21(){
        return jLabel21;
    }
    //Product ID   
    public JTextField getjTextField4(){
        return jTextField4;
    }
    //Brand Name
    public JLabel getjLabel14(){
        return jLabel14;
    }
    //Product name
    public JLabel getjLabel18(){
        return jLabel18;
    }
    
    private void loadSize(){
        
        try {
            
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `size`");
            
            Vector<String> comboBoxData = new Vector<>();
            comboBoxData.add("Select");
                        
            while(resultSet.next()){
                String sizeID = resultSet.getString("id");
                String sizeName = resultSet.getString("name");
                
                comboBoxData.add(sizeName);
                sizeMap.put(sizeName, sizeID);
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxData);
            jComboBox1.setModel(model);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load size data from database.");
        }
        
    }
    
    private void loadGRNItems() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        double total = 0;

        for (GRNItem grnItem : grnItemMap.values()) {
            Vector<String> vector = new Vector<>();
            vector.add(grnItem.getProductId());
            vector.add(grnItem.getBrandName());
            vector.add(grnItem.getProductName());
            vector.add(grnItem.getSizeName());
            vector.add(String.valueOf(grnItem.getQty()));
            vector.add(String.valueOf(grnItem.getBuyingPrice()));
            vector.add(String.valueOf(grnItem.getSellingPrice()));

            double itemTotal = grnItem.getQty() * grnItem.getBuyingPrice();
            total += itemTotal;
            vector.add(String.valueOf(itemTotal));

            model.addRow(vector);
        }

        jLabel34.setText(String.valueOf(total));

    }
    
    public void setButtonAccess() {
        if (employeeType.equalsIgnoreCase("cashier")) {
            employeeManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("stores")) {
            customerManagementButton.setEnabled(false);
            employeeManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
            
            GRNReportButton.setEnabled(false);
            
        } else if (employeeType.equalsIgnoreCase("hr manager")) {
            customerManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
            supplierManagementButton.setEnabled(false);            
        } else if (employeeType.equalsIgnoreCase("sales associate")) {
            employeeManagementButton.setEnabled(false);
            productManagementButton.setEnabled(false);
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
        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        customerManagementButton = new javax.swing.JButton();
        employeeManagementButton = new javax.swing.JButton();
        orderManagementButton = new javax.swing.JButton();
        productManagementButton = new javax.swing.JButton();
        supplierManagementButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        GRNReportButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        jMenuItem1.setText("Delete Employee");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GRN Management");

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

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("GRN Management");

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
                            .addComponent(orderManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jLabel3)
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
        jLabel9.setText("GRN MANAGEMENT");

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel10.setText("GRN Number :");

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel11.setText("Supplier :");

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel13.setText("Product :");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-add-20.png"))); // NOI18N
        jButton2.setText("Add GRN");
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
                "Product ID", "Brand", "Name", "Size", "Quantity", "Buying Price", "Selling Price", "Total"
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

        jLabel31.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel31.setText("Total");

        jLabel32.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel32.setText("Payment");

        jLabel33.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel33.setText("Balance");

        jLabel34.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("0.00");

        jLabel35.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("0.00");

        jButton10.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton10.setText("Save GRN");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel33)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFormattedTextField6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        GRNReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-pdf-30.png"))); // NOI18N
        GRNReportButton.setText("Print GRN Report");
        GRNReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GRNReportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(GRNReportButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GRNReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel8.setText("Employee");

        jLabel19.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel19.setText("EMPLOYEE DETAILS");

        jButton1.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton1.setText("SELECT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Supplier Name");
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jButton5.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jButton5.setText("SELECT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel18.setText("PRODUCT NAME HERE");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel15.setText("Name");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel12.setText("Brand");

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel14.setText("PRODUCT BRAND HERE");

        jLabel20.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel20.setText("Buying Price");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel22.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel22.setText("Selling Price");

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField1.setText("0");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel16.setText("Quantity");

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel17.setText("Size :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(5, 5, 5)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1))
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField1))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel17))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(29, 29, 29)))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField3)))
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel18)
                        .addComponent(jLabel20)
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel8)
                        .addComponent(jLabel19)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton5)
                                    .addComponent(jLabel17)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (evt.getKeyCode() == 10) {
            jTextField2.grabFocus();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String qty = jFormattedTextField1.getText();
        String buying_price = jFormattedTextField2.getText();
        String selling_price = jFormattedTextField3.getText();
        String sizeName = jComboBox1.getSelectedItem().toString();
        String sizeId = sizeMap.get(sizeName);

        GRNItem grnItem = new GRNItem();
        grnItem.setProductId(jTextField4.getText());
        grnItem.setBrandName(jLabel14.getText());
        grnItem.setProductName(jLabel18.getText());
        grnItem.setSizeName(sizeName);
        grnItem.setSizeId(sizeId);
        grnItem.setQty(Double.parseDouble(qty));
        grnItem.setBuyingPrice(Double.parseDouble(buying_price));
        grnItem.setSellingPrice(Double.parseDouble(selling_price));


        if (grnItemMap.get(jTextField4.getText()) == null) {
            grnItemMap.put(jTextField4.getText(), grnItem);
            loadGRNItems();
        } else {

            GRNItem found = grnItemMap.get(jTextField4.getText());

            if (found.getBuyingPrice() == Double.parseDouble(buying_price)
                    && found.getSellingPrice() == Double.parseDouble(selling_price)) {

                found.setQty(found.getQty() + Double.parseDouble(qty));
                loadGRNItems();
            } else {

                JOptionPane.showMessageDialog(this, "GRN item already exists with diffrent prices", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
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
        
        int row = jTable1.getSelectedRow();
        
        //Menu item
        if (evt.getButton() == MouseEvent.BUTTON3 && row >= 0 && row < jTable1.getRowCount()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        int row = jTable1.getSelectedRow(); 
        if (row != -1) { 
            
            String productId = String.valueOf(jTable1.getValueAt(row, 0));

            int response = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete this GRN item?", 
                    "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                grnItemMap.remove(productId);

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(row);

                loadGRNItems();

                Notifications.getInstance().setJFrame(this);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Location.TOP_CENTER, "GRN Item deleted successfully");
            }
        } else {

            JOptionPane.showMessageDialog(this, 
                "Please select a GRN item to delete!","Warning", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void GRNReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GRNReportButtonActionPerformed
       
        String path = "src//reports//elite-wardrobe(userreport).jasper";
        
        HashMap<String,Object> params = new HashMap<>();
        params.put("Parameter1", new Date());
        
        try {   
                    
        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
        
        JasperViewer.viewReport(jasperPrint,false);
        
        Notifications.getInstance().setJFrame(this);
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Location.TOP_CENTER, "Report generated successfully!");
        
        } catch (JRException e) {
            logger.log(Level.SEVERE, "Error occurred while generating the report.");
        }
        
        JOptionPane.showMessageDialog(this, "An error occurred while generating the report.", "Error", JOptionPane.ERROR_MESSAGE);
        
    }//GEN-LAST:event_GRNReportButtonActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
       try {
        String grnNumber = jTextField1.getText();
        String supplierMobile = jTextField2.getText();
        String employeeEmail = jLabel19.getText();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String paidAmount = jFormattedTextField6.getText();

        MySQL21.executeIUD("INSERT INTO `grn` VALUES('" + grnNumber + "','" + supplierMobile + "','" + dateTime + "',"
                + "'" + employeeEmail + "','" + paidAmount + "')");

        for (GRNItem grnItem : grnItemMap.values()) {
            ResultSet resultSet = MySQL21.executeSearch("SELECT * FROM `stock` WHERE "
                    + "`product_id` = '" + grnItem.getProductId() + "' "
                    + "AND `price` = '" + grnItem.getSellingPrice() + "' "
                    + "AND `size_id` = '" + grnItem.getSizeId() + "'");

            String sid = "";

            if (resultSet.next()) {
                // Existing stock
                sid = resultSet.getString("id");
                String currentQty = resultSet.getString("qty");
                String updatedQty = String.valueOf(Double.parseDouble(currentQty) + grnItem.getQty());
                MySQL21.executeIUD("UPDATE `stock` SET `qty` = '" + updatedQty + "' WHERE `id` = '" + sid + "'");
            } else {
                // New stock
                MySQL21.executeIUD("INSERT INTO `stock` (`product_id`,`size_id`,`qty`,`price`) "
                        + "VALUES('" + grnItem.getProductId() + "','" + grnItem.getSizeId() + "','" + grnItem.getQty() + "','" + grnItem.getSellingPrice() + "')");

                ResultSet resultSet2 = MySQL21.executeSearch("SELECT * FROM `stock` WHERE "
                        + "`product_id` = '" + grnItem.getProductId() + "' "
                        + "AND `price` = '" + grnItem.getSellingPrice() + "'"
                        + "AND `size_id` = '" + grnItem.getSizeId() + "'");

                if (resultSet2.next()) {
                    sid = resultSet2.getString("id");
                }
                
                MySQL21.executeIUD("INSERT INTO `grn_item`(`stock_id`, `qty`, `price`, `grn_id`) "
                        + "VALUES('" + sid + "','" + grnItem.getQty() + "','" + grnItem.getBuyingPrice() + "','" + grnNumber + "')");
                
                Notifications.getInstance().setJFrame(this);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Location.TOP_CENTER, "GRN successfully saved!");
                
                reset();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jFormattedTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField6KeyReleased
        String total = jLabel34.getText();  
        String payment = jFormattedTextField6.getText();

        if (payment.isEmpty()) {
            payment = "0";  
        } 

        try {
            if (!payment.matches("^(\\d+\\.?\\d*)$")) {
                jLabel35.setText("INVALID");
                jLabel35.setForeground(Color.RED);

                Notifications.getInstance().setJFrame(this);
                Notifications.getInstance().show(Notifications.Type.WARNING, Location.TOP_CENTER, "Invalid payment format.");
            } else {

                double paymentAmount = Double.parseDouble(payment);
                double totalAmount = Double.parseDouble(total);

                double balance = paymentAmount - totalAmount;

                jLabel35.setText(String.valueOf(balance));
                jLabel35.setForeground(Color.WHITE);
            }
        } catch (NumberFormatException e) {
            
            jLabel35.setText("ERROR");
            jLabel35.setForeground(Color.RED);

            Notifications.getInstance().setJFrame(this);
            Notifications.getInstance().show(Notifications.Type.ERROR, Location.TOP_CENTER, "Unexpected error: invalid number format.");
        }
    }//GEN-LAST:event_jFormattedTextField6KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SupplierManagement suppman = new SupplierManagement(home, employeeType);
        suppman.setVisible(true);
        suppman.setGRNManagement(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ProductManagement proman = new ProductManagement(home, employeeType);
        proman.setVisible(true);
        proman.setGRNManagement(this);
        
    }//GEN-LAST:event_jButton5ActionPerformed

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

    private void orderManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderManagementButtonActionPerformed
        OrderManagement orderman = new OrderManagement(home, employeeType);
        orderman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_orderManagementButtonActionPerformed

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
    private javax.swing.JButton GRNReportButton;
    private javax.swing.JButton customerManagementButton;
    private javax.swing.JButton employeeManagementButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JButton orderManagementButton;
    private javax.swing.JButton productManagementButton;
    private javax.swing.JButton supplierManagementButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {
       jTextField1.setText("");
        jTextField2.setText("");
        jLabel21.setText("Supplier Name");
        jTextField4.setText("");
        jFormattedTextField1.setText("");
        jLabel18.setText("Product Name Here");
        jLabel14.setText("Product Brand Here");
        jComboBox1.setSelectedIndex(0);
        jFormattedTextField2.setText("");
        jFormattedTextField3.setText("");
        jLabel34.setText("0.00");
        jLabel35.setText("0.00");
        jFormattedTextField6.setText("");
        
        jTable1.clearSelection();
    }
}
