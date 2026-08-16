# Pharmacy Management System (Java Swing + MySQL)

A desktop pharmacy management system built with Java (Swing) and MySQL,
using a clean 3-layer architecture: model → dao → ui.

## Features
- 🔐 Login with role-based access (ADMIN vs STAFF)
- 💊 Inventory: add medicine, restock, delete, LOW-STOCK alerts, expiry check
- 🧾 POS billing: cart + transactional checkout (all-or-nothing) + automatic stock drop
- 👥 User management (admin only)
- 📊 Sales history & reports: today's revenue, low-stock/expired counts, per-sale items via SQL JOINs

## Tech Stack
Java (Swing, JDBC) • MySQL 8 • NetBeans • Git/GitHub

## Project Structure
Pharmacy_Management_System/src/
- model/  entities (User→Admin/SalesStaff, Medicine, Sale, SaleItem, Supplier)
- dao/    MedicineDAO, UserDAO, SaleDAO (JDBC + transactions)
- ui/     LoginFrame, MainFrame, PosFrame, AddMedicineFrame, UsersFrame, ReportsFrame
- util/   DBConnection (single point of connection)
sql/ database schema + sample data

## Setup
1. Install MySQL 8; run the sql/ scripts in Workbench (creates pharmacy_db + sample data)
2. Open project in NetBeans; add mysql-connector-j jar to Libraries
3. Edit credentials in util/DBConnection.java
4. Run app/Main.java
5. Default logins: admin/admin123 • staff1/staff123

## OOP Concepts Applied
- Encapsulation: private fields + validated setters
- Inheritance: abstract User → Admin / SalesStaff
- Polymorphism: canDeleteMedicine() decides admin-only UI at runtime
- DAO pattern: UI never touches SQL directly

## Future Work
Supplier purchase orders, barcode scanning, receipt printing,
expiry reminders, automated backups.

## Author
NoorulAin
