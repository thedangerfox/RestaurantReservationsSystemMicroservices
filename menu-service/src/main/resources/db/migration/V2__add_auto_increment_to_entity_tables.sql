-- Add AUTO_INCREMENT to customers, restaurant_tables, and menu_items
-- so JPA can generate IDs on insert.


ALTER TABLE menu_items ALTER COLUMN id BIGINT AUTO_INCREMENT;


ALTER TABLE menu_items ALTER COLUMN id RESTART WITH 11;

