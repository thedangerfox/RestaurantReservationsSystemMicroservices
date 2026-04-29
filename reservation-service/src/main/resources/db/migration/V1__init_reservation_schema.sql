-- ================================
-- RESERVATION SERVICE DATABASE
-- Owns reservations + pre-order items
-- No foreign keys to other services
-- ================================

CREATE TABLE IF NOT EXISTS reservations (
                                            id BIGSERIAL PRIMARY KEY,
                                            customer_id BIGINT NOT NULL,   -- validated via customer-service API
                                            table_id BIGINT NOT NULL,      -- validated via table-service API
                                            reservation_time TIMESTAMP NOT NULL,
                                            party_size INT NOT NULL,
                                            status VARCHAR(20) NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_res_customer_id ON reservations(customer_id);
CREATE INDEX IF NOT EXISTS idx_res_table_id ON reservations(table_id);

CREATE TABLE IF NOT EXISTS pre_order_items (
                                               id BIGSERIAL PRIMARY KEY,
                                               reservation_id BIGINT NOT NULL,
                                               item_id BIGINT NOT NULL,       -- validated via menu-service API
                                               quantity INT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_preorder_reservation_id ON pre_order_items(reservation_id);

-- Dummy reservations
INSERT INTO reservations (customer_id, table_id, reservation_time, party_size, status) VALUES
                                                                                           (1, 1, TIMESTAMP '2026-05-01 18:00:00', 2, 'PENDING'),
                                                                                           (2, 2, TIMESTAMP '2026-05-01 19:00:00', 2, 'PENDING'),
                                                                                           (3, 3, TIMESTAMP '2026-05-02 18:30:00', 4, 'PENDING'),
                                                                                           (4, 4, TIMESTAMP '2026-05-02 19:30:00', 4, 'PENDING'),
                                                                                           (5, 5, TIMESTAMP '2026-05-03 18:00:00', 6, 'PENDING');

-- Dummy pre-order items
INSERT INTO pre_order_items (reservation_id, item_id, quantity) VALUES
                                                                    (1, 1, 1),
                                                                    (1, 8, 2),
                                                                    (2, 2, 1),
                                                                    (3, 3, 2),
                                                                    (4, 5, 1);
