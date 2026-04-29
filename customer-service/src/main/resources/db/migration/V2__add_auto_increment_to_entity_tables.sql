-- Add AUTO_INCREMENT to customers, restaurant_tables, and menu_items
-- so JPA can generate IDs on insert.

ALTER TABLE customers ALTER COLUMN id BIGINT AUTO_INCREMENT;


-- Seed data occupies IDs 1-10; restart sequences past that.
ALTER TABLE customers ALTER COLUMN id RESTART WITH 11;

