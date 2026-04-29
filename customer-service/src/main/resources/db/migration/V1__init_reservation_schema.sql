-- ================================
-- CUSTOMER SERVICE DATABASE
-- Owns ONLY the customers table
-- ================================

CREATE TABLE IF NOT EXISTS customers (
                                         id BIGSERIAL PRIMARY KEY,
                                         full_name VARCHAR(120) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(20) NOT NULL
    );

-- Dummy data
INSERT INTO customers (full_name, phone, status) VALUES
                                                     ('John Smith', '514-555-1001', 'ACTIVE'),
                                                     ('Sarah Johnson', '514-555-1002', 'ACTIVE'),
                                                     ('Michael Brown', '514-555-1003', 'ACTIVE'),
                                                     ('Emily Davis', '514-555-1004', 'ACTIVE'),
                                                     ('David Wilson', '514-555-1005', 'ACTIVE');
