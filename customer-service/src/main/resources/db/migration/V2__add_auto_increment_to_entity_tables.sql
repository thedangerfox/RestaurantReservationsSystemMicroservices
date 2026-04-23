-- Add AUTO_INCREMENT to customers, restaurant_tables, and menu_items
-- so JPA can generate IDs on insert.

ALTER TABLE customers ALTER COLUMN id BIGINT AUTO_INCREMENT;
ALTER TABLE restaurant_tables ALTER COLUMN id BIGINT AUTO_INCREMENT;
ALTER TABLE menu_items ALTER COLUMN id BIGINT AUTO_INCREMENT;

-- Seed data occupies IDs 1-10; restart sequences past that.
ALTER TABLE customers ALTER COLUMN id RESTART WITH 11;
ALTER TABLE restaurant_tables ALTER COLUMN id RESTART WITH 11;
ALTER TABLE menu_items ALTER COLUMN id RESTART WITH 11;
ALTER TABLE reservations ALTER COLUMN id RESTART WITH 11;
ALTER TABLE pre_order_items ALTER COLUMN id RESTART WITH 11;
