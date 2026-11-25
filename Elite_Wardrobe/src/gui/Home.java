package gui;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Home extends javax.swing.JFrame {

    private String employeeName;
    private String employeeEmail;
    private String profileImagePath;
    private String employeeType;

    public Home(String employeeName, String employeeEmail, String profileImagePath, String employeeType) {
        initComponents();
        
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.profileImagePath = profileImagePath;
        this.employeeType = employeeType; 

        // Display employee data in labels
        jLabel1.setText(employeeName);
        jLabel2.setText(employeeEmail);
        
        // Load and display the profile image
        ImageIcon profileImage = new ImageIcon(profileImagePath);
        Image image = profileImage.getImage().getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_SMOOTH);
        jLabel3.setIcon(new ImageIcon(image));
        
        setButtonAccess();
                              
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
            orderManagementButton.setEnabled(false);
        } else if (employeeType.equalsIgnoreCase("hr manager")) {
            customerManagementButton.setEnabled(false);
            orderManagementButton.setEnabled(false);
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        customerManagementButton = new javax.swing.JButton();
        employeeManagementButton = new javax.swing.JButton();
        grnManagementButton = new javax.swing.JButton();
        orderManagementButton = new javax.swing.JButton();
        productManagementButton = new javax.swing.JButton();
        supplierManagementButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("User Name Here");

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("User Email Here");

        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8-log-out-30.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("HOME");

        customerManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        customerManagementButton.setText("Customer Management");
        customerManagementButton.setBorderPainted(false);
        customerManagementButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                customerManagementButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                customerManagementButtonMouseExited(evt);
            }
        });
        customerManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerManagementButtonActionPerformed(evt);
            }
        });

        employeeManagementButton.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        employeeManagementButton.setText("Employee Management");
        employeeManagementButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                employeeManagementButtonMouseEntered(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(customerManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(grnManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(orderManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customerManagementButton)
                .addGap(18, 18, 18)
                .addComponent(employeeManagementButton)
                .addGap(18, 18, 18)
                .addComponent(grnManagementButton)
                .addGap(18, 18, 18)
                .addComponent(orderManagementButton)
                .addGap(18, 18, 18)
                .addComponent(productManagementButton)
                .addGap(18, 18, 18)
                .addComponent(supplierManagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void employeeManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeManagementButtonActionPerformed
        
        EmployeeRegistration employreg = new EmployeeRegistration(this, employeeType);
        employreg.setButtonAccess();
        employreg.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_employeeManagementButtonActionPerformed

    private void customerManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerManagementButtonActionPerformed
        CustomerRegistration custreg = new CustomerRegistration(this, employeeType);
        custreg.setButtonAccess();
        custreg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_customerManagementButtonActionPerformed

    private void supplierManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierManagementButtonActionPerformed
        SupplierManagement suppman = new SupplierManagement(this, employeeType);
        suppman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_supplierManagementButtonActionPerformed

    private void productManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productManagementButtonActionPerformed
        ProductManagement proman = new ProductManagement(this, employeeType);
        proman.setButtonAccess();
        proman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_productManagementButtonActionPerformed

    private void grnManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grnManagementButtonActionPerformed
        GRNManagement grnman = new GRNManagement(this, employeeType);
        grnman.setVisible(true);
        this.dispose();    }//GEN-LAST:event_grnManagementButtonActionPerformed

    private void orderManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderManagementButtonActionPerformed
        OrderManagement orderman = new OrderManagement(this, employeeType);
        orderman.setButtonAccess();
        orderman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_orderManagementButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        SignInWindown signin = new SignInWindown();
        signin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void customerManagementButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerManagementButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_customerManagementButtonMouseEntered

    private void customerManagementButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerManagementButtonMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_customerManagementButtonMouseExited

    private void employeeManagementButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeManagementButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeManagementButtonMouseEntered

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerManagementButton;
    private javax.swing.JButton employeeManagementButton;
    private javax.swing.JButton grnManagementButton;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton orderManagementButton;
    private javax.swing.JButton productManagementButton;
    private javax.swing.JButton supplierManagementButton;
    // End of variables declaration//GEN-END:variables
}
