<h1 align="center">👔 Elite Wardrobe POS</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java_11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 11" />
  <img src="https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=java&logoColor=white" alt="Swing" />
  <img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/JasperReports-333333?style=for-the-badge" alt="JasperReports" />
</p>

<p align="center">
  <strong>Elite Wardrobe</strong> is a comprehensive desktop Point of Sale (POS) and inventory management system built with Java Swing. It features integrated invoicing, stock management, and a customer loyalty program.
</p>

> *Note: This is one of my early foundational projects, built to explore desktop application development, complex relational database management, and report generation. It served as a stepping stone to my later enterprise web and mobile applications.*

---

## 📖 Overview

**Elite Wardrobe** is an offline retail management application designed for clothing businesses. It streamlines the entire retail workflow—from receiving stock via GRNs (Goods Received Notes) to generating customer invoices, applying discounts, and awarding loyalty points. It uses a monolithic Swing MVC-style architecture backed by a local MySQL database.

## ✨ Key Features

### 🛒 Point of Sale (POS) & Invoicing
- Interactive product selection and invoice generation.
- Automatic calculation of totals, discounts, and payment balances.
- Printable PDF invoices generated via JasperReports.
- Integrated customer loyalty point earning, redemption, and history.

### 📦 Inventory & Stock Management
- Track products across brands, categories, sizes, colors, and seasons.
- Generate GRNs (Goods Received Notes) for supplier deliveries.
- Automated stock quantity updates upon GRN creation and sales.

### 👥 User & Supplier Management
- Role-based navigation for Admin, Cashier, Stores, HR, and Sales.
- Customer registration, search, and invoice history tracking.
- Supplier management with company and supplier type categorizations.

## 🛠️ Technology Stack

| Category | Technologies |
| :--- | :--- |
| **Language** | Java 11 |
| **UI Framework** | Java Swing / AWT, FlatLaf 3.4.1 (Modern Themes) |
| **Database** | MySQL, JDBC (Connector/J 8.0.33) |
| **Reporting** | JasperReports 6.20.5, OpenPDF |
| **Utilities** | Jackson (JSON), JCalendar, Swing Toast Notifications |
| **Build Tool** | Apache Ant (NetBeans) |

## 🏗️ System Architecture

The project is a **monolithic desktop application** utilizing a Swing MVC-style architecture:

1. **UI Layer:** NetBeans-generated Swing frames capture user input.
2. **Data Access:** A centralized JDBC utility (`MySQL21`) executes string-built SQL queries directly to the local MySQL database.
3. **Reporting Pipeline:** Swing table data is converted into a DataSource and sent to `.jasper` templates to generate dynamic PDF reports and invoices.
4. **Session State:** Employee identity and role are managed via static in-memory fields during the application lifecycle.

## 🗄️ Database Schema

The application utilizes a highly relational MySQL schema (`elite_wardrobe`). Key entities include:

- **Users:** `employee`, `employee_type`, `employee_details`, `employee_address`
- **Catalog:** `product`, `brand`, `product_category`, `size`, `color`, `fashion_season`
- **Inventory:** `stock`, `grn` (Goods Received Note), `grn_item`
- **Sales:** `invoice`, `invoice_item`, `payment_method`, `discount`
- **Loyalty:** `customer`, `loyalty_point_transaction`
- **Suppliers:** `supplier`, `supplier_type`, `company`

## 🚀 Known Limitations & Future Enhancements

*(Note: As an early foundational project, the following production-level improvements were identified for future iterations).*
- [ ] **Security:** Implement Prepared Statements to prevent SQL injection (currently uses string concatenation).
- [ ] **Security:** Add password hashing (e.g., BCrypt) instead of plaintext storage.
- [ ] **Security:** Implement server-level authorization rather than just disabling UI buttons based on role.
- [ ] **Infrastructure:** Move hardcoded database credentials and report templates to external config files.
- [ ] **Database:** Wrap critical operations (like invoice generation and stock deduction) in ACID transactions.
- [ ] **Testing:** Add JUnit tests for pricing calculations, discounts, and stock updates.

## 👨‍💻 Author

**Himansu Nethsara Ranasinghe**
- 🎓 BSc (Hons) Software Engineering Undergraduate (JIAT / BCU)
- 🔗 [LinkedIn](https://www.linkedin.com/in/himansu-nethsara/)
- 🐙 [GitHub](https://github.com/HimansuNethsara)