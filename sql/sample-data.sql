USE pharmacy_db;

INSERT INTO users (username, password, full_name, role) VALUES
('admin',  'admin123', 'Noor Malik', 'ADMIN'),
('staff1', 'staff123', 'Ali Raza',   'STAFF');

INSERT INTO suppliers (name, phone, address) VALUES
('MediSupply Co.',  '0300-1112223', 'Karachi'),
('HealthPlus Ltd.', '0301-4445556', 'Lahore');

INSERT INTO medicines (name, category, price, stock, min_stock, expiry_date, supplier_id) VALUES
('Panadol 500mg',   'Tablet',   30.00, 200, 20, '2027-05-31', 1),
('Amoxil 250mg',    'Capsule', 120.00,   8, 15, '2026-12-01', 1),
('Vitamin C 500mg', 'Tablet',  250.00,  50, 10, '2026-09-10', 2);

INSERT INTO sales (total_amount, user_id) VALUES (180.00, 2);
INSERT INTO sale_items (sale_id, medicine_id, quantity, unit_price) VALUES
(1, 1, 2,  30.00),
(1, 2, 1, 120.00);