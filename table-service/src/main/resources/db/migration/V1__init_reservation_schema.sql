-- ================================
-- TABLE SERVICE DATABASE
-- Owns ONLY restaurant tables
-- ================================

CREATE TABLE IF NOT EXISTS restaurant_tables (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 table_number INT NOT NULL UNIQUE,
                                                 capacity INT NOT NULL,
                                                 status VARCHAR(20) NOT NULL
    );

-- Dummy data
INSERT INTO restaurant_tables (table_number, capacity, status) VALUES
                                                                   (1, 2, 'AVAILABLE'),
                                                                   (2, 2, 'AVAILABLE'),
                                                                   (3, 4, 'AVAILABLE'),
                                                                   (4, 4, 'AVAILABLE'),
                                                                   (5, 6, 'AVAILABLE'),
                                                                   (6, 6, 'AVAILABLE'),
                                                                   (7, 8, 'AVAILABLE'),
                                                                   (8, 8, 'AVAILABLE'),
                                                                   (9, 10, 'AVAILABLE'),
                                                                   (10, 10, 'AVAILABLE');
