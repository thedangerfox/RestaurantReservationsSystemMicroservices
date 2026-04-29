-- ================================
-- MENU SERVICE DATABASE
-- Owns ONLY menu items
-- ================================

CREATE TABLE IF NOT EXISTS menu_items (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(120) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL
    );

-- Dummy data
INSERT INTO menu_items (name, price, status) VALUES
                                                 ('Margherita Pizza', 14.99, 'ACTIVE'),
                                                 ('Pepperoni Pizza', 15.99, 'ACTIVE'),
                                                 ('Caesar Salad', 9.99, 'ACTIVE'),
                                                 ('Greek Salad', 10.99, 'ACTIVE'),
                                                 ('Spaghetti Bolognese', 16.99, 'ACTIVE'),
                                                 ('Lasagna', 17.99, 'ACTIVE'),
                                                 ('Chicken Alfredo', 18.99, 'ACTIVE'),
                                                 ('Garlic Bread', 5.99, 'ACTIVE'),
                                                 ('Tiramisu', 7.99, 'ACTIVE'),
                                                 ('Cheesecake', 8.99, 'ACTIVE');
